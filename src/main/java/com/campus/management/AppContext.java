// java
package com.campus.management;

import com.campus.management.model.User;
import com.campus.management.service.AuthService;
import com.campus.management.service.EventService;
import com.campus.management.service.impl.AuthServiceImpl;
import com.campus.management.service.impl.EventServiceImpl;

/*
 Simple global context / service locator for small apps.
 Replace with DI (Guice / Spring) for larger projects.
*/
public final class AppContext {
    private static AuthService authService;
    private static EventService eventService;
    private static User currentUser;

    private AppContext() { /* no-op */ }

    public static void init() {
        // wire in-memory implementations (swap for DAO/DB implementations later)
        authService = new AuthServiceImpl();
        eventService = new EventServiceImpl();
    }

    public static AuthService getAuthService() {
        return authService;
    }

    public static EventService getEventService() {
        return eventService;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
