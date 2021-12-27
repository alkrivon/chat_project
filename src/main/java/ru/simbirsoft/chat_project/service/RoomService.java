package ru.simbirsoft.chat_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.chat_project.dto.RoomDtoRequest;
import ru.simbirsoft.chat_project.dto.RoomDtoResponse;
import ru.simbirsoft.chat_project.entities.Room;
import ru.simbirsoft.chat_project.entities.User;
import ru.simbirsoft.chat_project.exception.RoomNotFoundException;
import ru.simbirsoft.chat_project.mappers.RoomMapper;
import ru.simbirsoft.chat_project.repository.RoomRepository;
import ru.simbirsoft.chat_project.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public RoomDtoResponse getRoomById(Long id) {
        Optional<Room> room = roomRepository.findRoomById(id);
        if (room.isPresent()) {
            return RoomMapper.INSTANCE.roomToRoomDto(room.get());
        }
        throw new RoomNotFoundException("There is no room with id = " + id);
    }
    @Transactional(readOnly = true)
    public RoomDtoResponse getRoomByName(String name) {
        Optional<Room> room = roomRepository.findRoomByName(name);
        if (room.isPresent()) {
            return RoomMapper.INSTANCE.roomToRoomDto(room.get());
        }
        throw new RoomNotFoundException("There is no room with name = " + name);
    }
    @Transactional(readOnly = true)
    public List<RoomDtoResponse> getRoomByOwner(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isPresent()) {
        return roomRepository.findRoomByOwner(user.get())
                .stream()
                .map(RoomMapper.INSTANCE::roomToRoomDto)
                .collect(Collectors.toList());
        }
        throw new RoomNotFoundException("There is no user with name = " + username);
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
