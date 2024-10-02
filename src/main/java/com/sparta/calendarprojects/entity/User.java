package com.sparta.calendarprojects.entity;

import com.sparta.calendarprojects.dto.UserRequestDto;
import lombok.*;

@NonNull
@Getter
@Setter
@NoArgsConstructor
public class User {
    private Long id;
    private String username;
    private String email;
    private java.sql.Timestamp join_date;
    private java.sql.Timestamp update_date;

    public User(UserRequestDto userRequestDto) {
        this.id = userRequestDto.getId();
        this.username = userRequestDto.getUsername();
        this.email = userRequestDto.getEmail();
        this.join_date = userRequestDto.getJoin_date();
        this.update_date = userRequestDto.getUpdate_date();
    }
}
