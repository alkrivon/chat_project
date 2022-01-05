package ru.simbirsoft.chat_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.chat_project.dto.RoomDtoRequest;
import ru.simbirsoft.chat_project.dto.RoomDtoResponse;
import ru.simbirsoft.chat_project.service.RoomService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/room")
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/get/{roomId}")
    public RoomDtoResponse getRoom(@PathVariable Long roomId) {
        return roomService.getRoomById(roomId);
    }

    @GetMapping("/get/byName")
    public RoomDtoResponse getRoomByName(@RequestParam String roomName) {
        return roomService.getRoomByName(roomName);
    }

    @GetMapping("/get/byOwner")
    public List<RoomDtoResponse> getRoomByOwner(@RequestParam String ownerName) {
        return roomService.getRoomByOwner(ownerName);
    }

    @PostMapping("/save")
    public RoomDtoResponse saveRoom(@RequestBody RoomDtoRequest roomDtoRequest) {
        return roomService.saveRoom(roomDtoRequest);
    }

    @PutMapping("/update/{roomId}")
    public RoomDtoResponse updateRoom(@PathVariable Long roomId,
                                      @RequestBody RoomDtoRequest roomDtoRequest) {
        return roomService.updateRoom(roomId, roomDtoRequest);
    }

    @DeleteMapping("/delete/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable("roomId") Long roomId) {
        roomService.deleteRoom(roomId);
        return new ResponseEntity<>("Room has been deleted", HttpStatus.OK);
    }
}

