package com.campus.management.model;

public class EventRegistration {
    protected String user_id;
    protected int event_id;

    public EventRegistration(String user_id, int event_id) {
        this.user_id = user_id;
        this.event_id = event_id;
    }

    public String getUser_id() { return user_id; }
    public int getEvent_id() { return event_id; }
    public void setUser_id(String user_id) { this.user_id=user_id; }
    public void setEvent_id(int event_id) { this.event_id=event_id; }
}
