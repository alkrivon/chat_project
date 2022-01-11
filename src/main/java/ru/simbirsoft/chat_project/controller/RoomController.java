package ru.simbirsoft.chat_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.chat_project.dto.RoomDtoRequest;
import ru.simbirsoft.chat_project.dto.RoomDtoResponse;
import ru.simbirsoft.chat_project.exception.NotFoundException;
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

    @PreAuthorize("principal.accountNonLocked")
    @PostMapping("/addUserToRoom/{userId}")
    public ResponseEntity<String> addUserToRoom(@PathVariable Long userId,
                                                @RequestParam Long roomId) throws NotFoundException {
        roomService.addUserToRoom(userId, roomId);
        return new ResponseEntity<>("User has been added", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') OR principal.username == @roomRepository." +
                                                    "findRoomById(#roomId)" +
                                                     ".get().owner.login")
    @PostMapping("/deleteUserFromRoom/{userId}")
    public ResponseEntity<String> deleteUserFromRoom(@PathVariable Long userId,
                                                     @RequestParam Long roomId) throws NotFoundException {
        roomService.deleteUserFromRoom(userId, roomId);
        return new ResponseEntity<>("User has been deleted", HttpStatus.OK);
    }

    @PreAuthorize("principal.accountNonLocked")
    @PostMapping("/create")
    public RoomDtoResponse saveRoom(@RequestBody RoomDtoRequest roomDtoRequest) {
        return roomService.createRoom(roomDtoRequest);
    }

    @PreAuthorize("hasRole('ADMIN') OR principal.username == @roomRepository." +
                                                     "findRoomById(#roomId)" +
                                                      ".get().owner.login")
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
}

