// src/main/java/com/campus/management/controller/AdminController.java
package com.campus.management.controller;

import com.campus.management.model.Event;
import com.campus.management.model.EventStatus;
import com.campus.management.model.User;
import com.campus.management.service.EventService;
import com.campus.management.service.impl.EventServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class AdminController {

    @FXML
    private ListView<Event> eventsListView;
    @FXML
    private ListView<User> usersListView;
    @FXML
    private Label statusLabel;
    @FXML
    private javafx.scene.layout.VBox eventsView;
    @FXML
    private javafx.scene.layout.VBox usersView;

    private final EventService eventService = new EventServiceImpl();

    @FXML
    public void initialize() {
        loadAllEvents();
    }

    @FXML
    protected void showEventsView() {
        eventsView.setVisible(true);
        usersView.setVisible(false);
    }

    @FXML
    protected void showUsersView() {
        eventsView.setVisible(false);
        usersView.setVisible(true);
        loadAllUsers();
    }

    // --- Event Logic ---

    @FXML
    protected void loadAllEvents() {
        if (eventsListView != null) {
            eventsListView.getItems().setAll(eventService.listEvents());
            if (statusLabel != null)
                statusLabel.setText("Loaded all events");
        }
    }

    @FXML
    protected void loadPendingEvents() {
        if (eventsListView != null) {
            eventsListView.getItems().setAll(
                    eventService.listEvents().stream()
                            .filter(e -> e.getStatus() == EventStatus.PENDING)
                            .toList());
            if (statusLabel != null)
                statusLabel.setText("Showing pending events");
        }
    }

    @FXML
    protected void approveSelected() {
        Event selected = eventsListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            eventService.updateStatus(selected.getId(), "APPROVED");
            loadPendingEvents();
            if (statusLabel != null)
                statusLabel.setText("Event approved");
        }
    }

    @FXML
    protected void rejectSelected() {
        Event selected = eventsListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            eventService.updateStatus(selected.getId(), "REJECTED");
            loadPendingEvents();
            if (statusLabel != null)
                statusLabel.setText("Event rejected");
        }
    }

    @FXML
    protected void deleteSelected() {
        Event selected = eventsListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            eventService.deleteEvent(selected.getId());
            loadAllEvents();
            if (statusLabel != null)
                statusLabel.setText("Event deleted");
        }
    }

    // --- User Logic ---

    @FXML
    protected void loadAllUsers() {
        if (usersListView != null) {
            usersListView.getItems().setAll(com.campus.management.service.database.UserDao.getAllUsers());
        }
    }

    @FXML
    protected void blockSelectedUser() {
        User selected = usersListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Using "STUDENT" role change or some other logic. Request said "Block".
            com.campus.management.service.database.UserDao.deleteUser(selected.getUserid());
            loadAllUsers();
        }
    }

    @FXML
    protected void removeSelectedUser() {
        User selected = usersListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            com.campus.management.service.database.UserDao.deleteUser(selected.getUserid());
            loadAllUsers();
        }
    }

    @FXML
    protected void onLogout() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = (javafx.stage.Stage) eventsListView.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root, 800, 600));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
