package com.campus.management.service.impl;

import com.campus.management.model.Event;
import com.campus.management.model.EventStatus;
import com.campus.management.service.EventService;
import com.campus.management.service.database.EventDao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EventServiceImpl implements EventService {

    private List<Event> events = new ArrayList<>();

    @Override
    public Event createEvent(Event event) {
        return EventDao.addEvent(event);
    }

    @Override
    public List<Event> listEvents() {
        return EventDao.readEvents();
    }

    @Override
    public Event updateEvent(Event updated) {
        return EventDao.updateEvent(updated);
    }

    @Override
    public Event findById(String id) {
        events = listEvents();
        return events.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Event updateStatus(String id, String status) {
        Event e = findById(id);
        if (e != null) {
            e.setStatus(EventStatus.valueOf(status));
            EventDao.updateEvent(e);
        }
        return e;
    }

    @Override
    public List<Event> listEventsByOrganizer(String organizerId) {
        return events.stream()
                .filter(e -> e.getOrganizerId().equals(organizerId))
                .collect(Collectors.toList());
    }

    public void deleteEvent(String id) {
        EventDao.deleteEvent(id);
    }
}
