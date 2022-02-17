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
import ru.simbirsoft.chat_project.exception.NotFoundException;;
import ru.simbirsoft.chat_project.security.CustomUserDetails;
import ru.simbirsoft.chat_project.service.MessageService;
import ru.simbirsoft.chat_project.service.RoomService;
import ru.simbirsoft.chat_project.service.UserService;
import ru.simbirsoft.chat_project.utill.SearchVideoYoutube;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/message")
public class MessageController {

    private final MessageService messageService;
    private final RoomService roomService;
    private final UserService userService;

    @Value("${regexCreateRoom}")
    private String regexCreateRoom;
    @Value("${regexRemoveRoom}")
    private String regexRemoveRoom;
    @Value("${regexRenameRoom}")
    private String regexRenameRoom;
    @Value("${regexAddUser}")
    private String regexAddUser;
    @Value("${regexDeleteUser}")
    private String regexDeleteUser;
    @Value("${regexUserBanTime}")
    private String regexUserBanTime;
    @Value("${youtube.apikey}")
    private String apikey;
    @Value("${regexYoutubeBotViews}")
    private String regexYoutubeBotViews;
    @Value("${regexYoutubeBotLikes}")
    private String regexYoutubeBotLikes;
    @Value("${regexYoutubeBotViewsAndLikes}")
    private String regexYoutubeBotViewsAndLikes;
    @Value("${regexYoutubeBotChannelNameAndVideos}")
    private String regexYoutubeBotChannelNameAndVideos;

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
    public ResponseEntity<String> createMessage(@RequestBody MessageDtoRequest messageDtoRequest,
                                            @AuthenticationPrincipal CustomUserDetails customUserDetails)
                                            throws NotFoundException, IOException {

        boolean isActiveCreateRoom = Pattern.matches(regexCreateRoom, messageDtoRequest.getContent());
        boolean isActiveRemoveRoom = Pattern.matches(regexRemoveRoom, messageDtoRequest.getContent());
        boolean isActiveRenameRoom = Pattern.matches(regexRenameRoom, messageDtoRequest.getContent());
        boolean isActiveAddUser = Pattern.matches(regexAddUser, messageDtoRequest.getContent());
        boolean isActiveDeleteUser = Pattern.matches(regexDeleteUser, messageDtoRequest.getContent());
        boolean isActiveUserBanTime = Pattern.matches(regexUserBanTime, messageDtoRequest.getContent());
        boolean isActiveYoutubeBotViews = Pattern.matches(regexYoutubeBotViews, messageDtoRequest.getContent());
        boolean isActiveYoutubeBotLikes = Pattern.matches(regexYoutubeBotLikes, messageDtoRequest.getContent());
        boolean isActiveYoutubeBotViewsAndLikes = Pattern.matches(regexYoutubeBotViewsAndLikes, messageDtoRequest.getContent());
        boolean isActiveYoutubeBotChannelNameAndVideos = Pattern.matches(regexYoutubeBotChannelNameAndVideos, messageDtoRequest.getContent());

        String substringOfCommandParameter = messageDtoRequest.getContent().substring(messageDtoRequest.getContent().indexOf('{')+1,
                                                                                messageDtoRequest.getContent().indexOf('}'));
        SearchVideoYoutube youtubeInfo = new SearchVideoYoutube(apikey);

        if (isActiveCreateRoom && messageDtoRequest.getRoom().equals(2L)) {
            RoomDtoRequest roomDtoRequest = new RoomDtoRequest();
            roomDtoRequest.setOwner(customUserDetails.getId());
            roomDtoRequest.setName(substringOfCommandParameter);
            roomService.createRoom(roomDtoRequest);
        }
        if (isActiveRemoveRoom && messageDtoRequest.getRoom().equals(2L)) {
            roomService.deleteRoom(roomService.getRoomByName(substringOfCommandParameter).getId());
        }
        if (isActiveRenameRoom && messageDtoRequest.getRoom().equals(2L)) {
            String newName = messageDtoRequest.getContent().substring(messageDtoRequest.getContent().indexOf("}{")+2,
                                                                    messageDtoRequest.getContent().length()-1);
            roomService.renameRoom(roomService.getRoomByName(substringOfCommandParameter).getId(), newName);
        }
        if (isActiveAddUser && messageDtoRequest.getRoom().equals(2L)) {
            String username = messageDtoRequest.getContent().substring(messageDtoRequest.getContent().indexOf("l{")+2,
                                                                    messageDtoRequest.getContent().length()-1);
            roomService.addUserToRoom(roomService.getRoomByName(substringOfCommandParameter).getId(),
                                    userService.getUserByUsername(username).getId());
        }
        if (isActiveDeleteUser && messageDtoRequest.getRoom().equals(2L)) {
            String username = messageDtoRequest.getContent().substring(messageDtoRequest.getContent().indexOf("l{")+2,
                                                                    messageDtoRequest.getContent().length()-1);
            roomService.deleteUserFromRoom(roomService.getRoomByName(substringOfCommandParameter).getId(),
                                        userService.getUserByUsername(username).getId());
        }
        if (isActiveUserBanTime && messageDtoRequest.getRoom().equals(2L)) {
            String username = messageDtoRequest.getContent().substring(messageDtoRequest.getContent().indexOf("l{")+2,
                                                                    messageDtoRequest.getContent().indexOf('}'));
            String minutes = messageDtoRequest.getContent().substring(messageDtoRequest.getContent().indexOf("m{")+2,
                                                                    messageDtoRequest.getContent().length()-1);
            Long banTime = Long.parseLong(minutes);
            userService.setBanEnd(userService.getUserByUsername(username).getId(), banTime);
        }
        if (isActiveYoutubeBotViews && messageDtoRequest.getRoom().equals(2L)) {
            messageDtoRequest.setAuthor(customUserDetails.getId());
            messageService.createMessage(messageDtoRequest);
            return new ResponseEntity<>("Views: " + youtubeInfo.getViewCount(substringOfCommandParameter),
                                                                                HttpStatus.OK);
        }
        if (isActiveYoutubeBotLikes && messageDtoRequest.getRoom().equals(2L)) {
            messageDtoRequest.setAuthor(customUserDetails.getId());
            messageService.createMessage(messageDtoRequest);
            return new ResponseEntity<>("Likes: " + youtubeInfo.getLikeCount(substringOfCommandParameter),
                                                                                HttpStatus.OK);
        }
        if (isActiveYoutubeBotViewsAndLikes && messageDtoRequest.getRoom().equals(2L)) {
            messageDtoRequest.setAuthor(customUserDetails.getId());
            messageService.createMessage(messageDtoRequest);
            return new ResponseEntity<>("Views: " + youtubeInfo.getViewCount(substringOfCommandParameter) + "\n" +
                                              "Likes: " + youtubeInfo.getLikeCount(substringOfCommandParameter), HttpStatus.OK);
        }
        if (isActiveYoutubeBotChannelNameAndVideos && messageDtoRequest.getRoom().equals(2L)) {
            messageDtoRequest.setAuthor(customUserDetails.getId());
            messageService.createMessage(messageDtoRequest);
            return new ResponseEntity<>("Channel's name: : " + youtubeInfo.getChannelName(substringOfCommandParameter)  + "\n" +
                                              "URL: " + youtubeInfo.getChannelVideos(substringOfCommandParameter), HttpStatus.OK);
        }
        messageDtoRequest.setAuthor(customUserDetails.getId());
        messageService.createMessage(messageDtoRequest);
        return new ResponseEntity<>("Message has been sent!", HttpStatus.OK);
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
