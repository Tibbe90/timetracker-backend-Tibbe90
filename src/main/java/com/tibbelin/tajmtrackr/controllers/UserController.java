package com.tibbelin.tajmtrackr.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tibbelin.tajmtrackr.Services.UserService;
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

    @GetMapping("/admin") 
    public ResponseEntity<List<User>> getAllUsers(Authentication authentication){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/login/{username}") 
    public ResponseEntity<User> getAllUsers(@PathVariable String username){
        return ResponseEntity.ok(userService.getUserByName(username));
    }
    /* USE THIS IF CONSIDERING A CUSTOM LOGIN, CURRENTLY USING BUILT IN /login

    @PostMapping("/login")
    public ResponseEntity<User> getUser(@RequestBody LoginDTO loginDTO,Authentication authentication) {
        return null;
    }
    */
}
