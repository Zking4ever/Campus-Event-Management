package com.campus.management.service.database;

import com.campus.management.model.Event;
import com.campus.management.model.EventRegistration;
import com.campus.management.service.EventService;
import com.campus.management.service.impl.EventServiceImpl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class databsase {

        public static void main(String[] args) {
            EventService eventService = new EventServiceImpl();

            List<EventRegistration> registrations = UserDao.readRegistrations();
            List<Event> list = eventService.listEvents();
            List<Event> myRegisteredEventList = new ArrayList<>();

            String userid = "8";
            for (EventRegistration registration : registrations) {
                if (registration.getUser_id().equals(userid)) {
                    for (Event event : list) {
                        if (registration.getEvent_id() == Integer.parseInt(event.getId())){
                            myRegisteredEventList.add(event);
                        }
                    }
                }
            }

            System.out.println(registrations.size());
            System.out.println(myRegisteredEventList.size());
            System.out.println(myRegisteredEventList.get(0).getTitle());
            System.out.println(myRegisteredEventList.get(1).getTitle());
     }

}
