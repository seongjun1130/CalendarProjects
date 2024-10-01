package com.sparta.calendarprojects.customexception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public enum CustomErrorCode {
    PASSWORD_EXCEPTION("비밀번호가 일치하지않습니다."),
    END_DATE_BEFORE_START_DATE("일정 종료일자가 시작일자보다 빠릅니다."),
    NULL_INPUT("입력을 하지 않은 값이 존재합니다.");

    private String statusMessage;

    CustomErrorCode(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
