package com.sparta.calendarprojects.controller;

import com.sparta.calendarprojects.dto.UserReponseDto;
import com.sparta.calendarprojects.dto.UserRequestDto;
import com.sparta.calendarprojects.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // User Data 생성 메소드
    @PostMapping("/add/")
    public UserReponseDto createUser(@RequestBody @Valid UserRequestDto requestDto) {
        return userService.createEvent(requestDto);
    }

    // 모든 User Data 조회 메소드
    @GetMapping("/read/")
    public List<UserReponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    // 단일 User Data 조회 메소드 (ID 값 기준)
    @GetMapping("/read/id/")
    public UserReponseDto getUserById(@RequestParam("id") @Positive Long id) {
        return userService.getUserById(id);
    }

    // 단일 User Data 변경 메소드 (ID값 기준)
    @PutMapping("/update/")
    public Long updateUser(@RequestParam("id") @Positive(message = "변경 ID를 입력해주세요.") Long id,
                           @RequestParam("username") @NotBlank @Size(min = 1, max = 5, message = "1 ~ 5 자리로 입력해주세요") String username) {
        return userService.updateUser(id, username);
    }

    // 단일 User Data 삭제 메소드 (ID값 기준)
    @DeleteMapping("/delete/")
    public Long deleteUser(@RequestParam @Positive Long id) {
        return userService.deleteUser(id);
    }
}
