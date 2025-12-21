// src/main/java/com/campus/management/controller/LoginController.java
package com.campus.management.controller;

import com.campus.management.model.Role;
import com.campus.management.model.User;
import com.campus.management.service.AuthService;
import com.campus.management.service.impl.AuthServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    private final AuthService authService = new AuthServiceImpl();

    @FXML
    protected void onLogin() {
        String u = usernameField.getText();
        String p = passwordField.getText();
        User user = authService.authenticate(u, p);
        if (user == null) {
            statusLabel.setText("Invalid credentials");
            return;
        }
        // route by role (replace with scene switch logic)
        if (user.getRole() == Role.ADMIN) {
            statusLabel.setText("Welcome Admin");
        } else if (user.getRole() == Role.ORGANIZER) {
            statusLabel.setText("Welcome Organizer");
        } else {
            statusLabel.setText("Welcome Student");
        }
    }
}
