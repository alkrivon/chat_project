package ru.simbirsoft.chat_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.chat_project.dto.MessageDtoRequest;
import ru.simbirsoft.chat_project.dto.MessageDtoResponse;
import ru.simbirsoft.chat_project.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("api/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @PostMapping("/save")
    public MessageDtoResponse saveMessage(@RequestBody MessageDtoRequest messageDtoRequest) {
        return messageService.saveMessage(messageDtoRequest);
    }

    @GetMapping("/get/byId/{messageId}")
    public MessageDtoResponse getMessage(@PathVariable Long messageId) {
        return messageService.getMessageById(messageId);
    }

    @GetMapping("/get/byAuthor/{authorName}")
    public List<MessageDtoResponse> getMessageByAuthor(@PathVariable String authorName) {
        return messageService.getMessageByAuthor(authorName);
    }

    @GetMapping("/get/byRoom/{roomName}")
    public List<MessageDtoResponse> getMessageByRoom(@PathVariable String roomName) {
        return messageService.getMessageByRoom(roomName);
    }

    @PutMapping("/update/{messageId}")
    public MessageDtoResponse updateMessage(@PathVariable Long messageId,
                                            @RequestBody MessageDtoRequest messageDtoRequest) {
        return messageService.updateMessage(messageId, messageDtoRequest);
    }

    @DeleteMapping("/delete/{messageId}")
    public String deleteMessage(@PathVariable("messageId") Long messageId) {
        messageService.deleteMessage(messageId);
        return "Message with id = " + messageId + " has been deleted";
    }
}
