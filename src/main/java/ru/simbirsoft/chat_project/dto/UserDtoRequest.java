package ru.simbirsoft.chat_project.dto;

import lombok.Data;
import ru.simbirsoft.chat_project.entities.Role;
import ru.simbirsoft.chat_project.entities.enums.Status;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
public class UserDtoRequest {

    private String username;
    private String login;
    private String password;
//    private Role role;
    private Status status;
    private LocalDateTime ban_start;
    private LocalTime ban_time;
    private Date updated;
}
