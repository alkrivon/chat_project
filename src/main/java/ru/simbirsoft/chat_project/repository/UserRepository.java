package ru.simbirsoft.chat_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.simbirsoft.chat_project.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

//    List<User> findAllByRole(Role role);

    Optional<User> findByLogin(String login);

    Optional<User> findByUsername(String username);

    Optional<User> findUserById(Long id);



    //Метод для поиска списка пользователей в комнате. Не совсем понимаю, как его сделать:
    //List<User> findAllByRooms(List<Room> rooms);
}
