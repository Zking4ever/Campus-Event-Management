# Campus Event Management System

## Prerequisites
- Java 25 or higher
- Maven (for building)

## Features
- **Role-Based Access Control**: Separate dashboards for Admins, Organizers, and Students.
- **Student Portal**: Browse events, filter by category (Academic, Art, Sport, Tech, Entertainment), and one-click registration.
- **Organizer Tools**: Create and manage events, track attendee statistics.
- **Admin Dashboard**: Comprehensive system overview with charts, user management, and event approval workflows.
- **Secure Authentication**: Password hashing using BCrypt.
- **Persistent Storage**: Local SQLite database for data persistence.
- **Modern UI**: Responsive JavaFX interface with custom styling.

## How to Run
1. Navigate to the folder containing the jar file.
2. Open a terminal and run:
   ```sh
   java -jar campus-event-management.jar
   ```
   if in JavaFX 11+
   ```sh
   java --module-path /path/to/javafx/lib \
     --add-modules javafx.controls,javafx.fxml \
     -jar yourApp.jar
     ```
## Default Credentials
The system automatically creates an admin account on first launch:
- **Username:** `admin`
- **Password:** `admin`
- **Email:** `admin@gmail.com`

**Important:** Please change these credentials or create a new admin account immediately after logging in for security.

## Database
The application uses a local SQLite database (`campusEvent.db`) which will be created automatically in the same directory.

## Contribute
 [https://github.com/Zking4ever/Campus-Event-Management]
