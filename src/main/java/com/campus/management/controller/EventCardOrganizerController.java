package com.campus.management.controller;

import com.campus.management.model.Event;
import com.campus.management.model.EventRegistration;
import com.campus.management.service.EventService;
import com.campus.management.service.database.UserDao;
import com.campus.management.service.impl.EventServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class EventCardOrganizerController {

    @FXML
    private VBox eventCardContainer;
    @FXML
    private ImageView eventImage;
    @FXML
    private Label title;
    @FXML
    private Label catagoryLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Label attendeesLabel;
    @FXML
    private Label descriptionLabel;
    Event myevent;
    String registeredNo;

    // This method receives parameters
    public void setEventData(Event event) {
        myevent = event;
        eventImage.setImage(new Image(event.getImageUrl()));
        title.setText(event.getTitle());
        if (myevent.getDate() != null) {
            dateLabel.setText(myevent.getDate().toString());
        }
        timeLabel.setText(myevent.getStart() + " - " + myevent.getEnd());
        locationLabel.setText(myevent.getLocation());

        // Category Logic
        String cat = myevent.getCategory();
        String emoji = "📅";
        String styleClass = "category-default";

        if (cat != null) {
            String lowerCat = cat.toLowerCase();
            if (lowerCat.contains("academic")) {
                emoji = "🎓";
                styleClass = "category-academic";
            } else if (lowerCat.contains("art")) {
                emoji = "🎨";
                styleClass = "category-art";
            } else if (lowerCat.contains("sport")) {
                emoji = "⚽";
                styleClass = "category-sport";
            } else if (lowerCat.contains("tech")) {
                emoji = "💻";
                styleClass = "category-tech";
            } else if (lowerCat.contains("entertainment")) {
                emoji = "🎬";
                styleClass = "category-entertainment";
            }
            catagoryLabel.setText(emoji + " " + cat);
            catagoryLabel.getStyleClass().setAll("category-badge", styleClass);
        } else {
            catagoryLabel.setVisible(false);
        }

        // Count registrations
        List<EventRegistration> eventRegistered = UserDao.readRegistrations();
        if (eventRegistered != null) {
            try {
                int eventId = Integer.parseInt(myevent.getId());
                eventRegistered.removeIf(e -> e.getEvent_id() != eventId);
                registeredNo = String.valueOf(eventRegistered.size());
            } catch (NumberFormatException e) {
                registeredNo = "0";
                System.err.println("Invalid Event ID: " + myevent.getId());
            }
        } else {
            registeredNo = "0";
        }
        attendeesLabel.setText(registeredNo);
    }

    @FXML
    private void handleEditEvent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/eventActions.fxml"));
            Parent root = loader.load();
            EventActionController controller = loader.getController();
            controller.setDataFields(myevent);
            controller.actionType.setText("Edit Event");
            controller.actionButton.setText("Edit");
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Create Event");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(title.getScene().getWindow());
            stage.showAndWait();
            refreshPage();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void handleDeletEvent() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Confirm Deletion");
        a.setHeaderText("Delete Event");
        a.setContentText("Are you sure you want to delete this event?");
        if (a.showAndWait().get() == ButtonType.OK) {
            EventService eventService = new EventServiceImpl();
            eventService.deleteEvent(myevent.getId());
            System.out.println("Event deleted successfully");
            refreshPage();
        }
    }

    private void refreshPage() {
        try {
            Stage stage = (Stage) title.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/organizer.fxml"));
            Parent root = loader.load();
            stage.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewAttendees() {
        try {
            List<com.campus.management.model.User> attendees = UserDao
                    .getAttendeesForEvent(Integer.parseInt(myevent.getId()));
            StringBuilder content = new StringBuilder();
            if (attendees.isEmpty()) {
                content.append("No attendees registered yet.");
            } else {
                for (com.campus.management.model.User u : attendees) {
                    content.append(u.getName())
                            .append(" (").append(u.getEmail()).append(")\n");
                }
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Attendee List");
            alert.setHeaderText("Registered Students for " + myevent.getTitle());
            alert.setContentText(content.toString());
            alert.getDialogPane().setMinHeight(javafx.scene.layout.Region.USE_PREF_SIZE);
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
