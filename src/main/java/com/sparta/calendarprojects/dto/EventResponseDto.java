package com.sparta.calendarprojects.dto;

import com.sparta.calendarprojects.entity.Event;
import lombok.Getter;

@Getter
public class EventResponseDto {
    private Long id;
    private String creator;
    private String todo;
    private String password;
    private java.sql.Timestamp createddate;
    private java.sql.Timestamp modifieddate;
    private java.sql.Date startday;
    private java.sql.Date endday;

    public EventResponseDto(Event event) {
        this.id = event.getId();
        this.creator = event.getCreator();
        this.todo = event.getTodo();
        this.password = event.getPassword();
        this.createddate = event.getCreateddate();
        this.modifieddate = event.getModifieddate();
        this.startday = event.getStartday();
        this.endday = event.getEndday();
    }

    public EventResponseDto(Long id, String todo, String createddate, String modifieddate, String startday, String endday, String creator) {
        super();
    }
}
