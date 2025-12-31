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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.function.UnaryOperator;

public class EventActionController {
    @FXML private Label actionType;
    @FXML private Label statusLabel;
    @FXML private ImageView imagePreview;
    @FXML private TextField titleField;
    @FXML private TextArea descArea;
    @FXML private DatePicker dateField;
    @FXML private TextField startTimePicker;
    @FXML private TextField endTimePicker;
    @FXML private TextField locationField;
    @FXML private ComboBox<String> catagorySelect;
    private String selectedImagePath = null;
    private String organizerId = null;
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
        startTimePicker.setTextFormatter(createTimeFormatter());
        endTimePicker.setTextFormatter(createTimeFormatter());
        dateField.getEditor().setTextFormatter(dateFormater());
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
        if(titleField.getText().isBlank() || descArea.getText().isBlank() || dateField.getEditor().getText().isBlank() || startTimePicker.getText().isEmpty() || endTimePicker.getText().isEmpty() || locationField.getText().isBlank()) {
            statusLabel.getStyleClass().add("error");
            statusLabel.setText("All fields are required");
            return;
        }
        if(organizerId == null) {
            statusLabel.getStyleClass().add("error");
            statusLabel.setText("You have login first to create an event");
            return;
        }
        try{
            LocalDate date = LocalDate.parse(dateField.getEditor().getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }catch (Exception e){
            statusLabel.getStyleClass().add("error");
            statusLabel.setText("Invalid date format");
            return;
        }
        try{
            LocalTime startTime = LocalTime.parse(startTimePicker.getText(), DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime endTime = LocalTime.parse(endTimePicker.getText(), DateTimeFormatter.ofPattern("HH:mm"));
        }catch (Exception e){
            statusLabel.getStyleClass().add("error");
            statusLabel.setText("Invalid time format");
            return;
        }
        if(imagePreview.getImage() == null) {
            statusLabel.getStyleClass().add("error");
            statusLabel.setText("Image not found");
        }

        Event e = new Event(null,
                titleField.getText(),
                descArea.getText(),
                organizerId,
                LocalDate.parse(dateField.getEditor().getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                startTimePicker.getText(),
                endTimePicker.getText(),
                EventStatus.PENDING,
                catagorySelect.getSelectionModel().getSelectedItem(),
                locationField.getText(),
                selectedImagePath
        );

        Event event = eventService.createEvent(e);
        if(event==null){
            statusLabel.getStyleClass().add("error");
            statusLabel.setText("Event not created");
            return;
        }
        statusLabel.getStyleClass().add("success");
        statusLabel.setText("Event created successfully!");
        setToDefault();
    }

    private  void setDataFields(Event event) {
        titleField.setText(event.getTitle());
        descArea.setText(event.getDescription());
        locationField.setText(event.getLocation());
        startTimePicker.setText(event.getStart());
        endTimePicker.setText(event.getEnd());
        dateField.setValue(event.getDate());
        imagePreview.setImage(new Image(event.getImageUrl()));
        selectedImagePath = event.getImageUrl();
        catagorySelect.getSelectionModel().select(event.getCategory());
    }
    private void setToDefault(){
        titleField.clear();
        descArea.clear();
        imagePreview.setImage(new Image(getClass().getResource("/images/placeholder.png").toExternalForm()));
        locationField.clear();
        startTimePicker.clear();
        endTimePicker.clear();
        dateField.setValue(LocalDate.now());
    }
    //    Allow only numbers for time
    private TextFormatter<String> createTimeFormatter() {
        return new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("^([01]?\\d?|2[0-3]?)?(:[0-5]?\\d?)?$")) {
                return change;
            }
            return null;
        });
    }

    public static TextFormatter<String> dateFormater() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (change.isDeleted()) {
                return change;
            }
            if (!change.getText().matches("\\d*")) {
                return null;
            }
            // Remove slashes for length check
            String digitsOnly = newText.replace("/", "");
            if (digitsOnly.length() > 8) {
                return null;
            }
            // Auto insert '/'
            if (change.getText().length() == 1) {
                int pos = change.getCaretPosition();

                if (pos == 3 || pos == 6) {
                    change.setText("/" + change.getText());
                    change.setCaretPosition(pos + 1);
                    change.setAnchor(pos + 1);
                }
            }
            return change;
        };

        return new TextFormatter<>(filter);
    }

}
