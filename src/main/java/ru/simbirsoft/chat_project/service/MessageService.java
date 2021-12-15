package ru.simbirsoft.chat_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.chat_project.dto.MessageDtoRequest;
import ru.simbirsoft.chat_project.dto.MessageDtoResponse;
import ru.simbirsoft.chat_project.entities.Message;
import ru.simbirsoft.chat_project.exception.MessageNotFoundException;
import ru.simbirsoft.chat_project.mappers.MessageMapper;
import ru.simbirsoft.chat_project.repository.MessageRepository;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Transactional(readOnly = true)
    public MessageDtoResponse getMessageById(Long id) {
        Optional<Message> message = messageRepository.findMessageById(id);
        if (message.isPresent()) {
            return MessageMapper.INSTANCE.messageToMessageDto(message.get());
        }
        throw new MessageNotFoundException("There is no message with id = " + id);
    }
    @Transactional
    public MessageDtoResponse saveMessage(MessageDtoRequest messageDtoRequest) {
        Message message = messageRepository.save(MessageMapper.INSTANCE.messageDtoToMessage(messageDtoRequest));
        return MessageMapper.INSTANCE.messageToMessageDto(message);
    }
    @Transactional
    public MessageDtoResponse updateMessage(Long id, MessageDtoRequest messageDtoRequest) {
        Optional<Message> messageOptional = messageRepository.findMessageById(id);
        if (messageOptional.isPresent()) {
            Message message = MessageMapper.INSTANCE.messageDtoToMessage(messageDtoRequest);
            message.setId(id);
            messageRepository.save(message);
            return MessageMapper.INSTANCE.messageToMessageDto(message);
        }
        throw new MessageNotFoundException("There is no message with id = " + id);
    }
    @Transactional
    public void deleteMessage(Long id) {
        Optional<Message> messageOptional = messageRepository.findMessageById(id);
        if (messageOptional.isPresent()) {
            messageRepository.delete(messageOptional.get());
        } else {
            throw new MessageNotFoundException("There is no message with id = " + id);
        }
    }
}
