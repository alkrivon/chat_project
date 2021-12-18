package ru.simbirsoft.chat_project.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.simbirsoft.chat_project.dto.UserDtoRequest;
import ru.simbirsoft.chat_project.dto.UserDtoResponse;
import ru.simbirsoft.chat_project.entities.Room;
import ru.simbirsoft.chat_project.entities.User;

import java.util.Set;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "login", target = "login"),
            @Mapping(source = "role", target = "role"),
            @Mapping(source = "ban_status", target = "ban_status"),
            @Mapping(source = "ban_start", target = "ban_start"),
            @Mapping(source = "ban_time", target = "ban_time"),
    })
    UserDtoResponse userToUserDto(User entity);

    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "login", target = "login"),
            @Mapping(source = "role", target = "role"),
            @Mapping(source = "ban_status", target = "ban_status"),
            @Mapping(source = "ban_start", target = "ban_start"),
            @Mapping(source = "ban_time", target = "ban_time"),
    })
    User userDtoToUser(UserDtoRequest dto);

}
