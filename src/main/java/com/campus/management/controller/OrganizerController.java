package com.campus.management.controller;

import com.campus.management.AppContext;
import com.campus.management.model.Event;
import com.campus.management.model.EventStatus;
import com.campus.management.model.User;
import com.campus.management.service.AuthService;
import com.campus.management.service.EventService;
import com.campus.management.service.impl.EventServiceImpl;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrganizerController {

    @FXML private ListView<Event> eventsListView;
    @FXML private Label statusLabel;

    private final EventService eventService = new EventServiceImpl();
    private String organizerId = "SENDERSID";

    @FXML
    public void initialize() {
        User current = AppContext.getCurrentUser();
        if (current != null) {
            organizerId = current.getUserid();
            loadMyEvents();
        }
        eventsListView.setCellFactory(param -> new ListCell<>() {
            private final ImageView imageView = new ImageView();
            @Override
            protected void updateItem(Event e, boolean empty) {
                super.updateItem(e, empty);
                if (empty || e == null) {
                    setText(null);
                    setGraphic(null);
                } else {
//                    imageView.setImage(new Image(e.getImageUrl(), 60, 60, true, true));
                    setText(e.getTitle() + " (" + e.getStatus() + ") \n" +
                            "Start: " + e.getStart() + " | End: " + e.getEnd());
                    setGraphic(imageView);
                }
            }
        });
    }

    @FXML
    protected void onCreateEvent() {


//        eventService.createEvent(e);
        statusLabel.setText("Event created successfully!");
        loadMyEvents();
    }

    @FXML
    protected void loadMyEvents() {
        List<Event> myEvents = eventService.listEventsByOrganizer(organizerId);
        eventsListView.setItems(FXCollections.observableArrayList(myEvents));
    }

    @FXML
    protected void onEditEvent() {
        Event selected = eventsListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            statusLabel.setText("Select an event to edit");
            return;
        }
        eventService.updateEvent(selected);
        loadMyEvents();
        statusLabel.setText("Event updated!");
    }

    @FXML
    protected void onViewFeedback() {
        Event selected = eventsListView.getSelectionModel().getSelectedItem();
        if (selected == null || selected.getFeedBack().isEmpty()) {
            statusLabel.setText("No feedback available");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Feedback");
        alert.setHeaderText(selected.getTitle() + " Feedback");
        alert.setContentText(String.join("\n", selected.getFeedBack()));
        alert.showAndWait();
    }
}
