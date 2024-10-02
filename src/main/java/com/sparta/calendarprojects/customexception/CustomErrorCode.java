package com.sparta.calendarprojects.customexception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public enum CustomErrorCode {
    // 예외 처리를 위한 eunm 객체 속성 생성.
    USER_INFO_MISMATCH("유저정보가 일치하지않습니다."),
    END_DATE_BEFORE_START_DATE("일정 종료일자가 시작일자보다 빠릅니다."),
    NULL_INPUT("입력을 하지 않은 값이 존재합니다. 모든값을 입력하세요."),
    OUT_OF_RANGE("선택한 ID 값이 존재하지 않습니다."),
    DATE_PARSE_ERROR("날짜 형태가 맞지않거나 해당날짜가 유효하지 않습니다. Ex) 2024-02-30 , YYYY-MM-DD)"),
    INVALID_EMAIL_FORMAT("이메일 형태가 올바르지 않습니다."),
    USER_NOT_FOUND("해당 유저가 존재하지 않습니다.");

    private String statusMessage;

    // 예외 발생시 Message 출력을 위한 생성자
    CustomErrorCode(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
