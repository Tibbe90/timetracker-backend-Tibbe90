package com.tibbelin.tajmtrackr.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tibbelin.tajmtrackr.Services.UserService;
import com.tibbelin.tajmtrackr.dto.UserDurationsDTO;
import com.tibbelin.tajmtrackr.models.User;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PostMapping("/admin") 
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDurationsDTO>> getAllUsers(Authentication authentication){
        return ResponseEntity.ok(userService.getAllUserDurations());
    }

    @PostMapping("/login") 
    public ResponseEntity<User> login(Authentication authentication){
        User user = userService.getUserByUsername(authentication.getName());
        return ResponseEntity.ok(user);
    }
}
