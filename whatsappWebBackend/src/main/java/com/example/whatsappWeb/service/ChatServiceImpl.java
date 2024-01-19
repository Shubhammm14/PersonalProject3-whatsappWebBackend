package com.example.whatsappWeb.service;

import com.example.whatsappWeb.entities.Chat;
import com.example.whatsappWeb.entities.User;
import com.example.whatsappWeb.exception.ChatException;
import com.example.whatsappWeb.exception.UserException;
import com.example.whatsappWeb.repository.ChatRepository;
import com.example.whatsappWeb.repository.UserRepository;
import com.example.whatsappWeb.request.GroupChatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {
    private final UserService userService;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Autowired
    public ChatServiceImpl(UserService userService, ChatRepository chatRepository,
                           UserRepository userRepository) {
        this.userService = userService;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Chat createChat(User reqUser, Long userId2) throws UserException {
        User user= userService.findUserById(userId2);
        Chat isChatExists=chatRepository.singleChatByUserId(reqUser,user);
        if(isChatExists!=null){
            //chat exists so return the chats
            return isChatExists;

        }
        Chat chat=new Chat();
        chat.setCreatedBy(reqUser);
        chat.getUsers().add(user);
        chat.getUsers().add(reqUser);
        chat.setGroup(false);

        return chatRepository.save(chat);

    }

    @Override
    public Chat findChatById(Long chatId) throws ChatException {

        Optional<Chat> opt =chatRepository.findById(chatId);
        if(opt.isPresent())
            return opt.get();
        throw new ChatException("chat not found with id"+chatId);
    }

    @Override
    public List<Chat> findAllChatByUserId(Long userId) throws UserException {
        List<Chat> chats=chatRepository.findChatByUserId(userId);
        return chats;
    }

    @Override
    public Chat createGroup(GroupChatRequest req, User reqUser) throws UserException {
        Chat chatGroup=new Chat();
        chatGroup.setCreatedBy(reqUser);
        chatGroup.setGroup(true);
        chatGroup.setChat_name(req.getChat_name());
        chatGroup.setChat_image(req.getChat_image());
        chatGroup.getAdmins().add(reqUser);
        for (Long userId :req.getUserIds()
             ) {chatGroup.getUsers().add(userService.findUserById(userId));

        }
        chatRepository.save(chatGroup);
        return chatGroup;
    }

    @Override
    public Chat addUserToGroup(Long chatId, Long userId,User reqUser) throws UserException, ChatException {
        Optional<Chat> chat=chatRepository.findById(chatId);
        if(!chat.isPresent()){
            throw new ChatException("Chat group  doesnt exists"+chat);
        }
        User user=userService.findUserById(userId);
        if(!chat.get().getAdmins().contains(reqUser))
            throw new UserException("user is not an admin to this chat group"+chat.get());
        chat.get().getUsers().add(user);
        return chatRepository.save(chat.get());

    }

    @Override
    public Chat renameGroup(Long chatId, String groupName, User reqUser) throws ChatException, UserException {
        Optional<Chat> chat=chatRepository.findById(chatId);
        if(!chat.get().getUsers().contains(reqUser))
            throw new UserException("user is not a member to this chat"+reqUser);
        chat.get().setChat_name(groupName);
        return chatRepository.save(chat.get());
    }

    @Override
    public Chat removeFromGroup(Long userId, Long chatId, User reqUser) throws UserException, ChatException {
        Optional<Chat> chat=chatRepository.findById(chatId);
        if(!chat.isPresent()){
            throw new ChatException("Chat group  doesnt exists"+chat);
        }
        User user=userService.findUserById(userId);
        if(!chat.get().getAdmins().contains(reqUser)) {
            if (!chat.get().getUsers().contains(user) && !user.equals(reqUser))
                throw new UserException("bad credentials");
        }
        if(!chat.get().getUsers().contains(user))
            throw new UserException("bad credentials");
        chat.get().getUsers().remove(user);
        return chatRepository.save(chat.get());

    }

    @Override
    public void deleteChat(Long chatId, Long userId) throws ChatException, UserException {
        Optional<Chat> chatOptional = chatRepository.findById(chatId);
        if (!chatOptional.isPresent()) {
            throw new ChatException("Chat group does not exist with id: " + chatId);
        }

        Chat chat = chatOptional.get();
        if (chat.getAdmins().contains(userService.findUserById(userId))) {
            chatRepository.delete(chat);
        } else {
            throw new UserException("User does not have the required permissions to delete the chat");
        }
    }

}
