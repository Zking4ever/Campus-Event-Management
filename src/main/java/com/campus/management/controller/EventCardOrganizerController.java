package com.campus.management.controller;

import com.campus.management.model.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EventCardOrganizerController {

    @FXML private ImageView eventImage;
    @FXML private Label title;
    @FXML private Label dateLabel;
    @FXML private Label timeLabel;
    @FXML private Label locationLabel;
    @FXML private Label attendeesLabel;
    @FXML private Label descriptionLabel;

    // This method receives parameters
    public void setEventData(Event event) {
        System.out.println(event.getTitle());
//        eventImage.setImage(new Image(getClass().getResource("/images/img.png").toExternalForm()));
        title.setText(event.getTitle());
        dateLabel.setText(event.getDate().toString());
        timeLabel.setText(event.getStart()+ " - " + event.getEnd());
        locationLabel.setText(event.getLocation());
        attendeesLabel.setText("30 / 75");
//        descriptionLabel.setText(event.getDescription());
    }

    @FXML
    private void onViewDetails() {
        System.out.println("Details clicked");
    }
}

