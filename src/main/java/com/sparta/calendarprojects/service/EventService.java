package com.sparta.calendarprojects.service;

import com.sparta.calendarprojects.dto.EventRequestDto;
import com.sparta.calendarprojects.dto.EventResponseDto;
import com.sparta.calendarprojects.entity.Event;
import com.sparta.calendarprojects.repository.EventRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final JdbcTemplate jdbcTemplate;
    private final EventRepository eventRepository;

    public EventService(JdbcTemplate jdbcTemplate, EventRepository eventRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.eventRepository = eventRepository;
    }

    public EventResponseDto createEvent(EventRequestDto requestDto) {
        // RequestDto -> Entity
        Event event = new Event(requestDto);
        // DB 저장
        Event saveEvent = eventRepository.save(event);
        // Entity -> ResponseDto
        return new EventResponseDto(saveEvent);
    }

    public List<EventResponseDto> getEvent() {
        // DB 조회
        return eventRepository.findAll();
    }
}
