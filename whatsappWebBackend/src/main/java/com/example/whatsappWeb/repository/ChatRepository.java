package com.example.whatsappWeb.repository;

import com.example.whatsappWeb.entities.Chat;
import com.example.whatsappWeb.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ChatRepository extends JpaRepository<Chat,Long> {

    @Query("select c from Chat c join c.users u where u.id = :userId")
    public List<Chat> findChatByUserId(@Param("userId") Long id);

    @Query("select c from Chat c where c.isGroup = false and :user member of c.users and :reqUser member of c.users")
    public Chat singleChatByUserId(@Param("user") User user, @Param("reqUser") User reqUser);

}
