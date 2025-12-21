package com.campus.management.model;

import java.time.LocalDateTime;

public class Event {
    private String id;
    private String title;
    private String description;
    private String organizerId;
    private LocalDateTime start;
    private LocalDateTime end;
    private EventStatus status;

    public Event(String id, String title, String description, String organizerId,
                 LocalDateTime start, LocalDateTime end, EventStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.organizerId = organizerId;
        this.start = start;
        this.end = end;
        this.status = status;
    }

    // getters / setters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getOrganizerId() { return organizerId; }
    public LocalDateTime getStart() { return start; }
    public LocalDateTime getEnd() { return end; }
    public EventStatus getStatus() { return status; }
    public void setStatus(EventStatus status) { this.status = status; }
}
