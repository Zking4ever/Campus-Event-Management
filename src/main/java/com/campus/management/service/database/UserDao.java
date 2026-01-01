package com.campus.management.service.database;

import com.campus.management.model.EventRegistration;
import com.campus.management.model.Role;
import com.campus.management.model.User;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public static User registerUser(User u) {
        String sql = """
                    Insert into users(userid,username,name,email,password,role)
                    values(?,?,?,?,?,?)
                """;
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            if (con == null) {
                return null;
            }
            ps.setString(1, u.getUserid());
            ps.setString(2, u.getUsername());
            ps.setString(3, u.getName());
            ps.setString(4, u.getEmail());
            // Hash the password
            String hashedPassword = BCrypt.hashpw(u.getPasswordHash(), BCrypt.gensalt());
            ps.setString(5, hashedPassword);
            ps.setString(6, u.getRole().toString());

            ps.execute();
            return u;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static User authenticate(String emailORusername, String password) {
        String sql = """
                    SELECT userid, username, name, email, password, role
                    FROM users
                    WHERE username = ? OR email = ?
                """;

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con != null ? con.prepareStatement(sql) : null) {

            if (con == null) {
                return null;
            }

            ps.setString(1, emailORusername);
            ps.setString(2, emailORusername);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                if (BCrypt.checkpw(password, storedHash)) {
                    return new User(
                            rs.getString("userid"),
                            rs.getString("username"),
                            rs.getString("name"),
                            rs.getString("email"),
                            storedHash, // Return stored hash (or empty) to keep object valid
                            Role.valueOf(rs.getString("role")));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void EvetRegister(String userid, int event_id) {
        String sql = """
                    Insert into eventRegistrations(user_id,event_id)
                    values(?,?)
                """;
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userid);
            ps.setInt(2, event_id);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<EventRegistration> readRegistrations() {
        String sql = """
                Select * from eventRegistrations
                """;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            List<EventRegistration> registrationList = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                EventRegistration event = new EventRegistration(rs.getString("user_id"), rs.getInt("event_id"));
                registrationList.add(event);
            }
            return registrationList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(new User(
                        rs.getString("userid"),
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getString("email"),
                        "", // Don't allow password reading
                        Role.valueOf(rs.getString("role"))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public static void deleteUser(String userId) {
        String sql = "DELETE FROM users WHERE userid = ?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Using Role update for blocking logic as requested
    public static void updateUserRole(String userId, String newRole) {
        String sql = "UPDATE users SET role = ? WHERE userid = ?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newRole);
            ps.setString(2, userId);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<User> getAttendeesForEvent(int eventId) {
        List<User> users = new ArrayList<>();
        String sql = """
                    SELECT u.userid, u.username, u.name, u.email, u.role
                    FROM users u
                    JOIN eventRegistrations er ON u.userid = er.user_id
                    WHERE er.event_id = ?
                """;
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(new User(
                        rs.getString("userid"),
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getString("email"),
                        "",
                        Role.valueOf(rs.getString("role"))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
