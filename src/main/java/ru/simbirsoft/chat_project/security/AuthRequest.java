package ru.simbirsoft.chat_project.security;

import lombok.Data;

@Data
public class AuthRequest {
    private String login;
    private String password;
}
