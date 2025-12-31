package com.campus.management.service.database;

import com.campus.management.model.Event;
import com.campus.management.model.EventStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventDao {
    public static Event addEvent(Event event){
        String sql = """
        Insert into events(title,description,date,start_time,end_time,location,category,organizer_id,status,imgURL)
        values(?,?,?,?,?,?,?,?,?,?)
    """;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if(con == null) {
                return null;
            }
            ps.setString(1, event.getTitle());
            ps.setString(2, event.getDescription());
            ps.setString(3, event.getDate().toString());
            ps.setString(4, event.getStart());
            ps.setString(5, event.getEnd());
            ps.setString(6, event.getLocation());
            ps.setString(7, event.getCategory());
            ps.setString(8, event.getOrganizerId());
            ps.setString(9, event.getStatus().toString());
            ps.setString(10,event.getImageUrl());

            ps.execute();
            return event;

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static List<Event> readEvents() {
        List<Event> events = new ArrayList<>();
        String sql = """
                    Select * from events
                """;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Event e = new Event(
                        String.valueOf(rs.getInt("id")),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("organizer_id"),
                        LocalDate.parse(rs.getString("date")),
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        EventStatus.valueOf(rs.getString("status")),
                        rs.getString("category"),
                        rs.getString("location"),
                        rs.getString("imgURL")
                    );
                events.add(e);
            }
            return events;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Event updateEvent(Event event){
        String sql = """
        UPDATE events
            SET title = ?,
                description = ?,
                date = ?,
                start_time = ?,
                end_time = ?,
                location = ?,
                category = ?,
                organizer_id = ?,
                status = ?,
                imgURL = ?
            WHERE id = ?;
    """;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if(con == null) {
                return null;
            }
            ps.setString(1, event.getTitle());
            ps.setString(2, event.getDescription());
            ps.setString(3, event.getDate().toString());
            ps.setString(4,event.getStart());
            ps.setString(5,event.getEnd());
            ps.setString(6,event.getLocation());
            ps.setString(7,event.getCategory());
            ps.setString(8,event.getOrganizerId());
            ps.setString(9,event.getStatus().toString());
            ps.setString(10,event.getImageUrl());
            ps.setString(11,event.getId());


            ps.execute();
            return event;

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
