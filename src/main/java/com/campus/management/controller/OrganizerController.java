package com.campus.management.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TabPane;

public class OrganizerController {

    // Optional: inject if you want to control them dynamically later
    @FXML
    private TabPane tabPane;

    @FXML
    private ProgressBar progressBar;

    /**
     * Called automatically after FXML is loaded
     */
    @FXML
    public void initialize() {
        // Initial setup
        if (progressBar != null) {
            progressBar.setProgress(0.73); // 73% registration
        }
    }

    /**
     * Handle "Create New Event" button
     */
    @FXML
    private void handleCreateEvent() {
        showInfo("Create Event", "Create New Event clicked");
    }

    /**
     * Handle View Event button
     */
    @FXML
    private void handleViewEvent() {
        showInfo("View Event", "Viewing event details");
    }

    /**
     * Handle Edit Event button
     */
    @FXML
    private void handleEditEvent() {
        showInfo("Edit Event", "Editing event");
    }

    /**
     * Handle Switch Role button
     */
    @FXML
    private void handleSwitchRole() {
        showInfo("Switch Role", "Role switching not implemented yet");
    }

    /**
     * Utility method for alerts
     */
    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

//protected void onCreateEvent() {
//
//
////        eventService.createEvent(e);
//    statusLabel.setText("Event created successfully!");
//    loadMyEvents();
//}
//
//@FXML
//protected void loadMyEvents() {
//    List<Event> myEvents = eventService.listEventsByOrganizer(organizerId);
//    eventsListView.setItems(FXCollections.observableArrayList(myEvents));
//}
//
//@FXML
//protected void onEditEvent() {
//    Event selected = eventsListView.getSelectionModel().getSelectedItem();
//    if (selected == null) {
//        statusLabel.setText("Select an event to edit");
//        return;
//    }
//    eventService.updateEvent(selected);
//    loadMyEvents();
//    statusLabel.setText("Event updated!");
//}
//
//@FXML
//protected void onViewFeedback() {
//    Event selected = eventsListView.getSelectionModel().getSelectedItem();
//    if (selected == null || selected.getFeedBack().isEmpty()) {
//        statusLabel.setText("No feedback available");
//        return;
//    }
//    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//    alert.setTitle("Feedback");
//    alert.setHeaderText(selected.getTitle() + " Feedback");
//    alert.setContentText(String.join("\n", selected.getFeedBack()));
//    alert.showAndWait();
//}
