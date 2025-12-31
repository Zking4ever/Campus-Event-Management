package com.campus.management.controller;

import com.campus.management.model.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class EventCardStudentController {

    @FXML private ImageView eventImage;
    @FXML private Label title;
    @FXML private Label dateLabel;
    @FXML private Label timeLabel;
    @FXML private Label locationLabel;
    @FXML private Label attendeesLabel;
    @FXML private Label descriptionLabel;
    @FXML private VBox container;

    public void initialize(){
        Rectangle clip = new Rectangle(eventImage.getFitWidth(),eventImage.getFitHeight());
        clip.setArcHeight(32);
        clip.setArcWidth(32);
        clip.widthProperty().bind(container.widthProperty());
        clip.heightProperty().bind(container.heightProperty());
        container.setClip(clip);
    }
    // This method receives parameters
    public void setEventData(Event event) {
        eventImage.setImage(new Image(event.getImageUrl()));
        title.setText(event.getTitle());
//        dateLabel.setText(event.getDateCreated().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        timeLabel.setText(event.getStart()+ " - " + event.getEnd());
        locationLabel.setText("Location: " + event.getLocation());
        attendeesLabel.setText("Attending: " + event.getOrganizerId() + " students");
        descriptionLabel.setText(event.getDescription());
    }





    @FXML
    private void onViewDetails() {
        System.out.println("Details clicked");
    }
}

