package com.example.whatsappWeb.controller;

import com.example.whatsappWeb.entities.User;
import com.example.whatsappWeb.exception.UserException;
import com.example.whatsappWeb.repository.UserRepository;
import com.example.whatsappWeb.request.UpdateUserRequest;
import com.example.whatsappWeb.response.ApiResponse;
import com.example.whatsappWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
    }
    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization")String token) throws UserException {
        User user=userService.findUserProfile(token);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchHandler(@RequestParam ("name") String name){
        return new ResponseEntity<>(userService.searchUser(name), HttpStatus.OK);
    }
    @PutMapping("/update/{userId}")
    public ResponseEntity<ApiResponse> updateUserHandler(@PathVariable Long userId, @RequestBody UpdateUserRequest req, @RequestHeader("Authorization") String token) throws UserException {
        User user=userService.findUserProfile(token);
        userService.updateUser(userId, req);
        return new ResponseEntity<>(new ApiResponse("user updated successfully",true),HttpStatus.OK);
    }

}
