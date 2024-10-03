package com.sparta.calendarprojects.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private CustomErrorCode code;
    private String description;
    private String detail;

    public ErrorResponse(CustomErrorCode code, String description) {
        this.code = code;
        this.description = description;
    }
}
