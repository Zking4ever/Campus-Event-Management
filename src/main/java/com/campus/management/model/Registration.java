// src/main/java/com/campus/management/model/Registration.java
package com.campus.management.model;

public class Registration {
    private String id;
    private String eventId;
    private String studentId;
    private boolean attended;

    public Registration(String id, String eventId, String studentId) {
        this.id = id;
        this.eventId = eventId;
        this.studentId = studentId;
        this.attended = false;
    }

    // getters/setters
    public String getId() { return id; }
    public String getEventId() { return eventId; }
    public String getStudentId() { return studentId; }
    public boolean isAttended() { return attended; }
    public void setAttended(boolean attended) { this.attended = attended; }
}
