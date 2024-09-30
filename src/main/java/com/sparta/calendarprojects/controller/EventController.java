package com.sparta.calendarprojects.controller;

import com.sparta.calendarprojects.dto.EventRequestDto;
import com.sparta.calendarprojects.dto.EventResponseDto;
import com.sparta.calendarprojects.service.EventService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {
    private final JdbcTemplate jdbcTemplate;
    private final EventService eventService;

    public EventController(JdbcTemplate jdbcTemplate, EventService eventService) {
        this.jdbcTemplate = jdbcTemplate;
        this.eventService = eventService;
    }

    @PostMapping("/schedul")
    public EventResponseDto createEvent(@RequestBody EventRequestDto requestDto) {
        return eventService.createEvent(requestDto);
    }

    @GetMapping("/schedul")
    public List<EventResponseDto> getAllEvents() {
        return eventService.getEvent();
    }
}
