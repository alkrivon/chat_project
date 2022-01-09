package ru.simbirsoft.chat_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.chat_project.dto.UserDtoRequest;
import ru.simbirsoft.chat_project.dto.UserDtoResponse;
import ru.simbirsoft.chat_project.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/get/{userId}")
    public UserDtoResponse getUser(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/get")
    public UserDtoResponse getUserByName(@RequestParam String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/get/allUsers")
    public List<UserDtoResponse> getAllUsers() {
        return userService.getAllUser();
    }

    @PostMapping("/create")
    public UserDtoResponse saveUser(@RequestBody UserDtoRequest userDtoRequest) {
        return userService.createUser(userDtoRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{userId}")
    public UserDtoResponse updateUser(@PathVariable Long userId,
                                      @RequestBody UserDtoRequest userDtoRequest) {
        return userService.updateUser(userId, userDtoRequest);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User has been deleted", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MODERATOR')")
    @PatchMapping("/setStatus/{userId}")
    private ResponseEntity<String> setStatus(@PathVariable("userId") Long userId,
                                             @RequestParam Boolean status) {
        userService.setStatus(userId, status);
        return new ResponseEntity<>("Status has been updated", HttpStatus.OK);
    }
}
