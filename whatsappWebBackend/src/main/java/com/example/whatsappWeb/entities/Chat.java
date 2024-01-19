package com.example.whatsappWeb.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String chat_name;
    private String chat_image;
    @Column(name="is_group")
    private boolean isGroup;
    @ManyToMany
    private Set<User> admins=new HashSet<>();
    @JoinColumn(name = "created_by")
    @ManyToOne
    private User createdBy;
    @OneToMany
    private List<Message> messages=new ArrayList<>();
    @ManyToMany
    private Set<User> users=new HashSet<>();
    public Set<User> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<User> admins) {
        this.admins = admins;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", chat_name='" + chat_name + '\'' +
                ", chat_image='" + chat_image + '\'' +
                ", isGroup=" + isGroup +
                ", admins=" + admins +
                ", createdBy=" + createdBy +
                ", messages=" + messages +
                ", users=" + users +
                '}';
    }


    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (chat_name != null ? chat_name.hashCode() : 0);
        return result;
    }

    public Chat(Long id, String chat_name, String chat_image, boolean isGroup, Set<User> admins, User createdBy, List<Message> messages, Set<User> users) {
        this.id = id;
        this.chat_name = chat_name;
        this.chat_image = chat_image;
        this.isGroup = isGroup;
        this.admins = admins;
        this.createdBy = createdBy;
        this.messages = messages;
        this.users = users;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Chat chat = (Chat) obj;

        if (id != null ? !id.equals(chat.id) : chat.id != null) return false;
        if (chat_name != null ? !chat_name.equals(chat.chat_name) : chat.chat_name != null) return false;

        return true;
    }


    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChat_name() {
        return chat_name;
    }

    public void setChat_name(String chat_name) {
        this.chat_name = chat_name;
    }

    public String getChat_image() {
        return chat_image;
    }

    public void setChat_image(String chat_image) {
        this.chat_image = chat_image;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public Chat() {
    }


}
