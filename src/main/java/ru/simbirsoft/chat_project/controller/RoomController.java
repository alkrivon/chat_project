package ru.simbirsoft.chat_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.chat_project.dto.RoomDtoRequest;
import ru.simbirsoft.chat_project.dto.RoomDtoResponse;
import ru.simbirsoft.chat_project.exception.NotFoundException;
import ru.simbirsoft.chat_project.security.CustomUserDetails;
import ru.simbirsoft.chat_project.service.RoomService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/room")
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/get/{roomId}")
    public RoomDtoResponse getRoom(@PathVariable Long roomId) throws NotFoundException {
        return roomService.getRoomById(roomId);
    }

    @GetMapping("/get/byName")
    public RoomDtoResponse getRoomByName(@RequestParam String roomName) throws NotFoundException {
        return roomService.getRoomByName(roomName);
    }

    @GetMapping("/get/byOwner")
    public List<RoomDtoResponse> getRoomByOwner(@RequestParam String ownerName) throws NotFoundException {
        return roomService.getRoomByOwner(ownerName);
    }

    @PostMapping("/addUserToRoom/{userId}")
    public ResponseEntity<String> addUserToRoom(@PathVariable Long userId,
                                                @RequestParam Long roomId) throws NotFoundException {
        roomService.addUserToRoom(userId, roomId);
        return new ResponseEntity<>("User has been added", HttpStatus.OK);
    }

    @PostMapping("/deleteUserFromRoom/{userId}")
    public ResponseEntity<String> deleteUserFromRoom(@PathVariable Long userId,
                                                     @RequestParam Long roomId) throws NotFoundException {
        roomService.deleteUserFromRoom(userId, roomId);
        return new ResponseEntity<>("User has been deleted", HttpStatus.OK);
    }

    @PostMapping("/create")
    public RoomDtoResponse createRoom(@RequestBody RoomDtoRequest roomDtoRequest,
                                      @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        roomDtoRequest.setOwner(customUserDetails.getId());
        return roomService.createRoom(roomDtoRequest);
    }

    @PutMapping("/update/{roomId}")
    public RoomDtoResponse updateRoom(@PathVariable Long roomId,
                                      @RequestBody RoomDtoRequest roomDtoRequest) throws NotFoundException {
        return roomService.updateRoom(roomId, roomDtoRequest);
    }

    @DeleteMapping("/delete/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable("roomId") Long roomId) throws NotFoundException {
        roomService.deleteRoom(roomId);
        return new ResponseEntity<>("Room has been deleted", HttpStatus.OK);
    }

    @PatchMapping("/rename/{roomId}")
    public ResponseEntity<String> renameRoom(@PathVariable Long roomId,
                                             @RequestParam String roomName) {
        roomService.renameRoom(roomId, roomName);
        return new ResponseEntity<>("Room has been renamed", HttpStatus.OK);
    }
}

