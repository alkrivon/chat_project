package ru.simbirsoft.chat_project.dto;

import lombok.Data;

@Data
public class RoomDtoResponse {

    private Long id;
    private String name;
    private boolean private_status;
    private String owner;
}
