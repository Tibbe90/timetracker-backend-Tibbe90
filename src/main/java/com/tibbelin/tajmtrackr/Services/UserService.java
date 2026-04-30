package com.tibbelin.tajmtrackr.services;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.tibbelin.tajmtrackr.enums.Roles;
import com.tibbelin.tajmtrackr.models.User;

@Service
public class UserService {
    private final MongoOperations mongoOperations;

    public UserService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public User createUser(User user) {
        if(user.getUsername().isBlank() || user.getEmail().isBlank() ||user.getPassword().isBlank()) {
            throw new IllegalArgumentException("All fields are required");
        }
        Query queryName = Query.query(Criteria.where("username").is(user.getUsername()));
        Query queryMail = Query.query(Criteria.where("email").is(user.getEmail()));
        if (mongoOperations.exists(queryName, User.class)) {
            throw new IllegalArgumentException("Username already exists");
        }
        else if (mongoOperations.exists(queryMail, User.class)) {
            throw new IllegalArgumentException("E-mail already exists");
        }
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
}