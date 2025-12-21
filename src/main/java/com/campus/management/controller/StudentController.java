// src/main/java/com/campus/management/controller/StudentController.java
package com.campus.management.controller;

import com.campus.management.model.Event;
import com.campus.management.service.EventService;
import com.campus.management.service.impl.EventServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;

public class StudentController {
    @FXML private ListView<Event> eventsListView;
    private final EventService eventService = new EventServiceImpl();

    @FXML
    protected void loadApprovedEvents() {
        List<Event> approved = eventService.listEvents().stream()
                .filter(e -> e.getStatus().name().equals("APPROVED")).toList();
        eventsListView.getItems().setAll(approved);
    }

    @FXML
    protected void onRegister() {
        Event sel = eventsListView.getSelectionModel().getSelectedItem();
        if (sel != null) {
            // call RegistrationService.register(...)
        }
    }
}
