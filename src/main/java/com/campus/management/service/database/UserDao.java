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
}
