package com.project.android.model;

public class User {
    private long userID;
    private String name;
    private String username;
    private String password;
    private String confirmPassword;
    private String profilePhoto;
    private String mail;
    private String phono;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhono() {

        return phono;
    }

    public void setPhono(String phono) {
        this.phono = phono;
    }

    public String getMail() {

        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getProfilePhoto() {

        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getConfirmPassword() {

        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUserID() {

        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }
}
