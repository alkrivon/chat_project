package ru.simbirsoft.chat_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.simbirsoft.chat_project.entities.User;
import ru.simbirsoft.chat_project.security.AuthRequest;
import ru.simbirsoft.chat_project.security.AuthResponse;
import ru.simbirsoft.chat_project.security.RegistrationRequest;
import ru.simbirsoft.chat_project.security.jwt.JwtTokenProvider;
import ru.simbirsoft.chat_project.service.UserService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public String registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        User user = new User();
        user.setPassword(registrationRequest.getPassword());
        user.setLogin(registrationRequest.getLogin());
        userService.saveUser(user);
        return "OK";
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        User user = userService.findByLoginAndPassword(request.getLogin(),
                                                        request.getPassword());
        String token = jwtTokenProvider.generateToken(user.getLogin());
        return new AuthResponse(token);
    }
}
