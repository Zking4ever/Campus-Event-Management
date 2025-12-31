package com.campus.management.controller;

import com.campus.management.AppContext;
import com.campus.management.model.Event;
import com.campus.management.model.User;
import com.campus.management.service.EventService;
import com.campus.management.service.impl.EventServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class OrganizerController {

    // Optional: inject if you want to control them dynamically later
    @FXML
    private TabPane tabPane;
    @FXML
    private ProgressBar progressBar;
    @FXML
    public VBox EventContainer;
    @FXML
    HBox rootContainer;
    @FXML
    VBox MainContentContainer;


    private final EventService eventService = new EventServiceImpl();
    ;
    static  List<Event> eventList;
    Event selectedEvent;
    String orgainizer_id;
    @FXML
    ImageView profileImage;
    @FXML
    Label orgainizer_name;
    @FXML
    Label orgainizer_username;

    @FXML
    public void initialize() {
        User current = AppContext.getCurrentUser();
        if (current != null) {
            orgainizer_id = current.getUserid();
            orgainizer_name.setText(current.getName());
            orgainizer_username.setText("@" + current.getUsername());
        }
        profileImage.setImage(new Image(getClass().getResource("/images/person.png").toExternalForm()));
        loadEventLists();
        renderEventList();
        MainContentContainer.prefWidthProperty().bind(rootContainer.widthProperty().multiply(0.8));
        if (progressBar != null) {
            progressBar.setProgress(0.73); // 73% registration
        }
    }

    protected void loadEventLists() {
        eventList = eventService.listEvents();
    }

    private void renderEventList() {
        EventContainer.getChildren().clear();
        try {
            for (Event event : eventList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/eventCardOrganizer.fxml"));
                Parent root = loader.load();
                EventCardOrganizerController controller = loader.getController();
                controller.setEventData(event);
                EventContainer.getChildren().add(root);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void handleCreateEvent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/eventActions.fxml"));
            Parent root = loader.load();
            EventActionController controller = loader.getController();
            controller.setToDefault();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Create Event");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(EventContainer.getScene().getWindow());
            stage.showAndWait();
            loadEventLists();
            renderEventList();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    @FXML
    protected void onLogout() {
        AppContext.setCurrentUser(null);
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            javafx.scene.Parent root = loader.load();
            Stage stage = (Stage) rootContainer.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root, 800, 600));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
