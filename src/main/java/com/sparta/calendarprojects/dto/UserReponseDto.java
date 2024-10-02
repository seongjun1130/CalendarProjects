package com.sparta.calendarprojects.dto;

import com.sparta.calendarprojects.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserReponseDto {
    private Long id;
    private String username;
    private String email;
    private java.sql.Timestamp join_date;
    private java.sql.Timestamp update_date;

    public UserReponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.join_date = user.getJoin_date();
        this.update_date = user.getUpdate_date();
    }
}
