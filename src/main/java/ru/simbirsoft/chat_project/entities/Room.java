package ru.simbirsoft.chat_project.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    private Set<User> users;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private Set<Message> messages;

    public Room() {
    }

    public Room(String name, boolean private_status, User owner) {
        this.name = name;
        this.private_status = private_status;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrivate_status() {
        return private_status;
    }

    public void setPrivate_status(boolean private_status) {
        this.private_status = private_status;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
}
