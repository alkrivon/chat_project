package ru.simbirsoft.chat_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.chat_project.dto.RoomDtoRequest;
import ru.simbirsoft.chat_project.dto.RoomDtoResponse;
import ru.simbirsoft.chat_project.entities.Room;
import ru.simbirsoft.chat_project.exception.RoomNotFoundException;
import ru.simbirsoft.chat_project.mappers.RoomMapper;
import ru.simbirsoft.chat_project.repository.RoomRepository;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Transactional(readOnly = true)
    public RoomDtoResponse getRoomById(Long id) {
        Optional<Room> room = roomRepository.findRoomById(id);
        if (room.isPresent()) {
            return RoomMapper.INSTANCE.roomToRoomDto(room.get());
        }
        throw new RoomNotFoundException("There is no room with id = " + id);
    }
    @Transactional
    public RoomDtoResponse saveRoom(RoomDtoRequest roomDtoRequest) {
        Room message = roomRepository.save(RoomMapper.INSTANCE.roomDtoToRoom(roomDtoRequest));
        return RoomMapper.INSTANCE.roomToRoomDto(message);
    }
    @Transactional
    public RoomDtoResponse updateRoom(Long id, RoomDtoRequest roomDtoRequest) {
        Optional<Room> roomOptional = roomRepository.findRoomById(id);
        if (roomOptional.isPresent()) {
            Room room = RoomMapper.INSTANCE.roomDtoToRoom(roomDtoRequest);
            room.setId(id);
            roomRepository.save(room);
            return RoomMapper.INSTANCE.roomToRoomDto(room);
        }
        throw new RoomNotFoundException("There is no room with id = " + id);
    }
    @Transactional
    public void deleteRoom(Long id) {
        Optional<Room> roomOptional = roomRepository.findRoomById(id);
        if (roomOptional.isPresent()) {
            roomRepository.delete(roomOptional.get());
        } else {
            throw new RoomNotFoundException("There is no room with id = " + id);
        }
    }
}
