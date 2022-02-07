package ru.simbirsoft.chat_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.simbirsoft.chat_project.entities.Room;
import ru.simbirsoft.chat_project.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findRoomByName(String name);

    List<Room> findRoomByOwner(User user);

    Optional<Room> findRoomById(Long Id);

    @Modifying
    @Query("UPDATE Room r SET r.name = :name WHERE r.id = :id")
    void setName(@Param("id") Long id, @Param("name") String name);
}
