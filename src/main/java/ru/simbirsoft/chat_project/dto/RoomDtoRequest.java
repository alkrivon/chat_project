package ru.simbirsoft.chat_project.dto;

import ru.simbirsoft.chat_project.entities.User;

public class RoomDtoRequest {

    private String name;
    private boolean private_status;
    private User owner;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrivate_status() {
        return private_status;
    }

    public void setPrivate_status(boolean private_status) {
        this.private_status = private_status;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public RoomDtoRequest(String name, boolean private_status, User owner) {
        this.name = name;
        this.private_status = private_status;
        this.owner = owner;
    }
}
