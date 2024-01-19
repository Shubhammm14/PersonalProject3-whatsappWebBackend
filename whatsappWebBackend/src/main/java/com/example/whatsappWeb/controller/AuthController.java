package com.example.whatsappWeb.controller;

import com.example.whatsappWeb.entities.User;
import com.example.whatsappWeb.exception.UserException;
import com.example.whatsappWeb.repository.UserRepository;
import com.example.whatsappWeb.request.LoginRequest;
import com.example.whatsappWeb.response.AuthResponse;
import com.example.whatsappWeb.service.CustomUserDetailService;
import com.example.whatsappWeb.service.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final CustomUserDetailService customUserDetailService;

    @Autowired
    public AuthController(PasswordEncoder passwordEncoder, UserRepository userRepository, TokenProvider tokenProvider, CustomUserDetailService customUserDetailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.customUserDetailService = customUserDetailService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
        String email = user.getEmail();
        String full_name = user.getFull_name();
        String password = user.getPassword();
        User usr = userRepository.findByEmail(email);
        if (usr != null) {
            throw new UserException("Email " + email + " already used..try with some other email");
        }
        User us = new User();
        us.setFull_name(full_name);
        us.setEmail(email);
        us.setPassword(passwordEncoder.encode(password));
        userRepository.save(us);
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponse(jwt, true), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> userLoginHandler(@RequestBody LoginRequest req) throws UserException {
        SecurityContextHolder.getContext().setAuthentication(authentication(req.getUsername(), req.getPassword()));
        String jwt=tokenProvider.generateToken(authentication(req.getUsername(), req.getPassword()));
        return new ResponseEntity<>(new AuthResponse(jwt, true), HttpStatus.ACCEPTED);
    }
    public Authentication authentication(String username,String password) throws UserException {
        UserDetails userDetails=customUserDetailService.loadUserByUsername(username);
        if(userDetails==null)
            throw new BadCredentialsException("user does'nt exists ...plss signup");
        if(!passwordEncoder.matches(password,userDetails.getPassword()))
            throw new UserException("Wrong Password");

      return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities()) ;

    }

}