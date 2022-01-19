package ru.simbirsoft.chat_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.chat_project.dto.MessageDtoRequest;
import ru.simbirsoft.chat_project.dto.MessageDtoResponse;
import ru.simbirsoft.chat_project.entities.Message;
import ru.simbirsoft.chat_project.entities.Room;
import ru.simbirsoft.chat_project.entities.User;
import ru.simbirsoft.chat_project.exception.NotFoundException;
import ru.simbirsoft.chat_project.mappers.MessageMapper;
import ru.simbirsoft.chat_project.repository.MessageRepository;
import ru.simbirsoft.chat_project.repository.RoomRepository;
import ru.simbirsoft.chat_project.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Transactional(readOnly = true)
    public MessageDtoResponse getMessageById(Long id) throws NotFoundException {
        Optional<Message> message = messageRepository.findMessageById(id);
        if (message.isPresent()) {
            return MessageMapper.INSTANCE.messageToMessageDto(message.get());
        }
        throw new NotFoundException("There is no message with id = " + id);
    }

    @Transactional(readOnly = true)
    public List<MessageDtoResponse> getMessageByAuthor(String username) throws NotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return messageRepository.findByAuthor(user.get())
                    .stream()
                    .map(MessageMapper.INSTANCE::messageToMessageDto)
                    .collect(Collectors.toList());
        }
        throw new NotFoundException("There is no user with name = " + username);
    }

    @Transactional(readOnly = true)
    public List<MessageDtoResponse> getMessageByRoom(String name) throws NotFoundException {
        Optional<Room> room = roomRepository.findRoomByName(name);
        if (room.isPresent()) {
            return messageRepository.findByRoom(room.get())
                    .stream()
                    .map(MessageMapper.INSTANCE::messageToMessageDto)
                    .collect(Collectors.toList());
        }
        throw new NotFoundException("There is no room with name = " + name);
    }

    @Transactional
    @PreAuthorize("principal.accountNonLocked")
    public MessageDtoResponse createMessage(MessageDtoRequest messageDtoRequest) {
        Message message = messageRepository.save(MessageMapper.INSTANCE.messageDtoToMessage(messageDtoRequest));
        return MessageMapper.INSTANCE.messageToMessageDto(message);
    }

//    public MessageDtoResponse botMessage(MessageDtoRequest messageDtoRequest) {
//
//    }

    @Transactional
    @PreAuthorize("principal.accountNonLocked")
    public MessageDtoResponse updateMessage(Long id, MessageDtoRequest messageDtoRequest) throws NotFoundException {
        Optional<Message> messageOptional = messageRepository.findMessageById(id);
        if (messageOptional.isPresent()) {
            Message message = MessageMapper.INSTANCE.messageDtoToMessage(messageDtoRequest);
            message.setId(id);
            messageRepository.save(message);
            return MessageMapper.INSTANCE.messageToMessageDto(message);
        }
        throw new NotFoundException("There is no message with id = " + id);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    public void deleteMessage(Long id) throws NotFoundException {
        Optional<Message> messageOptional = messageRepository.findMessageById(id);
        if (messageOptional.isPresent()) {
            messageRepository.delete(messageOptional.get());
        } else {
            throw new NotFoundException("There is no message with id = " + id);
        }
    }
}
