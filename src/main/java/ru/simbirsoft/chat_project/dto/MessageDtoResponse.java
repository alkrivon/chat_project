package ru.simbirsoft.chat_project.dto;

import lombok.Data;

@Data
public class MessageDtoResponse {

    private Long id;
    private String content;
    private String author;
    private Long room;
}
