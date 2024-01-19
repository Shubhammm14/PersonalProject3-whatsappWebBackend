package com.example.whatsappWeb.service;

import com.example.whatsappWeb.entities.Chat;
import com.example.whatsappWeb.entities.User;
import com.example.whatsappWeb.exception.ChatException;
import com.example.whatsappWeb.exception.UserException;
import com.example.whatsappWeb.request.GroupChatRequest;

import java.util.List;

public interface ChatService {
    public Chat createChat(User reqUser, Long userId2) throws UserException;
    public Chat findChatById(Long chatId)throws ChatException;
    public List<Chat> findAllChatByUserId(Long userId)throws UserException;
    public Chat createGroup(GroupChatRequest req,User reqUser) throws UserException;
    public Chat addUserToGroup(Long chatId,Long userId ,User reqUser)throws UserException,ChatException;
    public Chat renameGroup(Long chatId,String groupName,User reqUser)throws ChatException,UserException;
    public Chat removeFromGroup(Long userId,Long chatId,User reqUser)throws UserException,ChatException;
    public void deleteChat(Long chatId,Long userId)throws ChatException,UserException;

}
