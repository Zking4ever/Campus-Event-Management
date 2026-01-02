// java
package com.campus.management.controller;

import com.campus.management.AppContext;
import com.campus.management.model.Role;
import com.campus.management.model.User;
import com.campus.management.service.AuthService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrationController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ChoiceBox<Role> roleChoice;
    @FXML
    private Label statusLabel;

    private final AuthService authService = AppContext.getAuthService();

    @FXML
    public void initialize() {
        // Exclude ADMIN from public registration
        roleChoice.setItems(FXCollections.observableArrayList(
                java.util.Arrays.stream(Role.values())
                        .filter(r -> r != Role.ADMIN)
                        .toList()));
        roleChoice.getSelectionModel().select(Role.STUDENT);
    }

    @FXML
    protected void onRegister() {
        if (usernameField.getText().isEmpty() ||
                nameField.getText().isEmpty() ||
                emailField.getText().isEmpty() ||
                passwordField.getText().isEmpty()) {
            statusLabel.setText("Please fill all the fields");
            return;
        }
        if (!emailField.getText().contains("@gmail.com")) {
            statusLabel.setText("Email address is invalid");
            return;
        }

        String userid = User.getRandomId();

        User user = new User(
                userid,
                usernameField.getText(),
                nameField.getText(),
                emailField.getText(),
                passwordField.getText(),
                roleChoice.getValue());
        if (authService.register(user) == null) {
            statusLabel.setText("Registeration failed");
            return;
        }
        statusLabel.setText("Registered successfully");
        AppContext.setCurrentUser(user);

        try {
            String fxml = switch (user.getRole()) {
                case ADMIN -> "/fxml/admin.fxml";
                case ORGANIZER -> "/fxml/organizer.fxml";
                default -> "/fxml/student.fxml";
            };
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();

            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Dashboard - " + user.getUsername());
        } catch (Exception ex) {
            statusLabel.setText("Failed to open dashboard");
            ex.printStackTrace();
        }
    }

    @FXML
    private void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
