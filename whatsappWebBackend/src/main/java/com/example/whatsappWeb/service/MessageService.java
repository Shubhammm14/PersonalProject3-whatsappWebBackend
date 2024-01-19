package com.example.whatsappWeb.service;

import com.example.whatsappWeb.entities.Message;
import com.example.whatsappWeb.entities.User;
import com.example.whatsappWeb.exception.ChatException;
import com.example.whatsappWeb.exception.MessageException;
import com.example.whatsappWeb.exception.UserException;
import com.example.whatsappWeb.request.SendMessageRequest;


import java.util.List;

public interface MessageService {
    public Message sendMessage(SendMessageRequest req)throws UserException, ChatException;
    public List<Message> getChatMessages(Long chatId, User reqUser) throws ChatException, UserException;
    public Message findMessageById(Long messageId)throws MessageException;
    public void deleteMessageById(Long messageId,User reqUser) throws MessageException, UserException;

}
