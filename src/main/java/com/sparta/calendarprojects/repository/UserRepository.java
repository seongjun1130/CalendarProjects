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

    // User Data 를 DB에 저장하는 메소드
    public User save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO user (username, email,join_date,update_date) VALUES (?,?,?,?)";
        // 생성/수정일을 주기위한 Timestamp 생성
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
        // UserRequestDto 내의 생략되어있는 요소를 가져옴.
        Long id = keyHolder.getKey().longValue();
        user.setId(id);
        user.setJoin_date(timeNow);
        user.setUpdate_date(timeNow);
        return user;
    }

    // 단일 User Data 를 DB 상에서 조회하는 메소드
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

    // 모든 User Data 를 DB 상에서 조회하는 메소드
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

    // 단일 User Data 를 DB 상에서 수정하는 메소드
    public void update(Long id, UserRequestDto requestDto) {
        // 수정시간 입력을 위한 Timestamp 생성
        Timestamp update_date = Timestamp.valueOf(LocalDateTime.now());
        // 해당 ID를 가진 UserData 수정
        String sql = "UPDATE user SET username = ?, update_date = ? WHERE id = ?";
        jdbcTemplate.update(sql, requestDto.getUsername(), update_date, id);
        // event Table 에 일정을 가지고 있을 경우 변경값으로 함께 변경
        sql = "UPDATE event SET username = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, requestDto.getUsername(), id);
    }

    // 단일 User Data 를 DB 상에서 삭제하는 메소드
    public void delete(Long id) {
        // 해당 ID를 가진 UserData 삭제
        // Table 의 제약 조건에의해 해당 id 를 가지는 event Table 의 일정또한 같이 삭제됌.
        String sql = "DELETE FROM user WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
