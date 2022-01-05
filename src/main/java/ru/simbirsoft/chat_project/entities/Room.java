package ru.simbirsoft.chat_project.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "private_status")
    private boolean private_status;

    @ManyToOne
    private User owner;

    @ManyToMany(mappedBy = "rooms", fetch = FetchType.LAZY)
    private List<User> users;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Message> messages;

}
