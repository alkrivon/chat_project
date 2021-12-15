package ru.simbirsoft.chat_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.chat_project.dto.RoomDtoRequest;
import ru.simbirsoft.chat_project.dto.RoomDtoResponse;
import ru.simbirsoft.chat_project.service.RoomService;

@RestController
@RequestMapping("api/room")
public class RoomController {

    @Autowired
    RoomService roomService;

    @PostMapping("/save")
    public RoomDtoResponse saveRoom(@RequestBody RoomDtoRequest roomDtoRequest) {
        return roomService.saveRoom(roomDtoRequest);
    }

    @GetMapping("/get/{roomId}")
    public RoomDtoResponse getRoom(@PathVariable Long roomId) {
        return roomService.getRoomById(roomId);
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

