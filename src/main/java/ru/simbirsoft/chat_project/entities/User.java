package ru.simbirsoft.chat_project.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "ban_start")
    private LocalDateTime ban_start;

    @Column(name = "ban_time")
    private LocalTime ban_time;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Room> owner_rooms;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_room",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "room_id")}
    )
    private List<Room> rooms;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Message> messages;
}
