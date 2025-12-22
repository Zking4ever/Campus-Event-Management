// java
package com.campus.management.controller;

import com.campus.management.AppContext;
import com.campus.management.model.Role;
import com.campus.management.model.User;
import com.campus.management.service.AuthService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.UUID;

public class RegistrationController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private ChoiceBox<Role> roleChoice;
    @FXML private Label statusLabel;

    private final AuthService authService = AppContext.getAuthService();

    @FXML
    public void initialize() {
        roleChoice.setItems(FXCollections.observableArrayList(Role.values()));
        roleChoice.getSelectionModel().select(Role.STUDENT);
    }

    @FXML
    protected void onRegister() {
        String username = usernameField.getText();
        String rawPassword = passwordField.getText();
        Role role = roleChoice.getValue();

        if (username == null || username.isBlank() || rawPassword == null || rawPassword.isBlank()) {
            statusLabel.setText("Username and password required");
            return;
        }

        String id = UUID.randomUUID().toString();
        // store raw password for the simple in-memory impl (replace with hashed in production)
        User user = new User(id, username, rawPassword, role);
        authService.register(user, rawPassword);
        statusLabel.setText("Registered successfully");

        // return to login
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
