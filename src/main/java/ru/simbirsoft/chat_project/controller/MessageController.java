package ru.simbirsoft.chat_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.chat_project.dto.MessageDtoRequest;
import ru.simbirsoft.chat_project.dto.MessageDtoResponse;
import ru.simbirsoft.chat_project.dto.RoomDtoRequest;
import ru.simbirsoft.chat_project.entities.Role;
import ru.simbirsoft.chat_project.exception.NotFoundException;
import ru.simbirsoft.chat_project.repository.RoleRepository;
import ru.simbirsoft.chat_project.security.CustomUserDetails;
import ru.simbirsoft.chat_project.service.MessageService;
import ru.simbirsoft.chat_project.service.RoomService;
import ru.simbirsoft.chat_project.service.UserService;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/message")
public class MessageController {

    private final MessageService messageService;
    private final RoomService roomService;
    private final UserService userService;
    private final RoleRepository roleRepository;
    @Value("${regexCreateRoom}")
    private String regexCreateRoom;
    @Value("${regexCreateRoomWithStatus}")
    private String regexCreateRoomWithStatus;
    @Value("${regexRemoveRoom}")
    private String regexRemoveRoom;
    @Value("${regexRenameRoom}")
    private String regexRenameRoom;
    @Value("${regexAddUser}")
    private String regexAddUser;
    @Value("${regexDeleteUser}")
    private String regexDeleteUser;
    @Value("${regexUserRename}")
    private String regexUserRename;
    @Value("${regexUserBan}")
    private String regexUserBan;
    @Value("${regexUserBanTime}")
    private String regexUserBanTime;
    @Value("${regexUserModeratorOn}")
    private String regexUserModeratorOn;
    @Value("${regexUserModeratorOff}")
    private String regexUserModeratorOff;


    @GetMapping("/get/{messageId}")
    public MessageDtoResponse getMessage(@PathVariable Long messageId) throws NotFoundException {
        return messageService.getMessageById(messageId);
    }

    @GetMapping("/get/byAuthor")
    public List<MessageDtoResponse> getMessageByAuthor(@RequestParam String authorName) throws NotFoundException {
        return messageService.getMessageByAuthor(authorName);
    }

    @GetMapping("/get/byRoomName")
    public List<MessageDtoResponse> getMessageByRoom(@RequestParam String roomName) throws NotFoundException {
        return messageService.getMessageByRoom(roomName);
    }

