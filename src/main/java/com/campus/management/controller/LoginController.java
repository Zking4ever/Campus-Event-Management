package com.campus.management.controller;

import com.campus.management.AppContext;
import com.campus.management.model.User;
import com.campus.management.service.AuthService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    private final AuthService authService = AppContext.getAuthService();

    @FXML
    protected void onLogin() {
        statusLabel.setText("");
        String u = usernameField.getText();
        String p = passwordField.getText();
        if (u.equals("") || p.equals("")) {
            statusLabel.setText("All fields are required!");
            return;
        }
        User user = authService.authenticate(u, p);
        if (user == null) {
            statusLabel.setText("Invalid credentials");
            return;
        }
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
    private void goToRegister(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registration.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
        } catch (Exception e){
            statusLabel.setText("Failed to open registration page");
            e.printStackTrace();
        }
    }
}
