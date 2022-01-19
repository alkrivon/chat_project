package ru.simbirsoft.chat_project.dto;

import lombok.Data;

import java.time.LocalDateTime;



@Data
public class UserDtoResponse {

    private Long id;
    private String username;
    private String login;
    private Long role;
    private Boolean status;
    private LocalDateTime ban_end;
}
