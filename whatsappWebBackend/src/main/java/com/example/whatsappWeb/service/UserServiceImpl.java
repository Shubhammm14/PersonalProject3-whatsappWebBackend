package com.example.whatsappWeb.service;

import com.example.whatsappWeb.entities.User;
import com.example.whatsappWeb.exception.UserException;
import com.example.whatsappWeb.repository.UserRepository;
import com.example.whatsappWeb.request.UpdateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public User findUserById(Long id) throws UserException {
        Optional<User> op=userRepository.findById(id);
        if((op).isPresent())
        {
            return op.get();
        }
        throw new UserException("User not found");
    }

    @Override
    public User findUserProfile(String jwt) throws UserException {
        String email=tokenProvider.getEmailFromToken(jwt);
        if(email==null){
            throw new BadCredentialsException("received Invalid email//");
        }

        User user=userRepository.findByEmail(email);
        if(user==null){
            throw new UserException("user not found with "+email);
        }
        return user;
    }

    @Override
    public User updateUser(Long userId, UpdateUserRequest req) throws UserException {
        User user=userRepository.findUserById(userId);
        if(user==null)
            throw new UserException("Invalid Id");
        user.setFull_name(req.getFull_name());
        user.setProfile_picture(req.getProfile_picture());
        return userRepository.save(user);
    }

    @Override
    public List<User> searchUser(String query) {

        List<User> users=userRepository.searchUsers(query);
        return users;
    }
}
