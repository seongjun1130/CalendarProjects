package com.sparta.calendarprojects.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class EventRequestDto {
    @Positive
    private Long id;
    @NotNull(message = "유저 ID를 입력해주세요.")
    @Positive
    private Long user_id;
    @NotBlank(message = "일정을 입력해주세요.")
    @Size(min = 1, max = 500, message = "일정은 최소 1글자 최대 500글자 입력가능")
    private String todo;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
    private java.sql.Timestamp createddate;
    private java.sql.Timestamp modifieddate;
    @NotNull(message = "일정시작일을 입력해주세요.")
    private java.sql.Date startday;
    @NotNull(message = "일정종료일을 입력해주세요.")
    private java.sql.Date endday;
}
