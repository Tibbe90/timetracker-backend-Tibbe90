package com.tibbelin.tajmtrackr.models;

import java.time.LocalDate;
import java.time.LocalTime;

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
    private LocalTime timeStart;
    private LocalTime timeStop;
    private Long duration;
    private TimerStatus status;
    private LocalDate creationDate;

    public TimeTracker(String id) {
        this.Id = id;
        this.timeStart = LocalTime.now();
        this.status = TimerStatus.STARTED;
        this.creationDate = LocalDate.now();
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

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalTime getTimeStop() {
        return timeStop;
    }

    public void setTimeStop(LocalTime timeStop) {
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

    public LocalDate getCreationDate() {
        return creationDate;
    }
}