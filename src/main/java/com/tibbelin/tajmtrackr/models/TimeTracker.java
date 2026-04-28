package com.tibbelin.tajmtrackr.models;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.tibbelin.tajmtrackr.enums.TimerStatus;

/*
Själva timern körs i frontend.
Kör instant instället för LocalDateTime
https://stackoverflow.com/questions/32437550/whats-the-difference-between-instant-and-localdatetime

kollektionen innehåller ändå timeStart för att kunna återuppta en session som blivit avslutad
*/


@Document(collection = "time_entries")
public class TimeTracker {
    

    @Id
    private String Id;
    private String userId;
    private String categoryId;
    private Instant timeStart;
    private Instant timeStop;
    private Long duration;
    private TimerStatus status;

    public TimeTracker(String id, String userId, String categoryId, Instant timeStart, Instant timeStop, Long duration,
            TimerStatus status) {
        Id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.timeStart = timeStart;
        this.timeStop = timeStop;
        this.duration = duration;
        this.status = status;
    }

    public TimeTracker() {}

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public TimerStatus getStatus() {
        return status;
    }

    public void setStatus(TimerStatus status) {
        this.status = status;
    }

    public Instant getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Instant timeStart) {
        this.timeStart = timeStart;
    }

    public Instant getTimeStop() {
        return timeStop;
    }

    public void setTimeStop(Instant timeStop) {
        this.timeStop = timeStop;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}