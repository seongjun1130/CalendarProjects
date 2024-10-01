package com.sparta.calendarprojects.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NonNull
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
