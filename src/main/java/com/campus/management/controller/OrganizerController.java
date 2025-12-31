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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TabPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class OrganizerController {

    // Optional: inject if you want to control them dynamically later
    @FXML private TabPane tabPane;
    @FXML private ProgressBar progressBar;
    @FXML VBox EventContainer;


    private final EventService eventService = new EventServiceImpl();;
    List<Event> eventList;
    Event selectedEvent;
    String orgainizer_id;
    String orgainizer_name;

    @FXML
    public void initialize() {
        User current = AppContext.getCurrentUser();
        if (current != null) {
            orgainizer_id = current.getUserid();
            orgainizer_name = current.getName();
        }
        eventList = eventService.listEvents();
        renderEventList();
        if (progressBar != null) {
            progressBar.setProgress(0.73); // 73% registration
        }
    }

    private void renderEventList() {
       try {
           for (Event event : eventList) {
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/eventCardOrganizer.fxml"));
               Parent root = loader.load();
               EventCardOrganizerController controller = loader.getController();
               controller.setEventData(event);
               EventContainer.getChildren().add(root);
           }
       }catch (Exception e){
           System.out.println(e);
       }
    }

    @FXML
    private void handleCreateEvent() {
       try{
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/eventActions.fxml"));
           Parent root = loader.load();
           EventActionController controller = loader.getController();
//           controller.setDataFields(selectedEvent);
           Scene scene = new Scene(root);
           Stage stage = new Stage();
           stage.setTitle("Create Event");
           stage.setResizable(false);
           stage.setScene(scene);
           stage.show();
       }catch (Exception e){
           System.out.println(e);
       }
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
