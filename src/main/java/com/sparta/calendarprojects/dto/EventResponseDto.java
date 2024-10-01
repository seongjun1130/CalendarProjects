package com.sparta.calendarprojects.dto;

import com.sparta.calendarprojects.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventResponseDto {
    private Long id;
    private String creator;
    private String todo;
    private java.sql.Timestamp createddate;
    private java.sql.Timestamp modifieddate;
    private java.sql.Date startday;
    private java.sql.Date endday;

    public EventResponseDto(Event event) {
        this.id = event.getId();
        this.creator = event.getCreator();
        this.todo = event.getTodo();
        this.createddate = event.getCreateddate();
        this.modifieddate = event.getModifieddate();
        this.startday = event.getStartday();
        this.endday = event.getEndday();
    }

    public EventResponseDto(Long id, String todo, Timestamp createddate, Timestamp modifieddate, Date startday, Date endday, String creator) {
        this.id = id;
        this.todo = todo;
        this.createddate = createddate;
        this.modifieddate = modifieddate;
        this.startday = startday;
        this.endday = endday;
        this.creator = creator;
    }
}
