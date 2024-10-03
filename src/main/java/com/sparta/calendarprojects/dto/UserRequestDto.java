package com.sparta.calendarprojects.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class UserRequestDto {
    @Positive
    private Long id;
    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 1, max = 5, message = "최소 1 글자 최대 5글자로 입력해주세요.")
    private String username;
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 맞지 않습니다.")
    private String email;
    private java.sql.Timestamp join_date;
    private java.sql.Timestamp update_date;
}
