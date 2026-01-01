package com.campus.management.service.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    static Connection connection;
    static boolean tablesCreated = false;

//    private static final String URL =
//            "jdbc:mysql://localhost:3306/campus-event-management?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

//    public static Connection getConnection() {
//        try {
//            return DriverManager.getConnection(URL, USER, PASSWORD);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    private static final String URL = "jdbc:sqlite:campusEvent.db";
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            connection = conn;
            if(!tablesCreated){
                createUsersTable();
                createEventsTable();
                createEventRegistrationsTable();
                tablesCreated = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public static void createEventsTable() {
        String sql = """
        CREATE TABLE IF NOT EXISTS events (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            title TEXT NOT NULL,
            description TEXT,
            date TEXT NOT NULL,
            start_time TEXT NOT NULL,
            end_time TEXT NOT NULL,
            location TEXT,
            category TEXT,
            organizer_id TEXT,
            status TEXT,
            imgURL TEXT
        );
    """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creating events table: " + e.getMessage());
        }
    }

    public static void createUsersTable() {
        String sql = """
        CREATE TABLE IF NOT EXISTS users (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            userid TEXT NOT NULL,
            username TEXT NOT NULL UNIQUE,
            name TEXT NOT NULL,
            email TEXT NOT NULL UNIQUE,
            password TEXT NOT NULL,
            role TEXT NOT NULL
        );
    """;
        try ( Statement stmt = connection.createStatement()) {

            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creating users table: " + e.getMessage());
        }
    }

    public static void createEventRegistrationsTable() {
        String sql = """
        CREATE TABLE IF NOT EXISTS eventRegistrations (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            user_id INTEGER NOT NULL,
            event_id INTEGER NOT NULL,
            UNIQUE (user_id, event_id),
            FOREIGN KEY (user_id) REFERENCES users(userid),
            FOREIGN KEY (event_id) REFERENCES events(id)
        );
    """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println("Error creating eventRegistrations table: " + e.getMessage());
        }
    }

}
