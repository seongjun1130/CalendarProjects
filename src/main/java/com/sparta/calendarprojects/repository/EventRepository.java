package com.sparta.calendarprojects.repository;

import com.sparta.calendarprojects.dto.EventRequestDto;
import com.sparta.calendarprojects.dto.EventResponseDto;
import com.sparta.calendarprojects.entity.Event;
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
    public Event save(Event event) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        // SQL 문 작성
        String sql = "INSERT INTO event (todo, password,createddate,modifieddate,startday,endday,creator) VALUES (?,?,?,?,?,?,?)";
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
                    preparedStatement.setString(7, event.getCreator());

                    return preparedStatement;
                },
                keyHolder);
        // DB Insert 후 받아온 기본키 및 생성/수정시간 확인
        Long id = keyHolder.getKey().longValue();
        event.setId(id);
        event.setCreateddate(timeNow);
        event.setModifieddate(timeNow);
        return event;
    }

    // Client 에서 보내온 파라미터값에 따른 DB 조회 메소드
    // 조건에 따른 WHERE 절 변화
    public List<EventResponseDto> findAll(String creator, String modifieddate) {
        // 둘다 기입하지 않았을시 모든 일정 조회
        String sql = "SELECT * FROM event";
        // 작성자, 수정일을 둘다 기입했을시.
        if (creator != null && modifieddate != null) {
            sql += " WHERE creator = " + "'" + creator + "'" + " OR date_format(modifieddate,'%Y-%m-%d') = " + "'" + modifieddate + "'";
        }
        // 수정일만 기입시 수정일 기준으로 DB 조회.
        else if (modifieddate != null) {
            sql += " WHERE date_format(modifieddate,'%Y-%m-%d') = " + "'" + modifieddate + "'";
        }
        // 작성자만 기입시 작성자 기준으로 DB 조회.
        else if (creator != null) {
            sql += " WHERE creator = " + "'" + creator + "'";
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
                String creator = rs.getString("creator");
                return new EventResponseDto(id, todo, createddate, modifieddate, startday, endday, creator);
            }
        });
    }

    // ID 값을 기준으로 DB 조회 메소드
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
                event.setCreator(resultSet.getString("creator"));
                return event;
            } else {
                return null;
            }
        }, id);
    }

    // ID 값을 기준으로 DB 데이터 수정 메소드
    public void update(Long id, EventRequestDto requestDto) {
        java.sql.Timestamp timeNow = Timestamp.valueOf(LocalDateTime.now());
        String sql = "UPDATE event SET todo=?,creator=?,modifieddate=?, startday =?, endday=?  WHERE id =?";
        jdbcTemplate.update(sql, requestDto.getTodo(), requestDto.getCreator(), timeNow, requestDto.getStartday(), requestDto.getEndday(), id);
    }

    // ID 값을 기준으로 DB 데이터 삭제 메소드
    public void delete(Long id) {
        String sql = "DELETE FROM event WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
}
