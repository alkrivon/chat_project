package ru.simbirsoft.chat_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.chat_project.dto.UserDtoRequest;
import ru.simbirsoft.chat_project.dto.UserDtoResponse;
import ru.simbirsoft.chat_project.entities.Role;
import ru.simbirsoft.chat_project.entities.User;
import ru.simbirsoft.chat_project.exception.AuthException;
import ru.simbirsoft.chat_project.exception.NotFoundException;
import ru.simbirsoft.chat_project.mappers.UserMapper;
import ru.simbirsoft.chat_project.repository.RoleRepository;
import ru.simbirsoft.chat_project.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserDtoResponse getUserById(Long id) throws NotFoundException {
       Optional<User> user = userRepository.findUserById(id);
        if (user.isPresent()) {
            return UserMapper.INSTANCE.userToUserDto(user.get());
        }
        throw new NotFoundException("There is no user with id = " + id);
    }

    @Transactional(readOnly = true)
    public UserDtoResponse getUserByUsername(String username) throws NotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return UserMapper.INSTANCE.userToUserDto(user.get());
        }
        throw new NotFoundException("There is no user with name = " + username);
    }

    @Transactional(readOnly = true)
    public List<UserDtoResponse> getAllUser() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper.INSTANCE::userToUserDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDtoResponse createUser(UserDtoRequest userDtoRequest) {
        User user = userRepository.save(UserMapper.INSTANCE.userDtoToUser(userDtoRequest));
        return UserMapper.INSTANCE.userToUserDto(user);
    }

    @Transactional
    public UserDtoResponse updateUser(Long id, UserDtoRequest userDtoRequest) throws NotFoundException {
        Optional<User> userOptional = userRepository.findUserById(id);
        if (userOptional.isPresent()) {
            User user = UserMapper.INSTANCE.userDtoToUser(userDtoRequest);
            user.setId(id);
            userRepository.save(user);
            return UserMapper.INSTANCE.userToUserDto(user);
        }
        throw new NotFoundException("There is no user with id = " + id);
    }

    @Transactional
    public void deleteUser(Long id) throws NotFoundException {
        Optional<User> userOptional = userRepository.findUserById(id);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
        }
        throw new NotFoundException("There is no user with id = " + id);
    }

    @Transactional
    public void setStatus(Long id, Boolean status) {
        userRepository.setStatus(id, status);
    }

    public User saveUser(User user) {
        Role role = roleRepository.findByName("ROLE_USER");
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login).get();
    }

    public User findByLoginAndPassword(String login, String password) throws AuthException {
        User user = findByLogin(login);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        throw new AuthException("Invalid username or password!");
    }
}
