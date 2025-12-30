package com.campus.management.service.database;

import com.campus.management.model.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EventDao {
    public static Event addEvent(Event event){
        String sql = """
        Insert into events(title,description,date,start_time,end_time,location,category,organizer_id,status)
        values(?,?,?,?,?,?,?,?,?)
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

            ps.execute();
            return event;

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
