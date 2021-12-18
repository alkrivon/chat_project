package ru.simbirsoft.chat_project.dto;

import lombok.Data;

@Data
public class RoomDtoRequest {

    private String name;
    private boolean private_status;
    private Long owner;
}
