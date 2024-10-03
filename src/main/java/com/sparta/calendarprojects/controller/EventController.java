package com.sparta.calendarprojects.controller;

import com.sparta.calendarprojects.dto.EventRequestDto;
import com.sparta.calendarprojects.dto.EventResponseDto;
import com.sparta.calendarprojects.dto.PageResponseDto;
import com.sparta.calendarprojects.entity.Event;
import com.sparta.calendarprojects.info.PageInfo;
import com.sparta.calendarprojects.mapper.EventMapper;
import com.sparta.calendarprojects.service.EventService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.util.List;


// 일정 관련 정보를 조회하는 컨트롤러.
@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    // DI 생성자주입
    public EventController(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    // Paging 기능을 이용한 전체일정 조회 컨트롤러 메소드
    @GetMapping("/schedul/page/")
    public ResponseEntity getAllEventsPage(@Positive @RequestParam int page, @Positive @RequestParam int size) {
        Page<Event> eventsPage = eventService.findPageEvents(page - 1, size);
        PageInfo pageInfo = new PageInfo(page, size, (int) eventsPage.getTotalElements(), eventsPage.getTotalPages());
        List<Event> events = eventsPage.getContent();
        List<EventResponseDto> respones = eventMapper.eventsToEventResponseDtos(events);
        return new ResponseEntity<>(new PageResponseDto(respones, pageInfo), HttpStatus.OK);
    }

    // 일정을 생성하는 컨트롤러 메소드
    @PostMapping("/schedul/")
    public EventResponseDto createEvent(@RequestBody @Valid EventRequestDto requestDto) {
        return eventService.createEvent(requestDto);
    }

    // 일정을 조회하는 컨트롤러 메소드 정보 기입여부에 따른 출력이 달라짐.
    @GetMapping("/schedul/")
    public List<EventResponseDto> getAllEvents(@RequestParam(required = false) Long user_id, @RequestParam(required = false) String modifieddate) {
        return eventService.getEventAll(user_id, modifieddate);
    }

    // ID 값을 기준으로 정보를 조회하는 컨트롤러 메소드.
    @GetMapping("/schedul/id/")
    public EventResponseDto getEventById(@RequestParam Long id) {
        return eventService.getEventById(id);
    }

    // ID 값을 기준으로 정보를 수정하는 컨트롤러 메소드
    // DTO 를 Body 로 받는 이유는 URL 이 아닌 Body 로 받으면서 보안성 향상.
    @PutMapping("/schedul/")
    public Long updateEvent(@RequestParam Long id, @RequestBody @Valid EventRequestDto requestDto) {
        return eventService.updateEvent(id, requestDto);
    }

    // ID 값을 기준으로 정보를 삭제하는 컨트롤러 메소드
    // DTO 를 Body 로 받는 이유는 URL 이 아닌 Body 로 받으면서 보안성 향상.
    @DeleteMapping("/schedul/")
    public Long deleteEvent(@RequestParam Long id, @RequestBody EventRequestDto requestDto) {
        return eventService.deleteEvent(id, requestDto);
    }
}
