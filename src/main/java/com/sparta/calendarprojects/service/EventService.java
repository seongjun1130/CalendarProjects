package com.sparta.calendarprojects.service;

import com.sparta.calendarprojects.customexception.CustomException;
import com.sparta.calendarprojects.dto.EventRequestDto;
import com.sparta.calendarprojects.dto.EventResponseDto;
import com.sparta.calendarprojects.entity.Event;
import com.sparta.calendarprojects.entity.User;
import com.sparta.calendarprojects.repository.EventRepository;
import com.sparta.calendarprojects.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.sparta.calendarprojects.customexception.CustomErrorCode.*;


// 일정관련 비즈니스 로직을 지닌 서비스 클래스
@Service
public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    // DI 생성자주입.
    public EventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    // 일정 생성 메소드.
    public EventResponseDto createEvent(EventRequestDto requestDto) {
        boolean checkNotNull = Stream.of(requestDto.getTodo(), requestDto.getUser_id(), requestDto.getPassword(), requestDto.getStartday(), requestDto.getEndday()).allMatch(Objects::nonNull);
        User userinfo = userRepository.findId(requestDto.getUser_id());
        if (userinfo != null) {
            if (checkNotNull) {
                if (formatCheck(String.valueOf(requestDto.getStartday())) && formatCheck(String.valueOf(requestDto.getEndday()))) {
                    if (requestDto.getStartday().before(requestDto.getEndday())) {
                        // RequestDto -> Entity
                        Event event = new Event(requestDto);
                        // DB 저장
                        Event saveEvent = eventRepository.save(event, userinfo);
                        // Entity -> ResponseDto
                        return new EventResponseDto(saveEvent);
                    } else {
                        throw new CustomException(END_DATE_BEFORE_START_DATE);
                    }
                } else {
                    throw new CustomException(DATE_PARSE_ERROR);
                }
            } else {
                throw new CustomException(NULL_INPUT);
            }
        } else {
            throw new CustomException(USER_NOT_FOUND);
        }
    }

    // 일정 조회 메소드. 파라미터 여부에 따른 결과가 달라짐.
    public List<EventResponseDto> getEventAll(Long user_id, String modifieddate) {
        return eventRepository.findAll(user_id, modifieddate);
    }

    // 해당 일정 ID의 정보 조회 메소드.
    public EventResponseDto getEventById(Long id) {
        Event event = eventRepository.findId(id);
        if (event != null) {
            return new EventResponseDto(event);
        } else {
            throw new CustomException(OUT_OF_RANGE);
        }
    }

    // ID 기준의 DB 수정 메소드
    public Long updateEvent(Long id, EventRequestDto requestDto) {
        boolean checkNotNull = Stream.of(requestDto.getTodo(), requestDto.getUser_id(), requestDto.getPassword(), requestDto.getStartday(), requestDto.getEndday()).allMatch(Objects::nonNull);
        Event event = eventRepository.findId(id);
        User userinfo = userRepository.findId(requestDto.getUser_id());
        // 해당 ID가 존재하면서 게시글 비밀번호와 동일한 시 정보 수정
        if (event != null) {
            if (checkNotNull) {
                if (requestDto.getPassword().equals(event.getPassword()) && userinfo.getId().equals(event.getUser_id())) {
                    if (formatCheck(String.valueOf(requestDto.getStartday())) && formatCheck(String.valueOf(requestDto.getEndday()))) {
                        if (requestDto.getStartday().before(requestDto.getEndday())) {
                            eventRepository.update(id, requestDto);
                            return id;
                        } else {
                            throw new CustomException(END_DATE_BEFORE_START_DATE);
                        }
                    } else {
                        throw new CustomException(DATE_PARSE_ERROR);
                    }
                } else {
                    throw new CustomException(USER_INFO_MISMATCH);
                }
            } else {
                throw new CustomException(NULL_INPUT);
            }
        } else {
            throw new CustomException(OUT_OF_RANGE);
        }
    }

    // ID 기준의 DB 삭제 메소드
    public Long deleteEvent(Long id, EventRequestDto requestDto) {
        Event event = eventRepository.findId(id);
        User userinfo = userRepository.findId(requestDto.getUser_id());
        // 해당 ID가 존재하면서 게시글 비밀번호와 동일한 시 정보 삭제
        if (event != null)
            if (requestDto.getPassword().equals(event.getPassword()) && userinfo.getId().equals(event.getUser_id())) {
                eventRepository.delete(id);
                return id;
            } else {
                throw new CustomException(USER_INFO_MISMATCH);
            }
        else {
            throw new CustomException(OUT_OF_RANGE);
        }
    }

    // 해당 일자가 유효한지 검사 (Ex : 2024-02-30(false))
    private boolean formatCheck(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            System.out.println("날짜 형식에 맞지 않음 : " + date);
            e.printStackTrace();
            return false;
        }
    }
}
