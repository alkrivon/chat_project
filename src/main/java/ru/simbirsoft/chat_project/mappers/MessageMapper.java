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
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "content", target = "content"),
            @Mapping(source = "author", target = "author"),
            @Mapping(source = "room", target = "room")
    })
    MessageDtoResponse messageToMessageDto(Message entity);

    @Mappings({
            @Mapping(source = "content", target = "content"),
            @Mapping(source = "author", target = "author"),
            @Mapping(source = "room", target = "room")
    })
    Message messageDtoToMessage(MessageDtoRequest dto);
}
