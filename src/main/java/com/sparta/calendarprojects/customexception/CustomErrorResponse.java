package com.sparta.calendarprojects.customexception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@Builder
// Client 에게 예외를 보내주기 위한 DTO 객체
public class CustomErrorResponse {
    private CustomErrorCode status;
    private String statusMessage;
    private final HttpStatus httpStatus;
}
