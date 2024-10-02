package com.sparta.calendarprojects.controller;

import com.sparta.calendarprojects.dto.UserReponseDto;
import com.sparta.calendarprojects.dto.UserRequestDto;
import com.sparta.calendarprojects.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add/")
    public UserReponseDto createUser(@RequestBody UserRequestDto requestDto) {
        return userService.createEvent(requestDto);
    }

    @GetMapping("/read/")
    public List<UserReponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/read/id/")
    public UserReponseDto getUserById(@RequestParam Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/update/")
    public Long updateUser(@RequestParam Long id, @RequestBody UserRequestDto requestDto) {
        return userService.updateUser(id, requestDto);
    }

    @DeleteMapping("/delete/")
    public Long deleteUser(@RequestParam Long id) {
        return userService.deleteUser(id);
    }
}
