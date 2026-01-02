package com.campus.management.controller;

import com.campus.management.AppContext;
import com.campus.management.model.Event;
import com.campus.management.model.EventRegistration;
import com.campus.management.model.EventStatus;
import com.campus.management.model.User;
import com.campus.management.service.EventService;
import com.campus.management.service.database.UserDao;
import com.campus.management.service.impl.EventServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OrganizerController {

    @FXML
    private javafx.scene.chart.PieChart categoryChart;
    @FXML
    private javafx.scene.chart.BarChart<String, Number> attendanceChart;
    @FXML
    private javafx.scene.chart.CategoryAxis xAxis;
    @FXML
    private javafx.scene.chart.NumberAxis yAxis;
    @FXML
    private TabPane tabPane;
    @FXML
    private ProgressBar progressBar;
    @FXML
    public VBox EventContainer;
    @FXML
    HBox rootContainer;
    @FXML
    Label attendersLabel;
    @FXML
    Label pendingEventsLabel;
    @FXML
    VBox MainContentContainer;

    private final EventService eventService = new EventServiceImpl();
    static List<Event> eventList;
    String orgainizer_id;
    @FXML
    ImageView profileImage;
    @FXML
    Label eventNoLabel;
    @FXML
    Label orgainizer_name;
    @FXML
    Label orgainizer_username;

    @FXML
    public void initialize() {
        User current = AppContext.getCurrentUser();
        if (current != null) {
            orgainizer_id = current.getUserid();
            orgainizer_name.setText(current.getName());
            orgainizer_username.setText("@" + current.getUsername());
        }
        try {
            profileImage.setImage(new Image(getClass().getResource("/images/person.png").toExternalForm()));
        } catch (Exception e) {
            System.out.println("Image load error: " + e.getMessage());
        }

        loadEventLists();
        renderEventList();
        if (eventList != null) {
            eventNoLabel.setText(String.valueOf(eventList.size()));
        }

        if (MainContentContainer != null && rootContainer != null) {
            MainContentContainer.prefWidthProperty().bind(rootContainer.widthProperty().multiply(0.8));
        }

        if (progressBar != null) {
            progressBar.setProgress(0.73);
        }
        loadAnalytics();
    }

    private void loadAnalytics() {
        // Category Chart
        if (categoryChart != null && eventList != null) {
            categoryChart.getData().clear();
            eventList.stream()
                    .collect(Collectors.groupingBy(Event::getCategory, Collectors.counting()))
                    .forEach((category, count) -> categoryChart.getData()
                            .add(new javafx.scene.chart.PieChart.Data(category, count)));
        }

        // Attendance Chart
        if (attendanceChart != null && eventList != null) {
            attendanceChart.getData().clear();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Attendees");

            List<EventRegistration> allRegistrations = UserDao.readRegistrations();

            for (Event event : eventList) {
                long count = allRegistrations.stream()
                        .filter(r -> r.getEvent_id() == Integer.parseInt(event.getId()))
                        .count();
                series.getData().add(new XYChart.Data<>(event.getTitle(), count));
            }
            attendanceChart.getData().add(series);
        }
    }

    protected void loadEventLists() {
        if (orgainizer_id == null)
            return;
        List<Event> all = eventService.listEvents();
        eventList = all.stream().filter(e -> orgainizer_id.equals(e.getOrganizerId())).toList();

        Set<String> eventids = eventList.stream()
                .map(Event::getId)
                .collect(Collectors.toSet());
        List<EventRegistration> myRegister = UserDao.readRegistrations();
        myRegister.removeIf(e -> !eventids.contains(String.valueOf(e.getEvent_id())));
        attendersLabel.setText(String.valueOf(myRegister.size()));

        // Pending Events Count
        long pendingCount = eventList.stream()
                .filter(e -> e.getStatus() == EventStatus.PENDING)
                .count();
        if (pendingEventsLabel != null) {
            pendingEventsLabel.setText(String.valueOf(pendingCount));
        }
    }

    private void renderEventList() {
        if (EventContainer == null || eventList == null)
            return;
        EventContainer.getChildren().clear();
        try {
            for (Event event : eventList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/eventCardOrganizer.fxml"));
                Parent root = loader.load();
                EventCardOrganizerController controller = loader.getController();
                controller.setEventData(event);
                EventContainer.getChildren().add(root);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void handleCreateEvent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/eventActions.fxml"));
            Parent root = loader.load();
            EventActionController controller = loader.getController();
            controller.setToDefault();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Create Event");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(EventContainer.getScene().getWindow());
            stage.showAndWait();
            loadEventLists();
            renderEventList();
            loadAnalytics();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    protected void onLogout() {
        AppContext.setCurrentUser(null);
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            javafx.scene.Parent root = loader.load();
            Stage stage = (Stage) rootContainer.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root, 800, 600));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
