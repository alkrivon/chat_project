package ru.simbirsoft.chat_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.simbirsoft.chat_project.entities.Room;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByName(String name);

    List<Room> findByOwnerName(String name);

    Optional<Room> findById(Long Id);

    List<Room> findByOwnerId(Long Id);
}
