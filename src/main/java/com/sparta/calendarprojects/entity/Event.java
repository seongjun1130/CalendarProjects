package com.sparta.calendarprojects.entity;

import com.sparta.calendarprojects.dto.EventRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Event {
    private Long id;
    private String creator;
    private String todo;
    private String password;
    private java.sql.Timestamp createddate;
    private java.sql.Timestamp modifieddate;
    private java.sql.Date startday;
    private java.sql.Date endday;

    public Event(EventRequestDto requestDto) {
        this.creator = requestDto.getCreator();
        this.todo = requestDto.getTodo();
        this.password = requestDto.getPassword();
        this.createddate = requestDto.getCreateddate();
        this.modifieddate = requestDto.getModifieddate();
        this.startday = requestDto.getStartday();
        this.endday = requestDto.getEndday();
    }
}
