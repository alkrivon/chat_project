package ru.simbirsoft.chat_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.chat_project.dto.RoomDtoRequest;
import ru.simbirsoft.chat_project.dto.RoomDtoResponse;
import ru.simbirsoft.chat_project.dto.UserDtoResponse;
import ru.simbirsoft.chat_project.mappers.RoomMapper;
import ru.simbirsoft.chat_project.service.RoomService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/room")
public class RoomController {

    @Autowired
    RoomService roomService;

    @PostMapping("/save")
    public RoomDtoResponse saveRoom(@RequestBody RoomDtoRequest roomDtoRequest) {
        return roomService.saveRoom(roomDtoRequest);
    }

    @GetMapping("/get/byId/{roomId}")
    public RoomDtoResponse getRoom(@PathVariable Long roomId) {
        return roomService.getRoomById(roomId);
    }

    @GetMapping("/get/byName/{roomName}")
    public RoomDtoResponse getRoomByName(@PathVariable String roomName) {
        return roomService.getRoomByName(roomName);
    }

    @GetMapping("/get/byOwner/{ownerName}")
    public List<RoomDtoResponse> getRoomByOwner(@PathVariable String ownerName) {
        return roomService.getRoomByOwner(ownerName);
    }

    @PutMapping("/update/{roomId}")
    public RoomDtoResponse updateRoom(@PathVariable Long roomId,
                                      @RequestBody RoomDtoRequest roomDtoRequest) {
        return roomService.updateRoom(roomId, roomDtoRequest);
    }

    @DeleteMapping("/delete/{roomId}")
    public String deleteRoom(@PathVariable("roomId") Long roomId) {
        roomService.deleteRoom(roomId);
        return "Room with id = " + roomId + " has been deleted";
    }
}

