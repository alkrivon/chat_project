package ru.simbirsoft.chat_project.dto;

import ru.simbirsoft.chat_project.entities.Room;
import ru.simbirsoft.chat_project.entities.User;

public class MessageDtoResponse {

    private Long id;
    private String content;
    private User author;
    private Room room;

    public MessageDtoResponse(Long id, String content, User author, Room room) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNameAuthor() {
        return author.getName();
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getNameRoom() {
        return room.getName();
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
