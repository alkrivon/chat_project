package ru.simbirsoft.chat_project.dto;

import ru.simbirsoft.chat_project.entities.Room;
import ru.simbirsoft.chat_project.entities.User;

public class MessageDtoRequest {

    private String content;
    private User author;
    private Room room;

    public MessageDtoRequest(String content, User author, Room room) {
        this.content = content;
        this.author = author;
        this.room = room;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
