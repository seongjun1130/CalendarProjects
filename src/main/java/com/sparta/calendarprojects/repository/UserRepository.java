package com.sparta.calendarprojects.repository;

import com.sparta.calendarprojects.dto.UserReponseDto;
import com.sparta.calendarprojects.dto.UserRequestDto;
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
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO user (username, email,join_date,update_date) VALUES (?,?,?,?)";
        java.sql.Timestamp timeNow = Timestamp.valueOf(LocalDateTime.now());
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setTimestamp(3, timeNow);
            preparedStatement.setTimestamp(4, timeNow);
            return preparedStatement;
        }, keyHolder);
        Long id = keyHolder.getKey().longValue();
        user.setId(id);
        user.setJoin_date(timeNow);
        user.setUpdate_date(timeNow);
        return user;
    }

    public User findId(Long id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setJoin_date(resultSet.getTimestamp("join_date"));
                user.setUpdate_date(resultSet.getTimestamp("update_date"));
                return user;
            } else {
                return null;
            }
        }, id);
    }

    public List<UserReponseDto> findAll() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, new RowMapper<UserReponseDto>() {
            @Override
            public UserReponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("id");
                String username = rs.getString("username");
                String email = rs.getString("email");
                Timestamp join_date = rs.getTimestamp("join_date");
                Timestamp update_date = rs.getTimestamp("update_date");
                return new UserReponseDto(id, username, email, join_date, update_date);
            }
        });
    }

    public void update(Long id, UserRequestDto requestDto) {
        Timestamp update_date = Timestamp.valueOf(LocalDateTime.now());
        String sql = "UPDATE user SET username = ?, update_date = ? WHERE id = ?";
        jdbcTemplate.update(sql, requestDto.getUsername(), update_date, id);
        sql = "UPDATE event SET username = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, requestDto.getUsername(), id);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM user WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
