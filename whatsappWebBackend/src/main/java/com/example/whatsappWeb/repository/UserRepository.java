package com.example.whatsappWeb.repository;

import com.example.whatsappWeb.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    User findByEmail(String email);
    User findUserById(Long userId);
    @Query("SELECT u FROM User u WHERE LOWER(u.full_name) LIKE %:query% OR LOWER(u.email) LIKE %:query%")
    List<User> searchUsers(@Param("query") String query);
}
