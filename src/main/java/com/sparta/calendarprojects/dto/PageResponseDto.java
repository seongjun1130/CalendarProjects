package com.sparta.calendarprojects.dto;

import com.sparta.calendarprojects.info.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
// 전체 일정을 담고있는 Data 와 page Info 를 가지는 DTO
public class PageResponseDto {
    private List<EventResponseDto> data;
    private PageInfo pageInfo;
}
