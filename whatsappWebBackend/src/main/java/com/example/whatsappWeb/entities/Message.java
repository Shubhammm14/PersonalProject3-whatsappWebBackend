package com.example.whatsappWeb.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    private String Content;
    private LocalDateTime localDateTime;
    @ManyToOne
    @JoinColumn(name = "user") // Define the appropriate column name
    private User user;

    @ManyToOne
    @JoinColumn(name = "chat") // Define the appropriate column name
    private Chat chat;

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", Content='" + Content + '\'' +
                ", localDateTime=" + localDateTime +
                ", user=" + user +
                ", chat=" + chat +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
    public Message() {
    }

    public Message(Long id, String content, LocalDateTime localDateTime, User user, Chat chat) {
        this.id = id;
        this.Content = content;
        this.localDateTime = localDateTime;
        this.user = user;
        this.chat = chat;
    }


}
