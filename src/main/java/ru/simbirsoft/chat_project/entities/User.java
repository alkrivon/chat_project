package ru.simbirsoft.chat_project.entities;

import ru.simbirsoft.chat_project.entities.enums.Role;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

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
    private Set<Room> owner_rooms;

    @ManyToMany
    @JoinTable(
            name = "user_room",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "room_id")}
    )
    private Set<Room> rooms;

    @OneToMany(mappedBy = "author")
    private Set<Message> messages;

    public User(String name, String login, String password,
                Role role, boolean ban_status, LocalDateTime ban_start,
                LocalTime ban_time) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.role = role;
        this.ban_status = ban_status;
        this.ban_start = ban_start;
        this.ban_time = ban_time;
    }

    public User() {

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isBan_status() {
        return ban_status;
    }

    public void setBan_status(boolean ban_status) {
        this.ban_status = ban_status;
    }

    public LocalDateTime getBan_start() {
        return ban_start;
    }

    public void setBan_start(LocalDateTime ban_start) {
        this.ban_start = ban_start;
    }

    public LocalTime getBan_time() {
        return ban_time;
    }

    public void setBan_time(LocalTime ban_time) {
        this.ban_time = ban_time;
    }

    public Set<Room> getOwner_rooms() {
        return owner_rooms;
    }

    public void setOwner_rooms(Set<Room> owner_rooms) {
        this.owner_rooms = owner_rooms;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
}
