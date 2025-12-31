package com.campus.management.controller;

import com.campus.management.AppContext;
import com.campus.management.model.Event;
import com.campus.management.model.EventStatus;
import com.campus.management.model.User;
import com.campus.management.service.EventService;
import com.campus.management.service.impl.EventServiceImpl;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.time.LocalDateTime;

public class EventActionController {
    @FXML private Label statusLabel;
    @FXML private ImageView imagePreview;
    @FXML private TextField titleField;
    @FXML private TextArea descArea;
    @FXML private TextField startTimePicker;
    @FXML private TextField endDatePicker;
    @FXML private TextField locationField;
    @FXML private ComboBox<String> catagorySelect;
    private String selectedImagePath = null;
    private String organizerId = "SENDERSID";
    @FXML private HBox buttonsContainer;
    private final EventService eventService = new EventServiceImpl();

    @FXML
    public void initialize() {
        User current = AppContext.getCurrentUser();
        if (current != null) {
            organizerId = current.getUserid();
        }
        imagePreview.setImage(
                new Image(getClass().getResource("/images/placeholder.png").toExternalForm())
        );
        catagorySelect.getItems().addAll(
                "Academy",
                "Sport",
                "Art",
                "Entertainment",
                "Tech"
        );
        catagorySelect.getSelectionModel().select(0);
        buttonsContainer.setAlignment(Pos.BASELINE_RIGHT);
    }

    @FXML
    protected void uploadImage(MouseEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );

        File file = chooser.showOpenDialog(imagePreview.getScene().getWindow());
        if (file != null) {
            imagePreview.setImage(
                    new Image(file.toURI().toString())
            );
            selectedImagePath = file.toURI().toString();
        }
    }
    @FXML
    protected void onCreateEvent() {
        if (titleField.getText().isBlank() || descArea.getText().isBlank()) {
//            statusLabel.
            statusLabel.setText("Title and description required");
            return;
        }
        if(imagePreview.getImage() == null) {
            statusLabel.setText("Image not found");
        }

        Event e = new Event(null,
                titleField.getText(),
                descArea.getText(),
                organizerId,
                LocalDateTime.now(),
                startTimePicker.getText(),
                endDatePicker.getText(),
                EventStatus.PENDING,
                catagorySelect.getSelectionModel().getSelectedItem(),
                locationField.getText(),
                selectedImagePath
        );

        Event event = eventService.createEvent(e);
        if(event==null){

        }
        statusLabel.setText("Event created successfully!");
        titleField.clear();
        descArea.clear();
        imagePreview.setImage(new Image(getClass().getResource("images/placeholder.png").toExternalForm()));
    }

}
