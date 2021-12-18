package ru.simbirsoft.chat_project.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
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

    @ManyToMany(mappedBy = "rooms")
    private List<User> users;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Message> messages;

}
