package ru.simbirsoft.chat_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.chat_project.dto.UserDtoRequest;
import ru.simbirsoft.chat_project.dto.UserDtoResponse;
import ru.simbirsoft.chat_project.entities.User;
import ru.simbirsoft.chat_project.exception.UserNotFoundException;
import ru.simbirsoft.chat_project.mappers.UserMapper;
import ru.simbirsoft.chat_project.repository.RoleRepository;
import ru.simbirsoft.chat_project.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserDtoResponse getUserById(Long id) {
       Optional<User> user = userRepository.findUserById(id);
       if (user.isPresent()) {
           return UserMapper.INSTANCE.userToUserDto(user.get());
       }
       throw new UserNotFoundException("There is no user with id = " + id);
    }
    @Transactional(readOnly = true)
    public UserDtoResponse getUserByUsername(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isPresent()) {
            return UserMapper.INSTANCE.userToUserDto(user.get());
        }
        throw new UserNotFoundException("There is no user with name = " + username);
    }
    @Transactional(readOnly = true)
    public List<UserDtoResponse> getAllUser() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper.INSTANCE::userToUserDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public UserDtoResponse saveUser(UserDtoRequest userDtoRequest) {
        User user = userRepository.save(UserMapper.INSTANCE.userDtoToUser(userDtoRequest));
        return UserMapper.INSTANCE.userToUserDto(user);
    }
    @Transactional
    public UserDtoResponse updateUser(Long id, UserDtoRequest userDtoRequest) {
        Optional<User> userOptional = userRepository.findUserById(id);
        if (userOptional.isPresent()) {
            User user = UserMapper.INSTANCE.userDtoToUser(userDtoRequest);
            user.setId(id);
            userRepository.save(user);
            return UserMapper.INSTANCE.userToUserDto(user);
        }
        throw new UserNotFoundException("There is no user with id = " + id);
    }
    @Transactional
    public void deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findUserById(id);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
        } else {
            throw new UserNotFoundException("There is no user with id = " + id);
        }
    }
}
