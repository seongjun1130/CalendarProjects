package com.sparta.calendarprojects.service;

import com.sparta.calendarprojects.exception.CustomException;
import com.sparta.calendarprojects.dto.EventRequestDto;
import com.sparta.calendarprojects.dto.EventResponseDto;
import com.sparta.calendarprojects.entity.Event;
import com.sparta.calendarprojects.entity.User;
import com.sparta.calendarprojects.repository.EventPageRepository;
import com.sparta.calendarprojects.repository.EventRepository;
import com.sparta.calendarprojects.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.sparta.calendarprojects.exception.CustomErrorCode.*;


// 일정관련 비즈니스 로직을 지닌 서비스 클래스
@Service
public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventPageRepository eventPageRepository;

    // DI 생성자주입.
    public EventService(EventRepository eventRepository, UserRepository userRepository, EventPageRepository eventPageRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.eventPageRepository = eventPageRepository;
    }

    // 전달받은 page,size 파라미터를 적용하여 객체를 생성
    public Page<Event> findPageEvents(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return eventPageRepository.findAllByOrderByIdDesc(pageRequest);
    }

    // 일정 생성 메소드.
    public EventResponseDto createEvent(EventRequestDto requestDto) {
        User userinfo = userRepository.findId(requestDto.getUser_id());
        // 해당 유저가 존재하지 않는 경우
        if (userinfo != null) {
            // 해당 일자가 유효한지 검사 (Ex : 2024-02-30(false))
            if (formatCheck(String.valueOf(requestDto.getStartday())) && formatCheck(String.valueOf(requestDto.getEndday()))) {
                // 일정종료일이 시작일 보다 빠를경우 (Ex : 2024-09-30 ~ 2024-08-30)
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
        // 해당 일정이 존재하지 않을 경우
        if (event != null) {
            return new EventResponseDto(event);
        } else {
            throw new CustomException(OUT_OF_RANGE);
        }
    }

    // ID 기준의 DB 수정 메소드
    public Long updateEvent(Long id, EventRequestDto requestDto) {
        Event event = eventRepository.findId(id);
        // 글을 작성한 유저가 접근했는지 검사하기위한 UserInfo 객체 생성
        User userinfo = userRepository.findId(requestDto.getUser_id());
        // 해당 ID 일정이 존재하는 경우
        if (event != null || userinfo != null) {
            // 접근 User 가 일정을 작성한 유저 이면서 게시글에 대한 비밀번호가 일치하는지?
            if (requestDto.getPassword().equals(event.getPassword()) && userinfo.getId().equals(event.getUser_id())) {
                // 해당 일자가 유효한지 검사 (Ex : 2024-02-30(false))
                if (formatCheck(String.valueOf(requestDto.getStartday())) && formatCheck(String.valueOf(requestDto.getEndday()))) {
                    // 일정종료일이 시작일 보다 빠를경우 (Ex : 2024-09-30 ~ 2024-08-30)
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
            throw new CustomException(OUT_OF_RANGE);
        }
    }

    // ID 기준의 DB 삭제 메소드
    public Long deleteEvent(Long id, EventRequestDto requestDto) {
        Event event = eventRepository.findId(id);
        User userinfo = userRepository.findId(requestDto.getUser_id());
        // 해당 일정 ID가 존재하는지?
        if (event != null)
            // 접근한 User 가 일정을 작성한 User 이면서 비밀번호가 일치하는지?
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
