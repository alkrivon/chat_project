package ru.simbirsoft.chat_project.dto;

import lombok.Data;
import ru.simbirsoft.chat_project.entities.Role;
import ru.simbirsoft.chat_project.entities.enums.Status;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class UserDtoResponse {

    private Long id;
    private String username;
    private String login;
    private Role role;
    private Status status;
    private LocalDateTime ban_start;
    private LocalTime ban_time;
}
