package ru.simbirsoft.chat_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.simbirsoft.chat_project.entities.Message;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByAuthorName(String name);

    List<Message> findByRoomName(String name);

    Optional<Message> findByContent(String content);

    List<Message> findByAuthorId(Long Id);

    List<Message> findByRoomId(Long Id);

    Optional<Message> findMessageById(Long Id);


}
