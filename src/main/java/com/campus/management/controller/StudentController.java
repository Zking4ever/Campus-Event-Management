package com.campus.management.controller;

import com.campus.management.AppContext;
import com.campus.management.model.Event;
import com.campus.management.model.User;
import com.campus.management.service.EventService;
import com.campus.management.service.impl.EventServiceImpl;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class StudentController {

    @FXML private Label usernameLabel;
    @FXML private Label statusLabel;
    @FXML private ListView<Event> eventsListView;

    private final EventService eventService = new EventServiceImpl();

    @FXML
    public void initialize() {
        User current = AppContext.getCurrentUser();
        if (current != null) {
            usernameLabel.setText(current.getUsername());
            statusLabel.setText("Status: Active"); // you can customize this
        }

        loadApprovedEvents();
        setupEventListView();
    }

    private void setupEventListView() {
        eventsListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Event event, boolean empty) {
                super.updateItem(event, empty);
                if (empty || event == null) {
                    setGraphic(null);
                } else {
                    HBox container = new HBox(15);
                    container.setPadding(new Insets(10));
                    container.setStyle("-fx-background-color: white; -fx-background-radius: 8; " +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 4,0,0,2);");

                    // Event image
                    ImageView img = new ImageView();
                    try {
                        img.setImage(new Image(event.getImageUrl(), 80, 80, true, true));
                    } catch (Exception e) {
                        // default image if not found
                        img.setImage(new Image("/images/default_event.png", 80, 80, true, true));
                    }

                    // Event info
                    VBox info = new VBox(5);
                    Label title = new Label(event.getTitle());
                    title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
                    Label date = new Label("Date: " + event.getStart());
                    date.setStyle("-fx-text-fill: #6b7280;");
                    info.getChildren().addAll(title, date);

                    // Register button
                    Button regBtn = new Button("Register");
                    regBtn.setStyle("-fx-background-color: #16a34a; -fx-text-fill: white; -fx-background-radius: 6;");
                    regBtn.setOnAction(e -> registerEvent(event));

                    container.getChildren().addAll(img, info, regBtn);
                    setGraphic(container);
                }
            }
        });
    }

    @FXML
    protected void loadApprovedEvents() {
        List<Event> approved = eventService.listEvents().stream()
                .filter(e -> e.getStatus().name().equals("APPROVED"))
                .toList();
        eventsListView.setItems(FXCollections.observableArrayList(approved));
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
