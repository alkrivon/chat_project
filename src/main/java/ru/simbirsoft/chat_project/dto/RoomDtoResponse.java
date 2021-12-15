package ru.simbirsoft.chat_project.dto;

import ru.simbirsoft.chat_project.entities.User;

public class RoomDtoResponse {

    private Long id;
    private String name;
    private boolean private_status;
    private User owner;

    public RoomDtoResponse(Long id, String name, boolean private_status, User owner) {
        this.id = id;
        this.name = name;
        this.private_status = private_status;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getOwner() {
        return owner.getName();
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
