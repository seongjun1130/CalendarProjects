package com.sparta.calendarprojects.service;

import com.sparta.calendarprojects.dto.EventRequestDto;
import com.sparta.calendarprojects.dto.EventResponseDto;
import com.sparta.calendarprojects.entity.Event;
import com.sparta.calendarprojects.repository.EventRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// 일정관련 비즈니스 로직을 지닌 서비스 클래스
public class EventService {
    private final EventRepository eventRepository;

    // DI 생성자주입.
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // 일정 생성 메소드.
    public EventResponseDto createEvent(EventRequestDto requestDto) {
        // RequestDto -> Entity
        Event event = new Event(requestDto);
        // DB 저장
        Event saveEvent = eventRepository.save(event);
        // Entity -> ResponseDto
        return new EventResponseDto(saveEvent);
    }

    // 일정 조회 메소드. 파라미터 여부에 따른 결과가 달라짐.
    public List<EventResponseDto> getEventAll(String creator, String modifieddate) {
        return eventRepository.findAll(creator, modifieddate);
    }

    // 해당 일정 ID의 정보를 조회.
    public EventResponseDto getEvent(Long id) {
        Event event = eventRepository.findId(id);
        if (event != null) {
            return new EventResponseDto(event);
        }
        throw new IllegalArgumentException("선택한 일정은 존재하지 않습니다");
    }
}
