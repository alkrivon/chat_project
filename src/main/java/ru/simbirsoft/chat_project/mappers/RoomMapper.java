package ru.simbirsoft.chat_project.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.simbirsoft.chat_project.dto.RoomDtoRequest;
import ru.simbirsoft.chat_project.dto.RoomDtoResponse;
import ru.simbirsoft.chat_project.entities.Room;


@Mapper(componentModel = "spring")
public interface RoomMapper {

    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "private_status", source = "private_status"),
            @Mapping(target = "owner", source = "owner.name"),
    })
    RoomDtoResponse roomToRoomDto(Room entity);

    @Mappings({
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "private_status", source = "private_status"),
            @Mapping(target = "owner.id", source = "owner")
    })
    Room roomDtoToRoom(RoomDtoRequest dto);
}
