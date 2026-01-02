package com.campus.management.controller;

import com.campus.management.AppContext;
import com.campus.management.model.Event;
import com.campus.management.service.AuthService;
import com.campus.management.service.database.UserDao;
import com.campus.management.service.impl.AuthServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import com.campus.management.model.EventRegistration;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.List;

public class EventCardStudentController {

    @FXML
    private ImageView eventImage;
    @FXML
    private Label title;
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
    @FXML
    private VBox container;
    @FXML
    private Button eventButton;
    private Event event;

    public void initialize() {
        Rectangle clip = new Rectangle(eventImage.getFitWidth(), eventImage.getFitHeight());
        clip.setArcHeight(32);
        clip.setArcWidth(32);
        clip.widthProperty().bind(container.widthProperty());
        clip.heightProperty().bind(container.heightProperty());
        container.setClip(clip);
    }

    // This method receives parameters
    public void setEventData(Event event, String status) {
        this.event = event;
        eventImage.setImage(new Image(event.getImageUrl()));
        title.setText(event.getTitle());
        if (event.getDate() != null) {
            dateLabel.setText(event.getDate().toString());
        }
        timeLabel.setText(event.getStart() + " - " + event.getEnd());
        locationLabel.setText("Location: " + event.getLocation());

        // Count registrations
        List<EventRegistration> registrations = UserDao.readRegistrations();
        if (registrations != null) {
            try {
                int eventId = Integer.parseInt(event.getId()); // Corrected myevent to event
                long count = registrations.stream().filter(r -> r.getEvent_id() == eventId).count();
                attendeesLabel.setText("Attending: " + count + " students"); // Re-added "Attending: " and " students"
            } catch (NumberFormatException e) {
                attendeesLabel.setText("Attending: 0 students"); // Handle NFE case
            }
        } else {
            attendeesLabel.setText("Attending: 0 students"); // Handle null registrations case
        }

        descriptionLabel.setText(event.getDescription());
        if (status.equals("registered")) {
            eventButton.setVisible(false);
            eventButton.setManaged(false);
        }
    }

    @FXML
    private void registerToEvent() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Registration");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to register this event?");
        if (alert.showAndWait().orElse(null) == ButtonType.OK) {
            UserDao.EvetRegister(AppContext.getCurrentUser().getUserid(), Integer.parseInt(event.getId()));
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/student.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) eventImage.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
