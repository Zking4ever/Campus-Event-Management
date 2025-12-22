module com.campus.management {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;
    requires mysql.connector.j;

    exports com.campus.management.model;
    exports com.campus.management.service;
//    exports com.campus.management.util;
//    exports com.campus.management.storage;

    opens com.campus.management.controller to javafx.fxml;
    opens com.campus.management.model to javafx.fxml;
    exports com.campus.management.controller;
    exports com.campus.management;
    opens com.campus.management to javafx.graphics, javafx.fxml;
    exports com.campus.management.service.database;
}
