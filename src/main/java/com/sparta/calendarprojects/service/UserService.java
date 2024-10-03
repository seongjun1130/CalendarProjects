package com.sparta.calendarprojects.service;

import com.sparta.calendarprojects.exception.CustomException;
import com.sparta.calendarprojects.dto.UserReponseDto;
import com.sparta.calendarprojects.dto.UserRequestDto;
import com.sparta.calendarprojects.entity.User;
import com.sparta.calendarprojects.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sparta.calendarprojects.exception.CustomErrorCode.*;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // User Data 저장 서비스 메소드
    public UserReponseDto createEvent(UserRequestDto requestDto) {
        User user = new User(requestDto);
        User saveUser = userRepository.save(user);
        return new UserReponseDto(saveUser);
    }

    // 단일 User Data 조회 서비스 메소드
    public UserReponseDto getUserById(Long id) {
        User user = userRepository.findId(id);
        // 해당 User 가 DB상 존재하는지 확인
        if (user != null) {
            return new UserReponseDto(user);
        } else {
            throw new CustomException(OUT_OF_RANGE);
        }
    }

    // 모든 User Data 조회 서비스 메소드
    public List<UserReponseDto> getAllUsers() {
        return userRepository.findAll();
    }

    // 단일 User Data 변경 서비스 메소드
    public Long updateUser(Long id, String username) {
        User user = userRepository.findId(id);
        // 해당 User 가 DB상 존재하는지 확인
        if (user != null) {
            // NULL 값을 전송했는지 확인
            userRepository.update(id, username);
            return id;
        } else {
            throw new CustomException(OUT_OF_RANGE);
        }
    }

    // 단일 User Data 삭제 서비스 메소드
    public Long deleteUser(Long id) {
        User user = userRepository.findId(id);
        // 해당 User 가 DB상 존재하는지 확인
        if (user != null) {
            userRepository.delete(id);
            return id;
        } else {
            throw new CustomException(OUT_OF_RANGE);
        }
    }
}
