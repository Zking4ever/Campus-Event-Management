package com.campus.management.controller;

import com.campus.management.AppContext;
import com.campus.management.model.Event;
import com.campus.management.model.EventRegistration;
import com.campus.management.model.User;
import com.campus.management.service.EventService;
import com.campus.management.service.database.UserDao;
import com.campus.management.service.impl.EventServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class StudentController {

    @FXML private Label usernameLabel;
    @FXML private Label statusLabel;
    @FXML private FlowPane eventContainer;
    @FXML private FlowPane registeredEventContainer;
    @FXML private ImageView profileImage;

    private final EventService eventService = new EventServiceImpl();
    @FXML private List<Event> eventList;
    List<Event> registeredEvents = new ArrayList<>();
    List<EventRegistration> registrations;
    User current;

    @FXML
    public void initialize() {
        current = AppContext.getCurrentUser();
        if (current != null) {
            usernameLabel.setText(current.getUsername());
        }
        profileImage.setImage(new Image(getClass().getResource("/images/person.png").toExternalForm()));
        loadApprovedEvents();
        renderEvents();
    }

    private void renderEvents() {
        try {
            eventList.removeAll(registeredEvents);
            for (Event event : eventList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/eventCardStudent.fxml"));
                Parent card = loader.load();
                EventCardStudentController controller = loader.getController();
                controller.setEventData(event);
                eventContainer.getChildren().add(card);
            }
            for (Event event : registeredEvents) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/eventCardStudent.fxml"));
                Parent card = loader.load();
                EventCardStudentController controller = loader.getController();
                controller.setEventData(event);
                registeredEventContainer.getChildren().add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void loadApprovedEvents() {
        eventList = eventService.listEvents();
        registrations = UserDao.readRegistrations();
        for (EventRegistration registration : registrations) {
            if (registration.getUser_id().equals(current.getUserid())) {
                for (Event event : eventList) {
                    if (registration.getEvent_id() == Integer.parseInt(event.getId())){
                        registeredEvents.add(event);
                    }
                }
            }
        }
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
