package ru.simbirsoft.chat_project.dto;

import lombok.Data;
import ru.simbirsoft.chat_project.entities.Room;
import ru.simbirsoft.chat_project.entities.enums.Role;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Data
public class UserDtoResponse {

    private Long id;
    private String name;
    private String login;
    private Role role;
    private boolean ban_status;
    private LocalDateTime ban_start;
    private LocalTime ban_time;
}
