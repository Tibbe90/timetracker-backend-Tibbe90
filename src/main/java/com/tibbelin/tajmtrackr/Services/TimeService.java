package com.tibbelin.tajmtrackr.Services;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tibbelin.tajmtrackr.enums.TimerStatus;
import com.tibbelin.tajmtrackr.models.Category;
import com.tibbelin.tajmtrackr.models.TimeTracker;
import com.tibbelin.tajmtrackr.models.User;

/*
https://www.mongodb.com/docs/drivers/java/sync/current/crud/update-documents/
*/

@Service
public class TimeService {
    
    private final MongoOperations mongoOperations;
    private final String[] queryArray = {"STARTED", "PAUSED"};

    public TimeService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public TimeTracker startTime(TimeTracker newTracker, User user, String categoryId) {
        TimeTracker timeTracker = newTracker;
        timeTracker.setUserId(user.getId());
        timeTracker.setCategoryId(categoryId);
        Query query = Query.query(Criteria.where("status").is(queryArray).and("userId").is(user.getId()));
        if (mongoOperations.exists(query, TimeTracker.class)) {
            throw new IllegalStateException("a timer is already active");
        }
        return mongoOperations.insert(timeTracker);
    }

    public void pauseTime(User user) {
        Query checkForOutOfSync = Query.query(Criteria.where("status").is("PAUSED").and("userId").is(user.getId()));
        Query findTimer = Query.query(Criteria.where("status").is("STARTED").and("userId").is(user.getId()));
        // -----------------------------------------------------------
        // Could add an update to the timer here if backend is out of sync with frontend
        if (mongoOperations.exists(checkForOutOfSync, TimeTracker.class)) {
            throw new IllegalStateException("You already have a paused timer");
            /*
            TimeTracker timeTracker = mongoOperations.findOne(checkForOutOfSync, TimeTracker.class);
            timeTracker.setStatus(TimerStatus.PAUSED);
            return timeTracker;
            */
        }
        TimeTracker timeTracker = mongoOperations.findOne(findTimer, TimeTracker.class);
        timeCalculations(timeTracker, "pause", user);
    }

    public void resumeTimer(User user) {
        Query findTimer = Query.query(Criteria.where("status").is("PAUSED").and("userId").is(user.getId()));
        TimeTracker timeTracker = mongoOperations.findOne(findTimer, TimeTracker.class);
        timeCalculations(timeTracker, "resume", user);
    }

    public void stopTimer(User user) {
        Query findTimer = Query.query(Criteria.where("status").is(queryArray).and("userId").is(user.getId()));
        TimeTracker timeTracker = mongoOperations.findOne(findTimer, TimeTracker.class);
        timeCalculations(timeTracker, "stop", user);
    }

    public void cancelTimer(User user) {
        String[] cancelQuery = {TimerStatus.PAUSED.toString(), TimerStatus.STARTED.toString(), TimerStatus.STOPPED.toString()};
        Query findTimer = Query.query(Criteria.where("status").is(cancelQuery).and("userId").is(user.getId()));
        mongoOperations.remove(findTimer, TimeTracker.class);
    }

    public TimeTracker getActiveTimer(User user) {
        Query findTimer = Query.query(Criteria.where("status").is(queryArray).and("userId").is(user.getId()));
        return mongoOperations.findOne(findTimer, TimeTracker.class);
    }

    public List<TimeTracker> getTrackersByCategory(String categoryId) {
        Query findCategory = Query.query(Criteria.where("categoryId").is(categoryId));
        return mongoOperations.find(findCategory, TimeTracker.class);
    }



    // https://www.geeksforgeeks.org/java/localtime-until-method-in-java-with-examples/
    public void timeCalculations(TimeTracker timeTracker, String operation, User user) {
        LocalTime start = timeTracker.getTimeStart();
        Update update;
        TimerStatus findStatus;

        switch (operation) {
            case "pause":
                LocalTime pause = LocalTime.now();
                Long duration = start.until(pause, ChronoUnit.MILLIS);
                update = Update.update("duration", duration)
                .set("status", TimerStatus.PAUSED);
                findStatus = TimerStatus.STARTED;
                break;
            case "resume":
                update = Update.update("timeStart", LocalTime.now())
                .set("status", TimerStatus.STARTED);
                findStatus = TimerStatus.PAUSED;
                break;
            case "stop":
                LocalTime stop = LocalTime.now();
                Long finalDuration = start.until(stop, ChronoUnit.MILLIS);
                finalDuration += timeTracker.getDuration();
                update = Update.update("duration", finalDuration)
                .set("status", TimerStatus.STOPPED);
                findStatus = TimerStatus.STARTED;
                break;

            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        Query findTimer = Query.query(Criteria.where("status").is(findStatus).and("userId").is(user.getId()));
        mongoOperations.updateFirst(findTimer, update, TimeTracker.class);
            
        }
    }