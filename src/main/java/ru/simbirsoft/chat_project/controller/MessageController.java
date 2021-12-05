package ru.simbirsoft.chat_project.controller;

import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.chat_project.dto.MessageDtoRequest;
import ru.simbirsoft.chat_project.dto.MessageDtoResponse;

@RestController
@RequestMapping("api/message")
public class MessageController {

    @PostMapping("/save")
    public MessageDtoResponse saveMessage(@RequestBody MessageDtoRequest messageDtoRequest) {
        return null;
    }
    @GetMapping("/get/{messageId}")
    public MessageDtoResponse getMessage(@PathVariable("messageId") Long messageId) {
        return null;
    }
    @PutMapping("/update")
    public MessageDtoResponse updateMessage(@RequestBody MessageDtoRequest messageDtoRequest) {
        return null;
    }
    @DeleteMapping("/delete/{messageId}")
    public MessageDtoResponse deleteMessage(@PathVariable("messageId") Long messageId) {
        return null;
    }
}
