// java
package com.campus.management.controller;

import com.campus.management.AppContext;
import com.campus.management.model.Role;
import com.campus.management.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DashboardController {
    @FXML private Label welcomeLabel;
    @FXML private Button rolePageButton;
    @FXML private Button registrationButton;
    @FXML private Button logoutButton;

    @FXML
    public void initialize() {
        User current = AppContext.getCurrentUser();
        if (current != null) {
            welcomeLabel.setText("Welcome, " + current.getUsername() + " (" + current.getRole() + ")");
            // label the role button
            rolePageButton.setText(switch (current.getRole()) {
                case ADMIN -> "Open Admin Page";
                case ORGANIZER -> "Open Organizer Page";
                default -> "Open Student Page";
            });
        }
    }

    @FXML
    protected void onOpenRolePage() {
        User current = AppContext.getCurrentUser();
        if (current == null) return;
        String fxml = switch (current.getRole()) {
            case ADMIN -> "/fxml/admin.fxml";
            case ORGANIZER -> "/fxml/organizer.fxml";
            default -> "/fxml/student.fxml";
        };
        loadAndSetScene(fxml);
    }

    @FXML
    protected void onOpenRegistration() {
        loadAndSetScene("/fxml/registration.fxml");
    }

    @FXML
    protected void onLogout() {
        AppContext.setCurrentUser(null);
        loadAndSetScene("/fxml/login.fxml");
    }

    private void loadAndSetScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
