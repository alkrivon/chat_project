package ru.simbirsoft.chat_project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.simbirsoft.chat_project.entities.User;
import ru.simbirsoft.chat_project.repository.UserRepository;
import ru.simbirsoft.chat_project.service.UserServiceSecurity;

@Service
public class JwtUserDetailService implements UserDetailsService {

    private final UserServiceSecurity userServiceSecurity;

    @Autowired
    public JwtUserDetailService(UserServiceSecurity userServiceSecurity) {
        this.userServiceSecurity = userServiceSecurity;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userServiceSecurity.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        return jwtUser;
    }
}
