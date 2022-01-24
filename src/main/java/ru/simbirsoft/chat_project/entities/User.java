package ru.simbirsoft.chat_project.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
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

    @Column(name = "ban_end")
    private LocalDateTime ban_end;

    @OneToMany(mappedBy = "owner")
    private List<Room> owner_rooms;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_room",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "room_id")}
    )
    private List<Room> rooms;

    @OneToMany(mappedBy = "author")
    private List<Message> messages;
}
