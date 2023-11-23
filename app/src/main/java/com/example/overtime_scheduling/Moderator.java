package com.example.overtime_scheduling;

public class Moderator {
    String key = "";
    String name = "";
    String email = "";
    String contact = "";
    String address = "";
    String bloodGroup = "";
    String userId = "";
    String imageString = "";
    public Moderator(String key, String name, String email, String contact, String address, String bloodGroup, String userId, String imageString){
        this.key = key;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.bloodGroup = bloodGroup;
        this.userId = userId;
        this.imageString = imageString;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getAddress() {
        return address;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getUserId() {
        return userId;
    }

    public String getImageString() {
        return imageString;
    }
}
