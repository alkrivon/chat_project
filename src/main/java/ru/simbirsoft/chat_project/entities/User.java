package ru.simbirsoft.chat_project.entities;

import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import ru.simbirsoft.chat_project.entities.enums.Status;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "user_roles",
//            joinColumns = {@JoinColumn(name = "user_id")},
//            inverseJoinColumns = {@JoinColumn(name = "role_id")})
//    private List<Role> roles;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

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

    @OneToMany(mappedBy = "author")
    private List<Message> messages;

//    @LastModifiedDate
//    @Column(name = "updated")
//    private Date updated;
}
