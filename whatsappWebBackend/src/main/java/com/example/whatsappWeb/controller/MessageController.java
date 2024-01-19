package com.example.whatsappWeb.controller;

import com.example.whatsappWeb.entities.Message;
import com.example.whatsappWeb.exception.ChatException;
import com.example.whatsappWeb.exception.MessageException;
import com.example.whatsappWeb.exception.UserException;
import com.example.whatsappWeb.request.SendMessageRequest;
import com.example.whatsappWeb.response.ApiResponse;
import com.example.whatsappWeb.service.MessageService;
import com.example.whatsappWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    private final UserService userService;
    private final MessageService messageService;
    @Autowired
    public MessageController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }
    @PostMapping("/create")
    public ResponseEntity<Message> sendMessageHandler(@RequestBody SendMessageRequest req, @RequestHeader("Authorization") String jwt) throws ChatException, UserException {
        req.setUserId(userService.findUserProfile(jwt).getId());
        Message message=messageService.sendMessage(req);
        return new ResponseEntity<>(message, HttpStatus.GONE);
    }
    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getChatMessagesHandler(@PathVariable("chatId")Long chatId, @RequestHeader("Authorization") String jwt) throws ChatException, UserException {
        List<Message> messages=messageService.getChatMessages(chatId,userService.findUserProfile(jwt));
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
    @GetMapping("/{messageId}")
    public ResponseEntity<Message> getMessagesByIdHandler(@PathVariable("messageId")Long messageId) throws  MessageException {
        Message message=messageService.findMessageById(messageId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{messageId}")
    public ResponseEntity<ApiResponse> deleteHandler(@PathVariable ("messageId") Long messageId, @RequestHeader("Authorization") String jwt) throws UserException, MessageException {
        messageService.deleteMessageById(messageId,userService.findUserProfile(jwt));
        return new ResponseEntity<>(new ApiResponse("message deleted successfully...",false),HttpStatus.OK);
    }
}
