// src/main/java/com/campus/management/service/AuthService.java
package com.campus.management.service;

import com.campus.management.model.User;

public interface AuthService {
    User authenticate(String username, String password);
    boolean register(User user);
}
