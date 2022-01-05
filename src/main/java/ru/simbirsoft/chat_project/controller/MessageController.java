package ru.simbirsoft.chat_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.chat_project.dto.MessageDtoRequest;
import ru.simbirsoft.chat_project.dto.MessageDtoResponse;
import ru.simbirsoft.chat_project.service.MessageService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/message")
public class MessageController {

    private final MessageService messageService;


    @GetMapping("/get/{messageId}")
    public MessageDtoResponse getMessage(@PathVariable Long messageId) {
        return messageService.getMessageById(messageId);
    }

    @GetMapping("/get/byAuthor")
    public List<MessageDtoResponse> getMessageByAuthor(@RequestParam String authorName) {
        return messageService.getMessageByAuthor(authorName);
    }

    @GetMapping("/get/byRoomName")
    public List<MessageDtoResponse> getMessageByRoom(@RequestParam String roomName) {
        return messageService.getMessageByRoom(roomName);
    }

    @PostMapping("/save")
    public MessageDtoResponse saveMessage(@RequestBody MessageDtoRequest messageDtoRequest) {
        return messageService.saveMessage(messageDtoRequest);
    }

    @PutMapping("/update/{messageId}")
    public MessageDtoResponse updateMessage(@PathVariable Long messageId,
                                            @RequestBody MessageDtoRequest messageDtoRequest) {
        return messageService.updateMessage(messageId, messageDtoRequest);
    }

    @DeleteMapping("/delete/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable("messageId") Long messageId) {
        messageService.deleteMessage(messageId);
        return new ResponseEntity<>("Message has been deleted", HttpStatus.OK);
    }
}
