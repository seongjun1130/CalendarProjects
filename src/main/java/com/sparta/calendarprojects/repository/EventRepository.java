package com.sparta.calendarprojects.repository;

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

    public Event save(Event event) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO event (todo, password,createddate,modifieddate,startday,endday,creator) VALUES (?,?,?,?,?,?,?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, event.getTodo());
                    preparedStatement.setString(2, event.getPassword());
                    preparedStatement.setString(3, String.valueOf(java.sql.Timestamp.valueOf(LocalDateTime.now())));
                    preparedStatement.setString(4, String.valueOf(java.sql.Timestamp.valueOf(LocalDateTime.now())));
                    preparedStatement.setString(5, event.getStartday().toString());
                    preparedStatement.setString(6, event.getEndday().toString());
                    preparedStatement.setString(7, event.getCreator());

                    return preparedStatement;
                },
                keyHolder);
        // DB Insert 후 받아온 기본키 확인
        Long id = keyHolder.getKey().longValue();
        event.setId(id);
        return event;
    }

    public List<EventResponseDto> findAll() {
        String sql = "SELECT * FROM event";

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
}
