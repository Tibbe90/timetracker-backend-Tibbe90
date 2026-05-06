package com.tibbelin.tajmtrackr.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
    
    @PostMapping("/{id}/{categoryId}/start")
    public ResponseEntity<TimeTracker> startTimer(@RequestBody TimeTracker timeTracker, @PathVariable String categoryId, @PathVariable String id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(timeService.startTime(timeTracker, user, categoryId));
    }

    @PostMapping("/{id}/pause")
    public ResponseEntity<TimeTracker> pauseTimer(@PathVariable String id) {
        User user = userService.getUserById(id);
        timeService.pauseTime(user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/resume")
    public ResponseEntity<TimeTracker> resumeTimer(@PathVariable String id) {
        User user = userService.getUserById(id);
        timeService.resumeTimer(user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/stop")
    public ResponseEntity<TimeTracker> stopTimer(@PathVariable String id) {
        User user = userService.getUserById(id);
        timeService.stopTimer(user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<TimeTracker> cancelTimer(@PathVariable String id) {
        User user = userService.getUserById(id);
        timeService.cancelTimer(user);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<TimeTracker> getCurrentTimer(@PathVariable String id){
        User user = userService.getUserById(id);
        return ResponseEntity.ok(timeService.getActiveTimer(user));
    }
    
    @GetMapping("/category/{id}")
    public ResponseEntity<List<TimeTracker>> getTrackers(@PathVariable String id) {
        return ResponseEntity.ok(timeService.getTrackersByCategory(id));
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
