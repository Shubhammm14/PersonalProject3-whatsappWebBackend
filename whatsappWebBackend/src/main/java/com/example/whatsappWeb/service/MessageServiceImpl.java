package com.example.whatsappWeb.service;

import com.example.whatsappWeb.entities.Message;
import com.example.whatsappWeb.entities.User;
import com.example.whatsappWeb.exception.ChatException;
import com.example.whatsappWeb.exception.MessageException;
import com.example.whatsappWeb.exception.UserException;
import com.example.whatsappWeb.repository.MessageRepository;
import com.example.whatsappWeb.request.SendMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class MessageServiceImpl implements MessageService{
    private final ChatService chatService;
    private final MessageRepository messageRepository;
    private final UserService userService;
    @Autowired
    public MessageServiceImpl(ChatService chatService, MessageRepository messageRepository, UserService userService) {
        this.chatService = chatService;
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    @Override
    public Message sendMessage(SendMessageRequest req) throws UserException, ChatException {
        User user=userService.findUserById(req.getUserId());

        Message message=new Message();
        message.setChat(chatService.findChatById(req.getChatId()));
        message.setUser(user);
        message.setContent(req.getContent());
        message.setLocalDateTime( LocalDateTime.now());

        return message;
    }

    @Override
    public List<Message> getChatMessages(Long chatId,User reqUser) throws ChatException, UserException {
        if(!chatService.findChatById(chatId).getUsers().contains(reqUser))
        throw new UserException("user does not belongs to this chat");

        return messageRepository.findByChatId(chatId);
    }

    @Override
    public Message findMessageById(Long messageId) throws MessageException {
        Optional<Message> opt=messageRepository.findById(messageId);
        if(opt.isPresent())
        return opt.get();
        throw new MessageException("message do not exists");
    }

    @Override
    public void deleteMessageById(Long messageId,User reqUser) throws MessageException, UserException {
        Optional<Message> opt=messageRepository.findById(messageId);
        if(!opt.isPresent())
            throw new MessageException("message do not exists");
        if(!opt.get().getUser().equals(reqUser))
            throw new UserException("user do not belong to particular message");
        messageRepository.delete(opt.get());

    }
}
