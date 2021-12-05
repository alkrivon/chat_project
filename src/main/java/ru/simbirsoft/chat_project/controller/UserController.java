package ru.simbirsoft.chat_project.controller;

import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.chat_project.dto.UserDtoRequest;
import ru.simbirsoft.chat_project.dto.UserDtoResponse;

@RestController
@RequestMapping("api/user")
public class UserController {

    @PostMapping("/save")
    public UserDtoResponse saveUser(@RequestBody UserDtoRequest userDtoRequest) {
        return null;
    }

    @GetMapping("/get/{userId}")
    public UserDtoResponse getUser(@PathVariable("userId") Long userId) {
        return null;
    }

    @PutMapping("/update")
    public UserDtoResponse updateUser(@RequestBody UserDtoRequest userDtoRequest) {
        return null;
    }

    @DeleteMapping("/delete/{userId}")
    public UserDtoResponse deleteUser(@PathVariable("userId") Long userId) {
        return null;
    }
}
