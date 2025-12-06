package com.project.android.model;

public class Feedback {
    private long feedbackID;
    private long restaurantID;
    private long organizationID;
    private String description;
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public long getOrganizationID()
    {

        return organizationID;
    }
    public void setOrganizationID(long organizationID)
    {
        this.organizationID = organizationID;
    }
    public long getRestaurantID()
    {
        return restaurantID;
    }
    public void setRestaurantID(long restaurantID)
    {
        this.restaurantID = restaurantID;
    }
    public long getFeedbackID()
    {
        return feedbackID;
    }
    public void setFeedbackID(long feedbackID) {
        this.feedbackID = feedbackID;
    }
}

