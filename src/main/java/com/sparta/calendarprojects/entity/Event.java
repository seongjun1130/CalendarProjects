package com.sparta.calendarprojects.entity;

import com.sparta.calendarprojects.dto.EventRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@NonNull
@Getter
@Setter
@NoArgsConstructor
public class Event {
    private Long id;
    private Long user_id;
    private String username;
    private String todo;
    private String password;
    private java.sql.Timestamp createddate;
    private java.sql.Timestamp modifieddate;
    private java.sql.Date startday;
    private java.sql.Date endday;

    public Event(EventRequestDto requestDto) {
        this.user_id = requestDto.getUser_id();
        this.todo = requestDto.getTodo();
        this.password = requestDto.getPassword();
        this.startday = requestDto.getStartday();
        this.endday = requestDto.getEndday();
    }
}
