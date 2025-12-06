package com.project.android.model;

public class Restaurant {
    private long RestaurantID;
    private String name;
    private String mail;
    private String address;
    private String website;
    private String phno;
    private String foodType;
    private String profilePath;
    private String userName;
    private String password;
    private String type;
    private boolean deleted;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    private boolean approved;


    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getPhno() {

        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getWebsite() {

        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {

        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMail() {

        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRestaurantID() {

        return RestaurantID;
    }

    public void setRestaurantID(long restaurantID) {
        RestaurantID = restaurantID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
