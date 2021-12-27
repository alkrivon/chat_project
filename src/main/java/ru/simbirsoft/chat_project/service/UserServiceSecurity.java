package ru.simbirsoft.chat_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.simbirsoft.chat_project.entities.Role;
import ru.simbirsoft.chat_project.entities.User;
import ru.simbirsoft.chat_project.entities.enums.Status;
import ru.simbirsoft.chat_project.repository.RoleRepository;
import ru.simbirsoft.chat_project.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceSecurity {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceSecurity(UserRepository userRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepository.save(user);

        return registeredUser;
    }

    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        return result;
    }

    public User findByUsername(String username) {
        User result = userRepository.findUserByUsername(username).get();
        return result;
    }

    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            return null;
        }
        return result;
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
