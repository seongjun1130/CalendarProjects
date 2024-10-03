package com.sparta.calendarprojects.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
// 사용자 예외 발생시 예외를 처리해주는 Handler Class
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    // 예외 발생시 예외에 대한 DTO 객체를 생성해준다
    public ErrorResponse handleCustomException(CustomException e, HttpServletRequest request) {
        log.error("errorCode : {}, url : {}, message: {}", e.getCustomErrorCode(), request.getRequestURL(), e.getDetailMessage());
        return new ErrorResponse(e.getCustomErrorCode(), e.getDetailMessage(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.warn("MethodArgumentNotValidException 발생!!! url:{}, trace:{}", request.getRequestURI(), e.getStackTrace());
        ErrorResponse errorResponse = makeErrorResponse(e.getBindingResult());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse makeErrorResponse(BindingResult bindingResult) {
        CustomErrorCode code = null;
        String description = "";
        String detail = "";
        if (bindingResult.hasErrors()) {
            //DTO에 설정한 meaasge값을 가져온다
            detail = bindingResult.getFieldError().getDefaultMessage();

            //DTO에 유효성체크를 걸어놓은 어노테이션명을 가져온다.
            String bindResultCode = bindingResult.getFieldError().getCode();

            switch (bindResultCode) {
                case "NotBlank":
                    code = CustomErrorCode.NULL_BLANK_INPUT;
                    description = CustomErrorCode.NULL_BLANK_INPUT.getStatusMessage();
                    break;
                case "NotNull":
                    code = CustomErrorCode.NULL_BLANK_INPUT;
                    description = CustomErrorCode.NULL_BLANK_INPUT.getStatusMessage();
                    break;
                case "Email":
                    code = CustomErrorCode.INVALID_EMAIL_FORMAT;
                    description = CustomErrorCode.INVALID_EMAIL_FORMAT.getStatusMessage();
                    break;
                case "Size":
                    code = CustomErrorCode.INPUT_OUT_OF_BOUNDS;
                    description = CustomErrorCode.INPUT_OUT_OF_BOUNDS.getStatusMessage();
                    break;
            }
        }
        return new ErrorResponse(code, description, detail);
    }
}
