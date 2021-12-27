package ru.simbirsoft.chat_project.controller;
;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.simbirsoft.chat_project.dto.AdminUserDto;
import ru.simbirsoft.chat_project.entities.User;
import ru.simbirsoft.chat_project.repository.UserRepository;

@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminRestController {

    private final UserRepository userRepository;

    @Autowired
    public AdminRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "users/{id}")
    public ResponseEntity<AdminUserDto> getUserById(@PathVariable(name = "id") Long id) {
        User user = userRepository.findUserById(id).get();

        AdminUserDto result = AdminUserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
