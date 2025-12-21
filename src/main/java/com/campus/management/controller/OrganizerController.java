// src/main/java/com/campus/management/controller/OrganizerController.java
package com.campus.management.controller;

import com.campus.management.model.Event;
import com.campus.management.service.EventService;
import com.campus.management.service.impl.EventServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;

public class OrganizerController {
    @FXML private TextField titleField;
    @FXML private TextArea descArea;
    // add date/time pickers for start/end

    private final EventService eventService = new EventServiceImpl();
    private String organizerId; // set after login

    @FXML
    protected void onCreateEvent() {
        Event e = new Event(null, titleField.getText(), descArea.getText(),
                organizerId, LocalDateTime.now(), LocalDateTime.now().plusHours(2), null);
        Event created = eventService.createEvent(e);
        // show confirmation / clear fields
    }
}
