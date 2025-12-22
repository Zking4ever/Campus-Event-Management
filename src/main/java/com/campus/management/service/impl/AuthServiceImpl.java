// src/main/java/com/campus/management/service/impl/AuthServiceImpl.java
package com.campus.management.service.impl;

import com.campus.management.model.Role;
import com.campus.management.model.User;
import com.campus.management.service.AuthService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthServiceImpl implements AuthService {
    // simple in-memory user store (use DB later)
    private final Map<String, User> users = new HashMap<>();
    private final Map<String, String> passwordStore = new HashMap<>();

    public AuthServiceImpl() {
        // seed admin
        String id = UUID.randomUUID().toString();
        users.put("admin", new User(id, "admin", "adminHash", Role.ADMIN));
        users.put("student", new User(id, "astawus", "123456", Role.STUDENT));
        users.put("organizer", new User(id, "organizer", "123456", Role.ORGANIZER));
        passwordStore.put("admin", "admin");
        passwordStore.put("student", "123456");
        passwordStore.put("organizer", "123456");
    }

    @Override
    public User authenticate(String username, String password) {
        if (passwordStore.containsKey(username) && passwordStore.get(username).equals(password)) {
            return users.get(username);
        }
        return null;
    }

    @Override
    public void register(User user, String rawPassword) {
        users.put(user.getUsername(), user);
        passwordStore.put(user.getUsername(), rawPassword);
    }
}
