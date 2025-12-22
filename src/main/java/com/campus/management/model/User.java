package com.campus.management.model;

import java.util.ArrayList;
import java.util.UUID;

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

    private static final String[] characters = new String[]{"A","B", "C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
                                                            "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
                                                            "0","1","2","3","4","5","6","7","8","9"};

    public static String getRandomId(){
        String id = "";
        int length =0;
        while (length< 5){
            length = (int) Math.floor((Math.random()*25));
        }

        for (int i = 0; i < length; i++) {
            int r = (int) Math.floor((Math.random()*63));
            id = id.concat(characters[r]);
        }
        return id;
    }
}
