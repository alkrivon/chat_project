package ru.simbirsoft.chat_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.simbirsoft.chat_project.entities.Role;
import ru.simbirsoft.chat_project.entities.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    Optional<User> findByLogin(String login);

    Optional<User> findByUsername(String username);

    Optional<User> findUserById(Long id);

    @Modifying
    @Query("UPDATE User u SET u.status = :status WHERE u.id = :id")
     void setStatus(@Param("id") Long id, @Param("status") boolean status);

    @Modifying
    @Query("UPDATE User u SET u.username = :username WHERE u.id = :id")
    void setUsername(@Param("id") Long id, @Param("username") String username);

    @Modifying
    @Query("UPDATE User u SET u.ban_end = :ban_end WHERE u.id = :id")
    void setBanEnd(@Param("id") Long id, @Param("ban_end") LocalDateTime ban_end);

    @Modifying
    @Query("UPDATE User u SET u.role = :role WHERE u.id = :id")
    void setRole(@Param("id") Long id, @Param("role") Role role);
}
