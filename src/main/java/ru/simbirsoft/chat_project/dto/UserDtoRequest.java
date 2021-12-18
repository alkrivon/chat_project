package ru.simbirsoft.chat_project.dto;

import lombok.Data;
import ru.simbirsoft.chat_project.entities.enums.Role;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class UserDtoRequest {

    private String name;
    private String login;
    private String password;
    private Role role;
    private boolean ban_status;
    private LocalDateTime ban_start;
    private LocalTime ban_time;
}
