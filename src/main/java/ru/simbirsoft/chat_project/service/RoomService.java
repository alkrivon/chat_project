package ru.simbirsoft.chat_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.chat_project.dto.RoomDtoRequest;
import ru.simbirsoft.chat_project.dto.RoomDtoResponse;
import ru.simbirsoft.chat_project.entities.Room;
import ru.simbirsoft.chat_project.entities.User;
import ru.simbirsoft.chat_project.exception.NotFoundException;
import ru.simbirsoft.chat_project.mappers.RoomMapper;
import ru.simbirsoft.chat_project.repository.RoomRepository;
import ru.simbirsoft.chat_project.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public RoomDtoResponse getRoomById(Long id) throws NotFoundException {
        Optional<Room> room = roomRepository.findRoomById(id);
        if (room.isPresent()) {
            return RoomMapper.INSTANCE.roomToRoomDto(room.get());
        }
        throw new NotFoundException("There is no room with id = " + id);
    }

    @Transactional(readOnly = true)
    public RoomDtoResponse getRoomByName(String name) throws NotFoundException {
        Optional<Room> room = roomRepository.findRoomByName(name);
        if (room.isPresent()) {
            return RoomMapper.INSTANCE.roomToRoomDto(room.get());
        }
        throw new NotFoundException("There is no room with name = " + name);
    }

    @Transactional(readOnly = true)
    public List<RoomDtoResponse> getRoomByOwner(String username) throws NotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
        return roomRepository.findRoomByOwner(user.get())
                .stream()
                .map(RoomMapper.INSTANCE::roomToRoomDto)
                .collect(Collectors.toList());
        }
        throw new NotFoundException("There is no owner with name = " + username);
    }

    @Transactional
    public RoomDtoResponse createRoom(RoomDtoRequest roomDtoRequest) {
        Room message = roomRepository.save(RoomMapper.INSTANCE.roomDtoToRoom(roomDtoRequest));
        return RoomMapper.INSTANCE.roomToRoomDto(message);
    }

    @Transactional
    public RoomDtoResponse updateRoom(Long id, RoomDtoRequest roomDtoRequest) throws NotFoundException {
        Optional<Room> roomOptional = roomRepository.findRoomById(id);
        if (roomOptional.isPresent()) {
            Room room = RoomMapper.INSTANCE.roomDtoToRoom(roomDtoRequest);
            room.setId(id);
            roomRepository.save(room);
            return RoomMapper.INSTANCE.roomToRoomDto(room);
        }
        throw new NotFoundException("There is no room with id = " + id);
    }
    @Transactional
    public void addUserToRoom(Long userId, Long roomId) throws NotFoundException {
        Optional<User> user = userRepository.findUserById(userId);
        Optional<Room> room = roomRepository.findRoomById(roomId);
        if (user.isPresent() && room.isPresent()) {
            user.get().getRooms().add(room.get());
            userRepository.save(user.get());
        } else if(!user.isPresent()) {
            throw new NotFoundException("There is no user in with id = " + userId);
        } else {
            throw new NotFoundException("There is no room with id = " + roomId);
        }
    }

    @Transactional
    public void deleteUserFromRoom(Long userId, Long roomId) throws NotFoundException {
        Optional<User> user = userRepository.findUserById(userId);
        Optional<Room> room = roomRepository.findRoomById(roomId);
        if (user.isPresent() && room.isPresent()) {
            user.get().getRooms().remove(room.get());
            userRepository.save(user.get());
        } else if(!user.isPresent()) {
            throw new NotFoundException("There is no user in with id = " + userId);
        } else {
            throw new NotFoundException("There is no room with id = " + roomId);
        }
    }

    @Transactional
    public void deleteRoom(Long id) throws NotFoundException {
        Optional<Room> roomOptional = roomRepository.findRoomById(id);
        if (roomOptional.isPresent()) {
            roomRepository.delete(roomOptional.get());
        } else {
            throw new NotFoundException("There is no room with id = " + id);
        }
    }
}
