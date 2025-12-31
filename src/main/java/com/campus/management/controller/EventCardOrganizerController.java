package com.campus.management.controller;

import com.campus.management.model.Event;
import com.campus.management.service.EventService;
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
import javafx.stage.Window;

import static com.campus.management.controller.OrganizerController.eventList;

public class EventCardOrganizerController {

    @FXML private VBox eventCardContainer;
    @FXML private ImageView eventImage;
    @FXML private Label title;
    @FXML private Label catagoryLabel;
    @FXML private Label dateLabel;
    @FXML private Label timeLabel;
    @FXML private Label locationLabel;
    @FXML private Label attendeesLabel;
    @FXML private Label descriptionLabel;
    Event myevent;

    // This method receives parameters
    public void setEventData(Event event) {
        myevent = event;
        eventImage.setImage(new Image(event.getImageUrl()));
        title.setText(event.getTitle());
        catagoryLabel.setText( event.getCategory());
        dateLabel.setText(event.getDate().toString());
        timeLabel.setText(event.getStart()+ " - " + event.getEnd());
        locationLabel.setText(event.getLocation());
        attendeesLabel.setText("30 / 75");
        descriptionLabel.setText(event.getDescription());
    }

    @FXML
    private void handleEditEvent() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/eventActions.fxml"));
            Parent root = loader.load();
            EventActionController controller = loader.getController();
            controller.setDataFields(myevent);
            controller.actionType.setText("Edit Event");
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Create Event");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(title.getScene().getWindow());
            stage.showAndWait();
            refreshPage();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private void renderEventList() {
        try {
            for (Event event : eventList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/eventCardOrganizer.fxml"));
                Parent root = loader.load();
                EventCardOrganizerController controller = loader.getController();
                controller.setEventData(event);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void handleDeletEvent(){
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Confirm Deletion");
        a.setHeaderText("Delete Event");
        a.setContentText("Are you sure you want to delete this event?");
        if(a.showAndWait().get()== ButtonType.OK){
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
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

