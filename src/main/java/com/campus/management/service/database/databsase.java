package com.campus.management.service.database;

import com.campus.management.model.EventRegistration;

import java.sql.Connection;
import java.util.List;

public class databsase {

        public static void main(String[] args) {
            Connection con = DBConnection.getConnection();
            System.out.println(con != null ? "CONNECTED" : "FAILED");
            List<EventRegistration> registrations = UserDao.readRegistrations();
//            UserDao.EvetRegister("asas",4);

            System.out.println(registrations != null ? "REGISTRATIONS" : "FAILED");
            System.out.println(registrations.size());
     }

}
