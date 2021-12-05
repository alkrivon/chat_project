package ru.simbirsoft.chat_project.controller;

import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.chat_project.dto.RoomDtoRequest;
import ru.simbirsoft.chat_project.dto.RoomDtoResponse;

@RestController
@RequestMapping("api/room")
public class RoomController {

    @PostMapping("/save")
    public RoomDtoResponse saveRoom(@RequestBody RoomDtoRequest roomDtoRequest) {
        return null;
    }

    @GetMapping("/get/{roomId}")
    public RoomDtoResponse getRoom(@PathVariable("roomId") Long roomId) {
        return null;
    }

    @PutMapping("/update")
    public RoomDtoResponse updateRoom(@RequestBody RoomDtoRequest roomDtoRequest) {
        return null;
    }

    @DeleteMapping("/delete/{roomId}")
    public RoomDtoResponse deleteRoom(@PathVariable("roomId") Long roomId) {
        return null;
    }
}

