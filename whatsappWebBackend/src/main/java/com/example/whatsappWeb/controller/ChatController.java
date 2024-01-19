package com.example.whatsappWeb.controller;

import com.example.whatsappWeb.entities.Chat;
import com.example.whatsappWeb.exception.ChatException;
import com.example.whatsappWeb.exception.UserException;
import com.example.whatsappWeb.request.GroupChatRequest;
import com.example.whatsappWeb.request.SingleChatRequest;
import com.example.whatsappWeb.response.ApiResponse;
import com.example.whatsappWeb.service.ChatService;
import com.example.whatsappWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {
    private final UserService userService;
    private final ChatService chatService;
    @Autowired
    public ChatController(UserService userService, ChatService chatService) {
        this.userService = userService;
        this.chatService = chatService;
    }
    @PostMapping("/single")
    public ResponseEntity<Chat> chatCreateHandler(@RequestBody SingleChatRequest singleChatRequest, @RequestHeader("Authorization") String jwt) throws UserException {
        Chat chat=chatService.createChat(userService.findUserProfile(jwt),singleChatRequest.getUserId());
        return new ResponseEntity<>(chat, HttpStatus.CREATED);
    }
    @PostMapping("/group")
    public ResponseEntity<Chat> groupChatCreateHandler(@RequestBody GroupChatRequest groupeChatRequest, @RequestHeader("Authorization") String jwt) throws UserException {
        Chat chat=chatService.createGroup(groupeChatRequest,userService.findUserProfile(jwt));
        return new ResponseEntity<>(chat, HttpStatus.CREATED);
    }
    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> findChatByIdHandler(@PathVariable("chatId") Long chatId) throws ChatException {
        Chat chat=chatService.findChatById(chatId);
        return new ResponseEntity<>(chat,HttpStatus.OK);
    }
    @GetMapping("/user")
    public ResponseEntity<List<Chat>> findChatByUserIdHandler( @RequestHeader("Authorization") String jwt) throws UserException {
        List<Chat> chat=chatService.findAllChatByUserId(userService.findUserProfile(jwt).getId());
        return new ResponseEntity<>(chat, HttpStatus.CREATED);
    }
    @PutMapping("/{chatId}/add/{userId}")
    public ResponseEntity<Chat> addUserToGroupHandler(@PathVariable("chatId")Long chatId,@PathVariable ("userId")Long userId,@RequestHeader ("Authentication")String jwt) throws UserException, ChatException {
      Chat chat=chatService.addUserToGroup(chatId,userId,userService.findUserProfile(jwt));
      return new ResponseEntity<>(chat,HttpStatus.OK);
    }
    @PutMapping("/{chatId}/rename/{groupName}")
    public ResponseEntity<Chat> renameHandler(@PathVariable("chatId")Long chatId,@PathVariable ("groupName")String groupName,@RequestHeader ("Authentication")String jwt) throws UserException, ChatException {
        Chat chat=chatService.renameGroup(chatId,groupName,userService.findUserProfile(jwt));
        return new ResponseEntity<>(chat,HttpStatus.OK);
    }

    @PutMapping("/{chatId}/rename/{userId}")
    public ResponseEntity<Chat> removeFromGroupHandler(@PathVariable("chatId")Long chatId,@PathVariable ("userId")Long userId,@RequestHeader ("Authentication")String jwt) throws UserException, ChatException {
        Chat chat=chatService.removeFromGroup(userId,chatId,userService.findUserProfile(jwt));
        return new ResponseEntity<>(chat,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{chatId}")
    public ResponseEntity<ApiResponse> chatDeleteHandler(@PathVariable("chatId")Long chatId, @RequestHeader ("Authentication")String jwt) throws UserException, ChatException {
        chatService.deleteChat(chatId,userService.findUserProfile(jwt).getId());
        ApiResponse apiResponse=new ApiResponse("Chat deleted Successfully...",true);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
}
