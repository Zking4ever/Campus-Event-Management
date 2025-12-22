package com.campus.management.service;

import com.campus.management.model.Event;

import java.util.List;

public interface EventService {
    Event createEvent(Event event);
    List<Event> listEvents();
    Event findById(String id);
    Event updateStatus(String id, String status);
    List<Event> listEventsByOrganizer(String organizerId);
    Event updateEvent(Event updated); // status string mapped to enum
}
