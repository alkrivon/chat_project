package ru.simbirsoft.chat_project.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.simbirsoft.chat_project.dto.MessageDtoRequest;
import ru.simbirsoft.chat_project.dto.MessageDtoResponse;
import ru.simbirsoft.chat_project.entities.Message;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "content", source = "content"),
            @Mapping(target = "author", source = "author.username"),
            @Mapping(target = "room", source = "room.name")
    })
    MessageDtoResponse messageToMessageDto(Message entity);

    @Mappings({
            @Mapping(target = "content", source = "content"),
            @Mapping(target = "author.id", source = "author"),
            @Mapping(target = "room.id", source = "room")
    })
    Message messageDtoToMessage(MessageDtoRequest dto);
}
