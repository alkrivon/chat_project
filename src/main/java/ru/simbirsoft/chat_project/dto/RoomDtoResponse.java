package ru.simbirsoft.chat_project.dto;

import lombok.Data;
import ru.simbirsoft.chat_project.entities.User;

import java.util.List;

@Data
public class RoomDtoResponse {

    private Long id;
    private String name;
    private boolean private_status;
    private Long owner;
    private List<User> users;
}
