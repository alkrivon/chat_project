package ru.simbirsoft.chat_project.dto;

import lombok.Data;
import ru.simbirsoft.chat_project.entities.Room;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class UserDtoRequest {

    private String username;
    private String login;
    private String password;
    private Long role;
    private Boolean status;
    private LocalDateTime ban_start;
    private LocalTime ban_time;
}
