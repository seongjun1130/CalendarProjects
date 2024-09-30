package com.sparta.calendarprojects.dto;

import lombok.Getter;

@Getter
public class EventRequestDto {
    private Long id;
    private String creator;
    private String todo;
    private String password;
    private java.sql.Timestamp createddate;
    private java.sql.Timestamp modifieddate;
    private java.sql.Date startday;
    private java.sql.Date endday;
}
