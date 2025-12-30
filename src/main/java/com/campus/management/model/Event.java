package com.campus.management.model;

import javafx.scene.image.Image;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event {
    private String id;
    private String title;
    private String description;
    private LocalDateTime date;
    private String start;
    private String end;
    private String location;
    private String imageURL;
    private EventStatus status;
    private String category;
    private String organizerId;

    private ArrayList<String> feedBack;

    public Event(String id, String title, String description, String organizerId,LocalDateTime date,
                 String start, String end, EventStatus status, String category, String location, String imageURL) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.organizerId = organizerId;
        this.date = date;
        this.start = start;
        this.end = end;
        this.status = status;
        this.category = category;
        this.location = location;
        this.imageURL = imageURL;
    }

    // getters / setters
    public String getId() { return id; }

    public String getTitle() { return title; }
    public  void  setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public  void  setDescription(String description) { this.description = description; }

    public String getStart() { return start; }
    public  void  setStart(String start) { this.start = start; }

    public String getEnd() { return end; }
    public  void  setEnd(String end) { this.end = end; }


    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getImageUrl() { return imageURL; }
    public void setImageUrl(String imageURL) { this.imageURL = imageURL; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public EventStatus getStatus() { return status; }
    public void setStatus(EventStatus status) { this.status = status; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getOrganizerId() { return organizerId; }

    public ArrayList<String> getFeedBack() { return feedBack; }
    public  void setFeedback(ArrayList<String> feedBack) {
        this.feedBack = feedBack;
    }
}
