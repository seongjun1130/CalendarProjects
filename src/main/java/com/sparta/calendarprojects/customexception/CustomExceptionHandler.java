package com.sparta.calendarprojects.customexception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public CustomErrorResponse handleCustomException(CustomException e, HttpServletRequest request) {
        log.error("errorCode : {}, url : {}, message: {}, HttpStatus : {}", e.getCustomErrorCode(), request.getRequestURL(), e.getDetailMessage(), e.getHttpStatus());
        return CustomErrorResponse.builder().status(e.getCustomErrorCode()).statusMessage(e.getDetailMessage()).httpStatus(e.getHttpStatus()).build();
    }
}
