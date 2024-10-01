package com.sparta.calendarprojects.customexception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CustomErrorResponse {
    private CustomErrorCode status;
    private String statusMessage;
    private final HttpStatus httpStatus;
}
