package com.sparta.calendarprojects.customexception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public enum CustomErrorCode {
    PASSWORD_EXCEPTION("비밀번호가 일치하지않습니다."),
    END_DATE_BEFORE_START_DATE("일정 종료일자가 시작일자보다 빠릅니다."),
    NULL_INPUT("입력을 하지 않은 값이 존재합니다. 모든값을 입력하세요."),
    OUT_OF_RANGE("선택한 ID 값의 일정이 존재하지 않습니다."),
    DATE_PARSE_ERROR("날짜 형태가 맞지않거나 해당날짜가 유효하지 않습니다. Ex) 2024-02-30 , YYYY-MM-DD)");


    private String statusMessage;

    CustomErrorCode(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
