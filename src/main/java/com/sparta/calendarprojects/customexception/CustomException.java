package com.sparta.calendarprojects.customexception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomException extends RuntimeException {
    private CustomErrorCode customErrorCode;
    private String detailMessage;
    private HttpStatus httpStatus;

    public CustomException(CustomErrorCode customErrorCode) {
        super(customErrorCode.getStatusMessage());
        this.customErrorCode = customErrorCode;
        this.detailMessage = customErrorCode.getStatusMessage();
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

}
