package ru.simbirsoft.chat_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.chat_project.dto.UserDtoRequest;
import ru.simbirsoft.chat_project.dto.UserDtoResponse;
import ru.simbirsoft.chat_project.entities.Role;
import ru.simbirsoft.chat_project.exception.NotFoundException;
import ru.simbirsoft.chat_project.repository.RoleRepository;
import ru.simbirsoft.chat_project.service.UserService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @GetMapping("/get/{userId}")
    public UserDtoResponse getUser(@PathVariable Long userId) throws NotFoundException {
        return userService.getUserById(userId);
    }

    @GetMapping("/get")
    public UserDtoResponse getUserByName(@RequestParam String username) throws NotFoundException {
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

    @PutMapping("/update/{userId}")
    public UserDtoResponse updateUser(@PathVariable Long userId,
                                      @RequestBody UserDtoRequest userDtoRequest) throws NotFoundException {
        return userService.updateUser(userId, userDtoRequest);
    }


    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId) throws NotFoundException {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User has been deleted", HttpStatus.OK);
    }

    @PatchMapping("/setRole/{userId}")
    public ResponseEntity<String> setRole(@PathVariable("userId") Long userId,
                                    @RequestParam Long roleId) {
        Role role = roleRepository.findById(roleId).get();
        userService.setRole(userId, role);
        return new ResponseEntity<>("Role has been updated", HttpStatus.OK);
    }

    @PatchMapping("/setStatus/{userId}")
    public ResponseEntity<String> setStatus(@PathVariable("userId") Long userId,
                                             @RequestParam Boolean status) {
        userService.setStatus(userId, status);
        return new ResponseEntity<>("Status has been updated", HttpStatus.OK);
    }
}
