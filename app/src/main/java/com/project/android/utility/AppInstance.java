package com.project.android.utility;

import android.app.Application;
import android.content.Context;

import com.project.android.model.Organization;
import com.project.android.model.Restaurant;
import com.project.android.model.User;


public class AppInstance extends Application {
    public static AppInstance instance = null;

    public Organization getCurrentOrganization() {
        return currentOrganization;
    }

    public void setCurrentOrganization(Organization currentOrganization) {
        this.currentOrganization = currentOrganization;
    }

    private Organization currentOrganization;
    private Restaurant currentRestaurant;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    private User currentUser;




    private boolean isAdminUser;

    private static Context mContext;

    public static Context getInstance()
    {
        if (null == instance) {
            instance = new AppInstance();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }


    public boolean isAdminUser()
    {
        return isAdminUser;
    }

    public void setAdminUser(boolean isAdminUser)
    {
        this.isAdminUser = isAdminUser;
    }

    public Restaurant getCurrentRestaurant() {
        return currentRestaurant;
    }

    public void setCurrentRestaurant(Restaurant currentRestaurant) {
        this.currentRestaurant = currentRestaurant;
    }
}


