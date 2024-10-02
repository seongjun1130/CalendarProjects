package com.sparta.calendarprojects.service;

import com.sparta.calendarprojects.customexception.CustomException;
import com.sparta.calendarprojects.dto.UserReponseDto;
import com.sparta.calendarprojects.dto.UserRequestDto;
import com.sparta.calendarprojects.entity.User;
import com.sparta.calendarprojects.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.sparta.calendarprojects.customexception.CustomErrorCode.*;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserReponseDto createEvent(UserRequestDto requestDto) {
        if (checkEmailFormat(requestDto.getEmail())) {
            User user = new User(requestDto);
            User saveUser = userRepository.save(user);
            return new UserReponseDto(saveUser);
        } else {
            throw new CustomException(INVALID_EMAIL_FORMAT);
        }
    }

    public UserReponseDto getUserById(Long id) {
        User user = userRepository.findId(id);
        if (user != null) {
            return new UserReponseDto(user);
        } else {
            throw new CustomException(OUT_OF_RANGE);
        }
    }

    public List<UserReponseDto> getAllUsers() {
        return userRepository.findAll();
    }

    public Long updateUser(Long id, UserRequestDto requestDto) {
        boolean checkNotNull = Stream.of(requestDto.getUsername()).allMatch(Objects::nonNull);
        User user = userRepository.findId(id);
        if (user != null) {
            if (checkNotNull) {
                userRepository.update(id, requestDto);
                return id;
            } else {
                throw new CustomException(NULL_INPUT);
            }
        } else {
            throw new CustomException(OUT_OF_RANGE);
        }
    }

    public Long deleteUser(Long id) {
        User user = userRepository.findId(id);
        if (user != null) {
            userRepository.delete(id);
            return id;
        } else {
            throw new CustomException(OUT_OF_RANGE);
        }
    }

    private boolean checkEmailFormat(String email) {
        if (email.matches("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$")) {
            return true;
        } else {
            return false;
        }
    }
}
