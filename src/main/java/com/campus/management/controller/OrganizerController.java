package com.campus.management.controller;

import com.campus.management.AppContext;
import com.campus.management.model.Event;
import com.campus.management.model.EventStatus;
import com.campus.management.model.User;
import com.campus.management.service.EventService;
import com.campus.management.service.impl.EventServiceImpl;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrganizerController {
    @FXML private TextField titleField;
    @FXML private TextArea descArea;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private ListView<Event> eventsListView;
    @FXML private Label statusLabel;

    private final EventService eventService = new EventServiceImpl();
    private String organizerId;

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
                    imageView.setImage(new Image(e.getImageUrl(), 60, 60, true, true));
                    setText(e.getTitle() + " (" + e.getStatus() + ") \n" +
                            "Start: " + e.getStart() + " | End: " + e.getEnd());
                    setGraphic(imageView);
                }
            }
        });
    }

    @FXML
    protected void onCreateEvent() {
        if (titleField.getText().isBlank() || descArea.getText().isBlank()) {
            statusLabel.setText("Title and description required");
            return;
        }

        Event e = new Event(null,
                titleField.getText(),
                descArea.getText(),
                organizerId,
                startDatePicker.getValue().atStartOfDay(),
                endDatePicker.getValue().atStartOfDay(),
                EventStatus.PENDING);
        e.setFeedback(new ArrayList<>());
        eventService.createEvent(e);
        statusLabel.setText("Event created successfully!");
        titleField.clear();
        descArea.clear();
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
        selected.setTitle(titleField.getText());
        selected.setDescription(descArea.getText());
        selected.setStart(startDatePicker.getValue().atStartOfDay());
        selected.setEnd(endDatePicker.getValue().atStartOfDay());
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
