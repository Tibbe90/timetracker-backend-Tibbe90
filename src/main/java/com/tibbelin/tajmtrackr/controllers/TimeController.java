package com.tibbelin.tajmtrackr.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tibbelin.tajmtrackr.Services.TimeService;
import com.tibbelin.tajmtrackr.Services.UserService;
import com.tibbelin.tajmtrackr.models.Category;
import com.tibbelin.tajmtrackr.models.TimeTracker;
import com.tibbelin.tajmtrackr.models.User;

/*
https://docs.spring.io/spring-security/reference/servlet/authentication/architecture.html
*/

@RestController
@CrossOrigin
@RequestMapping("api/time")
public class TimeController {
    private final TimeService timeService;
    private final UserService userService;

    public TimeController(TimeService timeService, UserService userService) {
        this.timeService = timeService;
        this.userService = userService;
    }
    
    @PostMapping("/start")
    public ResponseEntity<TimeTracker> startTimer(@RequestBody TimeTracker timeTracker, @RequestBody Category category, Authentication authentication) {
        User user = userService.getUserByName(authentication.getName());
        return ResponseEntity.ok(timeService.startTime(timeTracker, user, category));
    }

    @PostMapping("/pause")
    public ResponseEntity<TimeTracker> pauseTimer(Authentication authentication) {
        User user = userService.getUserByName(authentication.getName());
        timeService.pauseTime(user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/resume")
    public ResponseEntity<TimeTracker> resumeTimer(Authentication authentication) {
        User user = userService.getUserByName(authentication.getName());
        timeService.resumeTimer(user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/stop")
    public ResponseEntity<TimeTracker> stopTimer(Authentication authentication) {
        User user = userService.getUserByName(authentication.getName());
        timeService.stopTimer(user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/cancel")
    public ResponseEntity<TimeTracker> cancelTimer(Authentication authentication) {
        User user = userService.getUserByName(authentication.getName());
        timeService.cancelTimer(user);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/status")
    public ResponseEntity<TimeTracker> getCurrentTimer(Authentication authentication){
        User user = userService.getUserByName(authentication.getName());
        return ResponseEntity.ok(timeService.getActiveTimer(user));
    }
    
    @GetMapping("/category/{id}")
    public ResponseEntity<List<TimeTracker>> getTrackers(@PathVariable String id, Authentication authentication) {
        User user = userService.getUserByName(authentication.getName());
        return ResponseEntity.ok(timeService.getTrackersByCategory(user.getId(), id));
    }

    /*
    Unsure if necessary
    @GetMapping("/{id}")
    public ResponseEntity<TimeTracker> getTimerById(@PathVariable String id) {
        return null;
    }
    */

    //Extra function, will possibly be unused in V1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimer(@RequestParam String id) {
        return null;
    }
}
