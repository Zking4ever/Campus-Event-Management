// src/main/java/com/campus/management/controller/AdminController.java
package com.campus.management.controller;

import com.campus.management.model.Event;
import com.campus.management.model.EventStatus;
import com.campus.management.service.EventService;
import com.campus.management.service.impl.EventServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class AdminController {

    @FXML private ListView<Event> eventsListView;
    @FXML private Label statusLabel;

    private final EventService eventService = new EventServiceImpl();

    @FXML
    public void initialize() {
        loadAllEvents();
    }

    @FXML
    protected void loadAllEvents() {
        eventsListView.getItems().setAll(eventService.listEvents());
        statusLabel.setText("Loaded all events");
    }

    @FXML
    protected void loadPendingEvents() {
        eventsListView.getItems().setAll(
                eventService.listEvents().stream()
                        .filter(e -> e.getStatus() == EventStatus.PENDING)
                        .toList()
        );
        statusLabel.setText("Showing pending events");
    }

    @FXML
    protected void approveSelected() {
        Event selected = eventsListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            eventService.updateStatus(selected.getId(), "APPROVED");
            loadPendingEvents();
            statusLabel.setText("Event approved");
        }
    }

    @FXML
    protected void rejectSelected() {
        Event selected = eventsListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            eventService.updateStatus(selected.getId(), "REJECTED");
            loadPendingEvents();
            statusLabel.setText("Event rejected");
        }
    }

    @FXML
    protected void deleteSelected() {
        Event selected = eventsListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            eventService.deleteEvent(selected.getId());
            loadAllEvents();
            statusLabel.setText("Event deleted");
        }
    }
}
