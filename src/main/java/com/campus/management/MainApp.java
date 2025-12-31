// java
package com.campus.management;

import com.campus.management.controller.EventCardController;
import com.campus.management.controller.StudentController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // initialize services / storage
        AppContext.init();

        // load initial scene (login)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/eventActions.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setTitle("Campus Management");
        primaryStage.setScene(scene);
        primaryStage.setWidth(890);
        primaryStage.setHeight(600);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
