package com.sparta.calendarprojects.mapper;

import com.sparta.calendarprojects.dto.EventResponseDto;
import com.sparta.calendarprojects.entity.Event;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    // Mapper 를 DI 받아 Entity 와 Dto 를 맵핑 해주는 역할
    List<EventResponseDto> eventsToEventResponseDtos(List<Event> events);
}
