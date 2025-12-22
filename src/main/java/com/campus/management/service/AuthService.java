package com.campus.management.service;

import com.campus.management.model.User;

public interface AuthService {
    User authenticate(String username, String password);
    User register(User user);
}
