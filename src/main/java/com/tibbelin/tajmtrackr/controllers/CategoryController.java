package com.tibbelin.tajmtrackr.controllers;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tibbelin.tajmtrackr.models.Category;
import com.tibbelin.tajmtrackr.models.TimeTracker;
import com.tibbelin.tajmtrackr.models.UpdateCategoryDTO;

import org.springframework.web.bind.annotation.RequestMapping;
/*
Info om Authentication
https://docs.spring.io/spring-security/reference/servlet/authentication/architecture.html
*/

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CategoryController {
    
    @PostMapping("/category")
    public ResponseEntity<Category> newCategory(@RequestBody Category category, Authentication authentication) {
        return null;
    }

    @GetMapping("/my-categories")
    public ResponseEntity<List<Category>> getPersonalCategories(Authentication authentication) {
        //User user = userService.findByUsername(authentication.getName)
        return null;
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<TimeTracker>> getTrackers(@PathVariable String id) {
        return null;
    }

    //Extra function, may be unused in V1
    @DeleteMapping("/category/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id, Authentication authentication) {
        return null;
    }

    @PatchMapping("/category/{id}")
    public ResponseEntity<Category> updateCategoryName(@PathVariable String id, @RequestBody UpdateCategoryDTO categoryDTO, Authentication authentication) {
        return null;
    }
}
