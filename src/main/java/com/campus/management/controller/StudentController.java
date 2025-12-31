package com.campus.management.controller;

import com.campus.management.AppContext;
import com.campus.management.model.Event;
import com.campus.management.model.User;
import com.campus.management.service.EventService;
import com.campus.management.service.impl.EventServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.util.List;

public class StudentController {

    @FXML private Label usernameLabel;
    @FXML private Label statusLabel;
    @FXML private List<Event> eventList;
    @FXML private FlowPane eventContainer;
    @FXML private ImageView profileImage;

    private final EventService eventService = new EventServiceImpl();
    List<Event> registeredEvents;

    @FXML
    public void initialize() {
        User current = AppContext.getCurrentUser();
        if (current != null) {
            usernameLabel.setText(current.getUsername());
        }
        profileImage.setImage(new Image(getClass().getResource("/images/person.png").toExternalForm()));
        loadApprovedEvents();
        renderEvents();
    }


    private void renderEvents() {
        try {
            for (Event event : eventList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/eventCardStudent.fxml"));
                Parent card = loader.load();
                EventCardStudentController controller = loader.getController();
                controller.setEventData(event);
                eventContainer.getChildren().add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void loadApprovedEvents() {
//       List<Event> approved = eventService.listEvents().stream()
//                .filter(e -> e.getStatus().name().equals("APPROVED"))
//                .toList();

        List<Event> eventList = eventService.listEvents();
    }

    private void registerEvent(Event event) {
        if (event != null) {
            // TODO: call RegistrationService.register(...) to register student for event
            System.out.println("Registered for event: " + event.getTitle());
        }
    }

    @FXML
    protected void onLogout() {
        AppContext.setCurrentUser(null);
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            javafx.scene.Parent root = loader.load();
            Stage stage = (Stage) usernameLabel.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root, 800, 600));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
