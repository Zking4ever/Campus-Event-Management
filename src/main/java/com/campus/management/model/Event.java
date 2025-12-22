package com.campus.management.model;

import javafx.scene.image.Image;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event {
    private String id;
    private String title;
    private String description;
    private String organizerId;
    private LocalDateTime start;
    private LocalDateTime end;
    private EventStatus status;
    private String imageUrl;
    private LocalDateTime dateCreated;
    private Image image;
    private ArrayList<String> feedBack;

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
    public  void  setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public  void  setDescription(String description) { this.description = description; }
    public String getOrganizerId() { return organizerId; }
    public LocalDateTime getStart() { return start; }
    public  void  setStart(LocalDateTime start) { this.start = start; }
    public LocalDateTime getEnd() { return end; }
    public EventStatus getStatus() { return status; }
    public  void  setEnd(LocalDateTime end) { this.end = end; }
    public void setStatus(EventStatus status) { this.status = status; }
    public String getImageUrl() {
        // Placeholder image URL, in real scenario this would be a field
        return "https://via.placeholder.com/80";
    }
    public LocalDateTime getDateCreated() { return dateCreated; }
    public void setDateCreated(LocalDateTime dateCreated) { this.dateCreated = dateCreated; }
    public Image getImage() { return image; }
    public void setImage(Image image) { this.image = image; }
    public ArrayList<String> getFeedBack() { return feedBack; }
    public  void setFeedback(ArrayList<String> feedBack) {
        this.feedBack = feedBack;
    }

}
