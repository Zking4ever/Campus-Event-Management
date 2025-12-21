// src/main/java/com/campus/management/service/impl/EventServiceImpl.java
package com.campus.management.service.impl;

import com.campus.management.model.Event;
import com.campus.management.model.EventStatus;
import com.campus.management.service.EventService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EventServiceImpl implements EventService {
    private final List<Event> events = new ArrayList<>();

    @Override
    public Event createEvent(Event event) {
        Event e = new Event(UUID.randomUUID().toString(), event.getTitle(), event.getDescription(),
                event.getOrganizerId(), event.getStart(), event.getEnd(), EventStatus.PENDING);
        events.add(e);
        return e;
    }

    @Override
    public List<Event> listEvents() {
        return events.stream().collect(Collectors.toList());
    }

    @Override
    public Event findById(String id) {
        return events.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Event updateStatus(String id, String status) {
        Event e = findById(id);
        if (e != null) {
            e.setStatus(EventStatus.valueOf(status));
        }
        return e;
    }
}
