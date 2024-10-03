package com.sparta.calendarprojects.repository;

import com.sparta.calendarprojects.dto.EventRequestDto;
import com.sparta.calendarprojects.dto.EventResponseDto;
import com.sparta.calendarprojects.entity.Event;
import com.sparta.calendarprojects.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class EventRepository {
    private final JdbcTemplate jdbcTemplate;


    public EventRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // DB 에 Entity 객체(Event) 정보 저장 메소드
    public Event save(Event event, User userinfo) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        // SQL 문 작성
        String sql = "INSERT INTO event (todo, password,createddate,modifieddate,startday,endday,user_id,username) VALUES (?,?,?,?,?,?,?,?)";
        java.sql.Timestamp timeNow = Timestamp.valueOf(LocalDateTime.now());
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);
                    // Event 객체 필드에 있는 정보 주입.
                    preparedStatement.setString(1, event.getTodo());
                    preparedStatement.setString(2, event.getPassword());
                    preparedStatement.setString(3, String.valueOf(timeNow));
                    preparedStatement.setString(4, String.valueOf(timeNow));
                    preparedStatement.setString(5, event.getStartday().toString());
                    preparedStatement.setString(6, event.getEndday().toString());
                    preparedStatement.setString(7, event.getUser_id().toString());
                    preparedStatement.setString(8, userinfo.getUsername());
                    return preparedStatement;
                },
                keyHolder);
        // DB Insert 후 받아온 기본키 및 생성/수정시간 삽입
        Long id = keyHolder.getKey().longValue();
        event.setId(id);
        event.setCreateddate(timeNow);
        event.setModifieddate(timeNow);
        event.setUsername(userinfo.getUsername());
        return event;
    }

    // Client 에서 보내온 파라미터값에 따른 DB 조회 메소드
    // 조건에 따른 WHERE 절 변화
    public List<EventResponseDto> findAll(Long user_id, String modifieddate) {
        // 둘다 기입하지 않았을시 모든 일정 조회
        String sql = "SELECT * FROM event";
        // 작성자 ID, 수정일을 둘다 기입했을시.
        if (user_id != null && modifieddate != null) {
            sql += " WHERE user_id = " + user_id + " OR date_format(modifieddate,'%Y-%m-%d') = " + "'" + modifieddate + "'";
        }
        // 수정일만 기입시 수정일 기준으로 DB 조회.
        else if (modifieddate != null) {
            sql += " WHERE date_format(modifieddate,'%Y-%m-%d') = " + "'" + modifieddate + "'";
        }
        // 작성자 ID 만 기입시 작성자 기준으로 DB 조회.
        else if (user_id != null) {
            sql += " WHERE user_id = " + user_id;
        }
        sql += " ORDER BY modifieddate DESC";
        return jdbcTemplate.query(sql, new RowMapper<EventResponseDto>() {
            @Override
            public EventResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Memo 데이터들을 EventResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("id");
                String todo = rs.getString("todo");
                java.sql.Timestamp createddate = rs.getTimestamp("createddate");
                java.sql.Timestamp modifieddate = rs.getTimestamp("modifieddate");
                java.sql.Date startday = rs.getDate("startday");
                java.sql.Date endday = rs.getDate("endday");
                Long user_id = rs.getLong("user_id");
                String username = rs.getString("username");
                return new EventResponseDto(id,  user_id, username, todo, createddate, modifieddate, startday, endday);
            }
        });
    }

    // 일정 ID 값을 기준으로 DB 조회 메소드
    public Event findId(Long id) {
        String sql = "SELECT * FROM event WHERE id=?";
        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                Event event = new Event();
                event.setId(resultSet.getLong("id"));
                event.setTodo(resultSet.getString("todo"));
                event.setPassword(resultSet.getString("password"));
                event.setCreateddate(resultSet.getTimestamp("createddate"));
                event.setModifieddate(resultSet.getTimestamp("modifieddate"));
                event.setStartday(resultSet.getDate("startday"));
                event.setEndday(resultSet.getDate("endday"));
                event.setUser_id(resultSet.getLong("user_id"));
                return event;
            } else {
                return null;
            }
        }, id);
    }

    // 일정 ID 값을 기준으로 DB 데이터 수정 메소드
    public void update(Long id, EventRequestDto requestDto) {
        java.sql.Timestamp modifieddate = Timestamp.valueOf(LocalDateTime.now());
        String sql = "UPDATE event SET todo=?, modifieddate=?, startday =?, endday=?  WHERE id =?";
        jdbcTemplate.update(sql, requestDto.getTodo(), modifieddate, requestDto.getStartday(), requestDto.getEndday(), id);
    }

    // 일정 ID 값을 기준으로 DB 데이터 삭제 메소드
    public void delete(Long id) {
        String sql = "DELETE FROM event WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
}
