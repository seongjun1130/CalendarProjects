package com.sparta.calendarprojects.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NonNull
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private Long id;
    private String username;
    private String email;
    private java.sql.Timestamp join_date;
    private java.sql.Timestamp update_date;
}
