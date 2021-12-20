package ru.simbirsoft.chat_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.chat_project.dto.UserDtoRequest;
import ru.simbirsoft.chat_project.dto.UserDtoResponse;
import ru.simbirsoft.chat_project.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public UserDtoResponse saveUser(@RequestBody UserDtoRequest userDtoRequest) {
        return userService.saveUser(userDtoRequest);
    }

    @GetMapping("/get/byId/{userId}")
    public UserDtoResponse getUser(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/get/byName/{userName}")
    public UserDtoResponse getUserByName(@PathVariable String userName) {
        return userService.getUserByName(userName);
    }

    @GetMapping("/get/allUsers")
    public List<UserDtoResponse> getAllUsers() {
        return userService.getAllUser();
    }

    @PutMapping("/update/{userId}")
    public UserDtoResponse updateUser(@PathVariable Long userId,
                                      @RequestBody UserDtoRequest userDtoRequest) {
        return userService.updateUser(userId, userDtoRequest);
    }

    @DeleteMapping("/delete/{userId}")
    public String deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return "User with id = " + userId + " has been deleted";
    }
}
