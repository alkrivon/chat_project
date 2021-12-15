package ru.simbirsoft.chat_project.dto;

import ru.simbirsoft.chat_project.entities.enums.Role;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class UserDtoRequest {

    private String name;
    private String login;
    private String password;
    private Role role;
    private boolean ban_status;
    private LocalDateTime ban_start;
    private LocalTime ban_time;

    public UserDtoRequest(String name, String login,
                          String password, Role role, boolean ban_status,
                          LocalDateTime ban_start, LocalTime ban_time) {
        this.name = name;
        this.login = login;
        this.role = role;
        this.ban_status = ban_status;
        this.ban_start = ban_start;
        this.ban_time = ban_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isBan_status() {
        return ban_status;
    }

    public void setBan_status(boolean ban_status) {
        this.ban_status = ban_status;
    }

    public LocalDateTime getBan_start() {
        return ban_start;
    }

    public void setBan_start(LocalDateTime ban_start) {
        this.ban_start = ban_start;
    }

    public LocalTime getBan_time() {
        return ban_time;
    }

    public void setBan_time(LocalTime ban_time) {
        this.ban_time = ban_time;
    }
}
