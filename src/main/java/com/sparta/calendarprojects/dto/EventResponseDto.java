package com.sparta.calendarprojects.dto;

import com.sparta.calendarprojects.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventResponseDto {
    private Long id;
    private Long user_id;
    private String username;
    private String todo;
    private java.sql.Timestamp createddate;
    private java.sql.Timestamp modifieddate;
    private java.sql.Date startday;
    private java.sql.Date endday;

    public EventResponseDto(Event event) {
        this.id = event.getId();
        this.user_id = event.getUser_id();
        this.username = event.getUsername();
        this.todo = event.getTodo();
        this.createddate = event.getCreateddate();
        this.modifieddate = event.getModifieddate();
        this.startday = event.getStartday();
        this.endday = event.getEndday();
    }

    public EventResponseDto(Long id, String username, String todo, Timestamp createddate, Timestamp modifieddate, Date startday, Date endday, Long user_id) {
        this.id = id;
        this.todo = todo;
        this.createddate = createddate;
        this.modifieddate = modifieddate;
        this.startday = startday;
        this.endday = endday;
        this.user_id = user_id;
        this.username = username;
    }
}
