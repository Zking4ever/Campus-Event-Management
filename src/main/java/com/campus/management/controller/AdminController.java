// src/main/java/com/campus/management/controller/AdminController.java
package com.campus.management.controller;

import com.campus.management.model.Event;
import com.campus.management.service.EventService;
import com.campus.management.service.impl.EventServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class AdminController {
    @FXML private ListView<Event> eventsListView;
    private final EventService eventService = new EventServiceImpl();

    @FXML
    protected void loadPending() {
        eventsListView.getItems().setAll(eventService.listEvents().stream()
                .filter(e -> e.getStatus().name().equals("PENDING")).toList());
    }

    @FXML
    protected void approveSelected() {
        Event sel = eventsListView.getSelectionModel().getSelectedItem();
        if (sel != null) {
            eventService.updateStatus(sel.getId(), "APPROVED");
            loadPending();
        }
    }
}
