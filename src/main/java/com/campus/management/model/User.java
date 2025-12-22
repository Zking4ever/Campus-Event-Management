package com.campus.management.model;

public class User {
    private String userid;
    private String username;
    private String name;
    private String email;
    private String passwordHash;
    private Role role;

    public User(String userid, String username, String name, String email, String passwordHash, Role role) {
        this.userid = userid;
        this.username = username;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    // getters / setters
    public String getUserid() { return userid; }
    public String getUsername() { return username; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public Role getRole() { return role; }
}
