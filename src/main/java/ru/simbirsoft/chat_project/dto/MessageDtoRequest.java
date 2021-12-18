package ru.simbirsoft.chat_project.dto;

import lombok.Data;

@Data
public class MessageDtoRequest {

    private String content;
    private Long author;
    private Long room;

}
