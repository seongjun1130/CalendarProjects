package com.sparta.calendarprojects.customexception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
// 사용자 예외 발생시 예외를 처리해주는 Handler Class
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    // 예외 발생시 예외에 대한 DTO 객체를 생성해준다
    public CustomErrorResponse handleCustomException(CustomException e, HttpServletRequest request) {
        log.error("errorCode : {}, url : {}, message: {}, HttpStatus : {}", e.getCustomErrorCode(), request.getRequestURL(), e.getDetailMessage(), e.getHttpStatus());
        return CustomErrorResponse.builder().status(e.getCustomErrorCode()).statusMessage(e.getDetailMessage()).httpStatus(e.getHttpStatus()).build();
    }
}
