package com.campus.management.model;

public class User {
    private String id;
    private String username;
    private String passwordHash;
    private Role role;

    public User(String id, String username, String passwordHash, Role role) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    // getters / setters
    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public Role getRole() { return role; }
}
