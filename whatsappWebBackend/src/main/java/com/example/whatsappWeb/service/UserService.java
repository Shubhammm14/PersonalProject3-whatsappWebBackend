package com.example.whatsappWeb.service;

import com.example.whatsappWeb.entities.User;
import com.example.whatsappWeb.exception.UserException;
import com.example.whatsappWeb.request.UpdateUserRequest;

import java.util.List;

public interface UserService {
    User findUserById(Long id) throws UserException;
    User findUserProfile(String jwt) throws UserException;
    User updateUser(Long userId, UpdateUserRequest req)throws UserException;
    List<User> searchUser(String query);
}