    @PostMapping("/create")
    public MessageDtoResponse createMessage(@RequestBody MessageDtoRequest messageDtoRequest,
                                            @AuthenticationPrincipal CustomUserDetails customUserDetails)
                                            throws NotFoundException {

        boolean createRoom = Pattern.matches(regexCreateRoom, messageDtoRequest.getContent());
        boolean createRoomWithStatus = Pattern.matches(regexCreateRoomWithStatus, messageDtoRequest.getContent());
        boolean removeRoom = Pattern.matches(regexRemoveRoom, messageDtoRequest.getContent());
        boolean renameRoom = Pattern.matches(regexRenameRoom, messageDtoRequest.getContent());
        boolean addUser = Pattern.matches(regexAddUser, messageDtoRequest.getContent());
        boolean deleteUser = Pattern.matches(regexDeleteUser, messageDtoRequest.getContent());
        boolean userRename = Pattern.matches(regexUserRename, messageDtoRequest.getContent());
        boolean userBan = Pattern.matches(regexUserBan, messageDtoRequest.getContent());
        boolean userBanTime = Pattern.matches(regexUserBanTime, messageDtoRequest.getContent());
        boolean userModeratorOn = Pattern.matches(regexUserModeratorOn, messageDtoRequest.getContent());
        boolean userModeratorOff = Pattern.matches(regexUserModeratorOff, messageDtoRequest.getContent());

        if (createRoom && messageDtoRequest.getRoom().equals(2L)) {
            String roomName = messageDtoRequest.getContent().substring(messageDtoRequest.getContent().indexOf('{')+1,
                                                                 messageDtoRequest.getContent().indexOf('}'));
            RoomDtoRequest roomDtoRequest = new RoomDtoRequest();
            roomDtoRequest.setOwner(customUserDetails.getId());
            roomDtoRequest.setName(roomName);
            roomService.createRoom(roomDtoRequest);
        }
        if (createRoomWithStatus && messageDtoRequest.getRoom().equals(2L)) {
            String roomName = messageDtoRequest.getContent().substring(messageDtoRequest.getContent().indexOf('{')+1,
                                                                    messageDtoRequest.getContent().indexOf('}'));
            RoomDtoRequest roomDtoRequest = new RoomDtoRequest();
            roomDtoRequest.setOwner(customUserDetails.getId());
            roomDtoRequest.setName(roomName);
            roomDtoRequest.setPrivate_status(true);
            roomService.createRoom(roomDtoRequest);
        }
        if (removeRoom && messageDtoRequest.getRoom().equals(2L)) {
            String roomName = messageDtoRequest.getContent().substring(messageDtoRequest.getContent().indexOf('{')+1,
                                                                    messageDtoRequest.getContent().indexOf('}'));
            roomService.deleteRoom(roomService.getRoomByName(roomName).getId());
        }
        if (renameRoom && messageDtoRequest.getRoom().equals(2L)) {
            String oldName = messageDtoRequest.getContent().substring(messageDtoRequest.getContent().indexOf('{')+1,
                                                                    messageDtoRequest.getContent().indexOf('}'));
            String newName = messageDtoRequest.getContent().substring(messageDtoRequest.getContent().indexOf("}{")+2,
                                                                    messageDtoRequest.getContent().length()-1);
            roomService.renameRoom(roomService.getRoomByName(oldName).getId(), newName);
        }
        if (addUser && messageDtoRequest.getRoom().equals(2L)) {
            String roomName = messageDtoRequest.getContent().substring(messageDtoRequest.getContent().indexOf('{')+1,
                                                                    messageDtoRequest.getContent().indexOf('}'));
            String username = messageDtoRequest.getContent().substring(messageDtoRequest.getContent().indexOf("l{")+2,
                                                                    messageDtoRequest.getContent().length()-1);
            roomService.addUserToRoom(roomService.getRoomByName(roomName).getId(),
                                    userService.getUserByUsername(username).getId());
        }
        if (deleteUser && messageDtoRequest.getRoom().equals(2L)) {
            String roomName = messageDtoRequest.getContent().substring(messageDtoRequest.getContent().indexOf('{')+1,
                                                                    messageDtoRequest.getContent().indexOf('}'));
            String username = messageDtoRequest.getContent().substring(messageDtoRequest.getContent().indexOf("l{")+2,
                                                                    messageDtoRequest.getContent().length()-1);
            roomService.deleteUserFromRoom(roomService.getRoomByName(roomName).getId(),
                    userService.getUserByUsername(username).getId());
        }
        if (userRename && messageDtoRequest.getRoom().equals(2L)) {
            String oldUsername = messageDtoRequest.getContent().substring(messageDtoRequest.getContent().indexOf('{')+1,
                    messageDtoRequest.getContent().indexOf('}'));
            String newUsername = messageDtoRequest.getContent().substring(messageDtoRequest.getContent().indexOf('(')+1,
                    messageDtoRequest.getContent().indexOf(')'));
            userService.setUsername(userService.getUserByUsername(oldUsername).getId(), newUsername);
        }
        if (userBan && messageDtoRequest.getRoom().equals(2L)) {
            String username = messageDtoRequest.getContent().substring(messageDtoRequest.getContent().indexOf('{')+1,
                                                                    messageDtoRequest.getContent().indexOf('}'));
            userService.setStatus(userService.getUserByUsername(username).getId(), false);
        }
        if (userBanTime && messageDtoRequest.getRoom().equals(2L)) {
            String username = messageDtoRequest.getContent().substring(messageDtoRequest.getContent().indexOf("l{")+2,
                                                                    messageDtoRequest.getContent().indexOf('}'));
            String hours = messageDtoRequest.getContent().substring(messageDtoRequest.getContent().indexOf("m{")+2,
                                                                    messageDtoRequest.getContent().length()-1);
            Long banTime = Long.parseLong(hours);
            userService.setBanEnd(userService.getUserByUsername(username).getId(), banTime);
        }
        if (userModeratorOn && messageDtoRequest.getRoom().equals(2L)) {
            String username = messageDtoRequest.getContent().substring(messageDtoRequest.getContent().indexOf('{')+1,
                    messageDtoRequest.getContent().indexOf('}'));
            Role role = roleRepository.findById(2L).get();
            userService.setRole(userService.getUserByUsername(username).getId(), role);
        }
        if (userModeratorOff && messageDtoRequest.getRoom().equals(2L)) {
            String username = messageDtoRequest.getContent().substring(messageDtoRequest.getContent().indexOf('{')+1,
                                                                    messageDtoRequest.getContent().indexOf('}'));
            Role role = roleRepository.findById(1L).get();
            userService.setRole(userService.getUserByUsername(username).getId(), role);
        }
        return messageService.createMessage(messageDtoRequest);
    }

    @PutMapping("/update/{messageId}")
    public MessageDtoResponse updateMessage(@PathVariable Long messageId,
                                            @RequestBody MessageDtoRequest messageDtoRequest) throws NotFoundException {
        return messageService.updateMessage(messageId, messageDtoRequest);
    }

    @DeleteMapping("/delete/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable("messageId") Long messageId) throws NotFoundException {
        messageService.deleteMessage(messageId);
        return new ResponseEntity<>("Message has been deleted", HttpStatus.OK);
    }
}
