package com.sparta.calendarprojects.info;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
// Page 의 정보를 가지고 있는 Info 객체
public class PageInfo {
    private int page;
    private int size;
    private int totalElements;
    private int totalPages;
}
