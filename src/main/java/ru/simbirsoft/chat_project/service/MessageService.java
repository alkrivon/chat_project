package ru.simbirsoft.chat_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.chat_project.dto.MessageDtoRequest;
import ru.simbirsoft.chat_project.dto.MessageDtoResponse;
import ru.simbirsoft.chat_project.entities.Message;
import ru.simbirsoft.chat_project.entities.Room;
import ru.simbirsoft.chat_project.entities.User;
import ru.simbirsoft.chat_project.exception.MessageNotFoundException;
import ru.simbirsoft.chat_project.exception.RoomNotFoundException;
import ru.simbirsoft.chat_project.mappers.MessageMapper;
import ru.simbirsoft.chat_project.repository.MessageRepository;
import ru.simbirsoft.chat_project.repository.RoomRepository;
import ru.simbirsoft.chat_project.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoomRepository roomRepository;

    @Transactional(readOnly = true)
    public MessageDtoResponse getMessageById(Long id) {
        Optional<Message> message = messageRepository.findMessageById(id);
        if (message.isPresent()) {
            return MessageMapper.INSTANCE.messageToMessageDto(message.get());
        }
        throw new MessageNotFoundException("There is no message with id = " + id);
    }

    @Transactional(readOnly = true)
    public List<MessageDtoResponse> getMessageByAuthor(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isPresent()) {
            return messageRepository.findByAuthor(user.get())
                    .stream()
                    .map(MessageMapper.INSTANCE::messageToMessageDto)
                    .collect(Collectors.toList());
        }
        throw new RoomNotFoundException("There is no user with name = " + username);
    }

    @Transactional(readOnly = true)
    public List<MessageDtoResponse> getMessageByRoom(String name) {
        Optional<Room> room = roomRepository.findRoomByName(name);
        if (room.isPresent()) {
            return messageRepository.findByRoom(room.get())
                    .stream()
                    .map(MessageMapper.INSTANCE::messageToMessageDto)
                    .collect(Collectors.toList());
        }
        throw new RoomNotFoundException("There is no room with name = " + name);
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
