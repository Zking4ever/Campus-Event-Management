// src/main/java/com/campus/management/service/impl/AuthServiceImpl.java
package com.campus.management.service.impl;

import com.campus.management.model.Role;
import com.campus.management.model.User;
import com.campus.management.service.AuthService;
import com.campus.management.service.database.UserDao;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthServiceImpl implements AuthService {


    public AuthServiceImpl() {
        // seed admin
    }

    @Override
    public User authenticate(String username, String password) {
       return  UserDao.authenticate(username, password);
    }

    @Override
    public User register(User user) {
        //process the password to set it to hash in here
        return UserDao.registerUser(user); // saves to db
    }
}
