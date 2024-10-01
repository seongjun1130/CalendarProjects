package com.sparta.calendarprojects.controller;

import com.sparta.calendarprojects.dto.EventRequestDto;
import com.sparta.calendarprojects.dto.EventResponseDto;
import com.sparta.calendarprojects.service.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;


// 일정 관련 정보를 조회하는 컨트롤러.
@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    // DI 생성자주입
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // 일정을 생성하는 컨트롤러 메소드
    @PostMapping("/schedul")
    public EventResponseDto createEvent(@RequestBody EventRequestDto requestDto) {
        return eventService.createEvent(requestDto);
    }

    // 일정을 조회하는 컨트롤러 메소드 정보 기입여부에 따른 출력이 달라짐.
    @GetMapping("/schedul/")
    public List<EventResponseDto> getAllEvents(@RequestParam(required = false) String creator, @RequestParam(required = false) String modifieddate) {
        return eventService.getEventAll(creator, modifieddate);
    }

    // ID 값을 기준으로 정보를 조회하는 컨트롤러 메소드.
    @GetMapping("/schedul/id/")
    public EventResponseDto getEventById(@RequestParam Long id) {
        return eventService.getEventById(id);
    }

    // ID 값을 기준으로 정보를 수정하는 컨트롤러 메소드
    // DTO 를 Body 로 받는 이유는 URL 이 아닌 Body 로 받으면서 보안성 향상.
    @PutMapping("/schedul/")
    public Long updateEvent(@RequestParam Long id, @RequestBody EventRequestDto requestDto) {
        return eventService.updateEvent(id, requestDto);
    }

    // ID 값을 기준으로 정보를 삭제하는 컨트롤러 메소드
    // DTO 를 Body 로 받는 이유는 URL 이 아닌 Body 로 받으면서 보안성 향상.
    @DeleteMapping("/schedul/")
    public Long deleteEvent(@RequestParam Long id, @RequestBody EventRequestDto requestDto) {
        return eventService.deleteEvent(id, requestDto);
    }
}
