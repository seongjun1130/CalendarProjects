package com.sparta.calendarprojects.customexception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
// 사용자예외를 발생시키기 위한 예외 클래스
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
