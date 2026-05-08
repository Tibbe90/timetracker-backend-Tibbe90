package com.tibbelin.tajmtrackr.Services;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tibbelin.tajmtrackr.enums.Roles;
import com.tibbelin.tajmtrackr.models.TimeTracker;
import com.tibbelin.tajmtrackr.models.User;

@Service
public class UserService {
    private final MongoOperations mongoOperations;
    private final PasswordEncoder passwordEncoder;

    public UserService(MongoOperations mongoOperations, PasswordEncoder passwordEncoder) {
        this.mongoOperations = mongoOperations;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        if (user.getUsername().isBlank() || user.getEmail().isBlank() || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("All fields are required");
        }
        Query queryName = Query.query(Criteria.where("username").is(user.getUsername()));
        Query queryMail = Query.query(Criteria.where("email").is(user.getEmail()));
        if (mongoOperations.exists(queryName, User.class)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else if (mongoOperations.exists(queryMail, User.class)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Roles.USER);
        return mongoOperations.insert(user);
    }

    public List<User> getAllUsers() {
        return mongoOperations.findAll(User.class);
    }

    public User getUserByName(String name) {
        Query query = Query.query(Criteria.where("username").is(name));
        return mongoOperations.findOne(query, User.class);
    }

    public User getUserById(String id) {
        Query query = Query.query(Criteria.where("id").is(id));
        return mongoOperations.findOne(query, User.class);
    }

    public List<User> getAllUsersForAdmin() {
        mongoOperations.findAll(TimeTracker.class);
        return null;
    }
}