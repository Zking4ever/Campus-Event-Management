package com.campus.management.service.database;

import com.campus.management.model.Role;
import com.campus.management.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {
    public static  boolean registerUser(User u){
        String sql = """
        Insert into users(userid,username,name,email,password,role)
        values(?,?,?,?,?,?)
    """;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
                if(con == null) {
                    return false;
                }

            ps.setString(1, u.getUserid());
            ps.setString(2, u.getUsername());
            ps.setString(3, u.getName());
            ps.setString(4,u.getEmail());
            ps.setString(5,u.getPasswordHash());
            ps.setString(6,u.getRole().toString());

            ps.execute();
            return true;

        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        }

    public static User authenticate(String emailORusername, String password) {
        String sql = """
        SELECT userid, username, name, email, password, role
        FROM users
        WHERE username = ?  AND password = ?
    """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con!=null ? con.prepareStatement(sql) : null) {

            if(con == null) {
                return  null;
            }

            ps.setString(1, emailORusername);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getString("userid"),
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role"))
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

