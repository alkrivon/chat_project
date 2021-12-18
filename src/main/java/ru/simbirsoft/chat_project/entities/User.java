package ru.simbirsoft.chat_project.entities;

import lombok.Data;
import ru.simbirsoft.chat_project.entities.enums.Role;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "ban_status")
    private boolean ban_status;

    @Column(name = "ban_start")
    private LocalDateTime ban_start;

    @Column(name = "ban_time")
    private LocalTime ban_time;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Room> owner_rooms;

    @ManyToMany
    @JoinTable(
            name = "user_room",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "room_id")}
    )
    private List<Room> rooms;

    @OneToMany(mappedBy = "author")
    private List<Message> messages;

}
