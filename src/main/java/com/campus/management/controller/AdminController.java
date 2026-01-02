// src/main/java/com/campus/management/controller/AdminController.java
package com.campus.management.controller;

import com.campus.management.model.Event;
import com.campus.management.model.EventStatus;
import com.campus.management.model.User;
import com.campus.management.service.EventService;
import com.campus.management.service.impl.EventServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class AdminController {

    @FXML
    private ListView<Event> eventsListView;
    @FXML
    private ListView<User> usersListView;
    @FXML
    private Label statusLabel;
    @FXML
    private javafx.scene.layout.VBox dashboardView;
    @FXML
    private javafx.scene.layout.VBox eventsView;
    @FXML
    private javafx.scene.layout.VBox usersView;
    @FXML
    private javafx.scene.control.Button dashboardBtn;
    @FXML
    private javafx.scene.control.Button eventsBtn;
    @FXML
    private javafx.scene.control.Button usersBtn;

    @FXML
    private Label totalEventsLabel;
    @FXML
    private Label totalUsersLabel;
    @FXML
    private Label pendingEventsLabel;

    private final EventService eventService = new EventServiceImpl();

    @FXML
    public void initialize() {
        setupEventListView();
        setupUserListView();
        showDashboard(); // Default view
    }

    // --- Navigation Logic ---

    @FXML
    protected void showDashboard() {
        dashboardView.setVisible(true);
        eventsView.setVisible(false);
        usersView.setVisible(false);
        updateNavigationState(dashboardBtn);
        loadDashboardStats();
    }

    @FXML
    protected void showEventsView() {
        dashboardView.setVisible(false);
        eventsView.setVisible(true);
        usersView.setVisible(false);
        updateNavigationState(eventsBtn);
        loadAllEvents();
    }

    @FXML
    protected void showUsersView() {
        dashboardView.setVisible(false);
        eventsView.setVisible(false);
        usersView.setVisible(true);
        updateNavigationState(usersBtn);
        loadAllUsers();
    }

    private void updateNavigationState(Button activeBtn) {
        // Reset all
        setStyleForNavBtn(dashboardBtn, false);
        setStyleForNavBtn(eventsBtn, false);
        setStyleForNavBtn(usersBtn, false);

        // Highlight active
        setStyleForNavBtn(activeBtn, true);
    }

    private void setStyleForNavBtn(Button btn, boolean isActive) {
        if (btn == null)
            return;
        if (isActive) {
            btn.setStyle(
                    "-fx-text-fill: white; -fx-alignment: CENTER_LEFT; -fx-background-color: rgba(255,255,255,0.1); -fx-border-color: #4f46e5; -fx-border-width: 0 0 0 3;");
        } else {
            btn.setStyle(
                    "-fx-text-fill: white; -fx-alignment: CENTER_LEFT; -fx-background-color: transparent; -fx-border-width: 0;");
        }
    }

    private void loadDashboardStats() {
        if (totalEventsLabel == null)
            return;

        java.util.List<Event> allEvents = eventService.listEvents();
        totalEventsLabel.setText(String.valueOf(allEvents.size()));

        long pending = allEvents.stream().filter(e -> e.getStatus() == EventStatus.PENDING).count();
        pendingEventsLabel.setText(String.valueOf(pending));

        java.util.List<User> allUsers = com.campus.management.service.database.UserDao.getAllUsers();
        totalUsersLabel.setText(String.valueOf(allUsers.size()));
    }

    // --- View Logic ---

    private void setupEventListView() {
        if (eventsListView != null) {
            eventsListView.setCellFactory(param -> new javafx.scene.control.ListCell<>() {
                @Override
                protected void updateItem(Event item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        HBox container = new javafx.scene.layout.HBox(10);
                        container.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

                        Label titleLbl = new Label(item.getTitle());
                        titleLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                        titleLbl.setPrefWidth(180);

                        Label dateLbl = new Label(item.getDate().toString());
                        dateLbl.setPrefWidth(90);

                        Label detailsLbl = new Label(item.getLocation());
                        detailsLbl.setPrefWidth(120);

                        Label statusLbl = new Label(
                                item.getStatus().toString());
                        String statusStyle = switch (item.getStatus()) {
                            case APPROVED ->
                                "-fx-text-fill: green; -fx-background-color: #dcfce7; -fx-padding: 3 8; -fx-background-radius: 10;";
                            case REJECTED ->
                                "-fx-text-fill: red; -fx-background-color: #fee2e2; -fx-padding: 3 8; -fx-background-radius: 10;";
                            default ->
                                "-fx-text-fill: orange; -fx-background-color: #ffedd5; -fx-padding: 3 8; -fx-background-radius: 10;";
                        };
                        statusLbl.setStyle(statusStyle);
                        statusLbl.setPrefWidth(80);
                        statusLbl.setAlignment(javafx.geometry.Pos.CENTER);

                        javafx.scene.layout.Region spacer = new javafx.scene.layout.Region();
                        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

                        // inline actions
                        javafx.scene.layout.HBox actions = new javafx.scene.layout.HBox(5);
                        actions.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);

                        if (item.getStatus() == EventStatus.PENDING) {
                            Button approveBtn = new Button("✓");
                            approveBtn.setStyle(
                                    "-fx-text-fill: white; -fx-background-color: #10b981; -fx-cursor: hand; -fx-font-weight: bold;");
                            approveBtn.setTooltip(new javafx.scene.control.Tooltip("Approve"));
                            approveBtn.setOnAction(e -> {
                                eventService.updateStatus(item.getId(), "APPROVED");
                                loadPendingEvents(); // Refresh view
                            });

                            javafx.scene.control.Button rejectBtn = new javafx.scene.control.Button("✗");
                            rejectBtn.setStyle(
                                    "-fx-text-fill: white; -fx-background-color: #f59e0b; -fx-cursor: hand; -fx-font-weight: bold;");
                            rejectBtn.setTooltip(new javafx.scene.control.Tooltip("Reject"));
                            rejectBtn.setOnAction(e -> {
                                eventService.updateStatus(item.getId(), "REJECTED");
                                loadPendingEvents(); // Refresh view
                            });
                            actions.getChildren().addAll(approveBtn, rejectBtn);
                        }

                        javafx.scene.control.Button deleteBtn = new javafx.scene.control.Button("🗑");
                        deleteBtn.setStyle("-fx-text-fill: white; -fx-background-color: #ef4444; -fx-cursor: hand;");
                        deleteBtn.setTooltip(new javafx.scene.control.Tooltip("Delete"));
                        deleteBtn.setOnAction(e -> {
                            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                                    javafx.scene.control.Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Delete Event");
                            alert.setHeaderText("Are you sure you want to delete '" + item.getTitle() + "'?");
                            if (alert.showAndWait().orElse(
                                    javafx.scene.control.ButtonType.CANCEL) == javafx.scene.control.ButtonType.OK) {
                                eventService.deleteEvent(item.getId());
                                // Refresh current view (either all or pending)
                                if (statusLabel.getText().toLowerCase().contains("pending")) {
                                    loadPendingEvents();
                                } else {
                                    loadAllEvents();
                                }
                            }
                        });
                        actions.getChildren().add(deleteBtn);

                        container.getChildren().addAll(titleLbl, dateLbl, detailsLbl, statusLbl, spacer, actions);
                        setGraphic(container);
                    }
                }
            });
        }
    }

    private void setupUserListView() {
        if (usersListView != null) {
            usersListView.setCellFactory(param -> new javafx.scene.control.ListCell<>() {
                @Override
                protected void updateItem(User item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        javafx.scene.layout.HBox container = new javafx.scene.layout.HBox(10);
                        container.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

                        javafx.scene.control.Label nameLbl = new javafx.scene.control.Label(item.getName());
                        nameLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                        nameLbl.setPrefWidth(150);

                        javafx.scene.control.Label userLbl = new javafx.scene.control.Label("@" + item.getUsername());
                        userLbl.setStyle("-fx-text-fill: #64748b;");
                        userLbl.setPrefWidth(120);

                        javafx.scene.control.Label emailLbl = new javafx.scene.control.Label(item.getEmail());
                        emailLbl.setPrefWidth(200);

                        javafx.scene.control.Label roleLbl = new javafx.scene.control.Label(item.getRole().toString());
                        roleLbl.setStyle(
                                "-fx-background-color: #e2e8f0; -fx-padding: 2 6; -fx-background-radius: 4; -fx-font-size: 11px;");

                        javafx.scene.layout.Region spacer = new javafx.scene.layout.Region();
                        javafx.scene.layout.HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

                        javafx.scene.control.Button deleteBtn = new javafx.scene.control.Button("🗑");
                        deleteBtn.setStyle("-fx-text-fill: white; -fx-background-color: #ef4444; -fx-cursor: hand;");
                        deleteBtn.setOnAction(e -> {
                            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                                    javafx.scene.control.Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Remove User");
                            alert.setHeaderText("Remove " + item.getUsername() + "?");
                            if (alert.showAndWait().orElse(
                                    javafx.scene.control.ButtonType.CANCEL) == javafx.scene.control.ButtonType.OK) {
                                com.campus.management.service.database.UserDao.deleteUser(item.getUserid());
                                loadAllUsers();
                            }
                        });

                        container.getChildren().addAll(nameLbl, userLbl, emailLbl, roleLbl, spacer, deleteBtn);
                        setGraphic(container);
                    }
                }
            });
        }
    }


    @FXML
    protected void loadAllEvents() {
        if (eventsListView != null) {
            eventsListView.getItems().setAll(eventService.listEvents());
            if (statusLabel != null)
                statusLabel.setText("Loaded all events");
        }
    }

    @FXML
    protected void loadPendingEvents() {
        if (eventsListView != null) {
            eventsListView.getItems().setAll(
                    eventService.listEvents().stream()
                            .filter(e -> e.getStatus() == EventStatus.PENDING)
                            .toList());
            if (statusLabel != null)
                statusLabel.setText("Showing pending events");
        }
    }

    @FXML
    protected void approveSelected() {
        Event selected = eventsListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            eventService.updateStatus(selected.getId(), "APPROVED");
            loadPendingEvents();
            if (statusLabel != null)
                statusLabel.setText("Event approved");
        }
    }

    @FXML
    protected void rejectSelected() {
        Event selected = eventsListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            eventService.updateStatus(selected.getId(), "REJECTED");
            loadPendingEvents();
            if (statusLabel != null)
                statusLabel.setText("Event rejected");
        }
    }

    @FXML
    protected void deleteSelected() {
        Event selected = eventsListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            eventService.deleteEvent(selected.getId());
            loadAllEvents();
            if (statusLabel != null)
                statusLabel.setText("Event deleted");
        }
    }

    // --- User Logic ---

    @FXML
    protected void loadAllUsers() {
        if (usersListView != null) {
            usersListView.getItems().setAll(com.campus.management.service.database.UserDao.getAllUsers());
        }
    }

    @FXML
    protected void blockSelectedUser() {
        User selected = usersListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Using "STUDENT" role change or some other logic. Request said "Block".
            com.campus.management.service.database.UserDao.deleteUser(selected.getUserid());
            loadAllUsers();
        }
    }

    @FXML
    protected void removeSelectedUser() {
        User selected = usersListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            com.campus.management.service.database.UserDao.deleteUser(selected.getUserid());
            loadAllUsers();
        }
    }

    @FXML
    protected void onLogout() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = (javafx.stage.Stage) eventsListView.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root, 800, 600));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
