package com.project.android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import com.project.android.model.Donation;
import com.project.android.model.Feedback;
import com.project.android.model.Organization;
import com.project.android.model.Restaurant;
import com.project.android.model.User;
import com.project.android.utility.Constants;
import com.project.android.utility.Utility;

public class AppDatabaseHelper extends SQLiteOpenHelper{ public AppDatabaseHelper(Context context) {
    super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
}

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_ORGANIZATION_TABLE = "CREATE TABLE " + Constants.ORGANIZATION_TABLE_NAME + "(" +
                Constants.ID_KEY + " INTEGER PRIMARY KEY," +
                Constants.ORGANIZATION_NAME_KEY + " TEXT," +
                Constants.ORGANIZATION_PASSWORD_KEY + " TEXT," +
                Constants.ORGANIZATION_MAIL_KEY + " TEXT," +
                Constants.ORGANIZATION_MOBILE_KEY + " TEXT," +
                Constants.ORGANIZATION_ADDRESS_KEY + " TEXT," +
                Constants.ORGANIZATION_PROFILEPATH_KEY + " TEXT," +
                Constants.ORGANIZATION_USERNAME_KEY + " TEXT," +
                Constants.ORGANIZATION_DELETED_KEY + " INTEGER," +
                Constants.ORGANIZATION_APPROVED_KEY + " INTEGER" +
                ")";
        String CREATE_FEEDBACK_TABLE = "CREATE TABLE " + Constants.FEEDBACK_TABLE_NAME + "(" +
                Constants.ID_KEY + " INTEGER PRIMARY KEY," +
                Constants.FEEDBACK_DESCRIPTION_KEY + " TEXT," +
                Constants.RESTAURANT_ID_KEY + " INTEGER," +
                Constants.ORGANIZATION_ID_KEY + " INTEGER" +
                ")";

        String CREATE_RESTAURANT_TABLE = "CREATE TABLE " + Constants.RESTAURANT_TABLE_NAME + "(" +
                Constants.ID_KEY + " INTEGER PRIMARY KEY," +
                Constants.RESTAURANT_NAME_KEY + " TEXT," +
                Constants.RESTAURANT_USERNAME_KEY + " TEXT," +
                Constants.RESTAURANT_PASSWORD_KEY + " TEXT," +
                Constants.RESTAURANT_MOBILE_KEY + " TEXT," +
                Constants.RESTAURANT_EMAIL_KEY + " TEXT," +
                Constants.RESTAURANT_ADDRESS_KEY + " TEXT," +
                Constants.RESTAURANT_TYPE_KEY + " TEXT," +
                Constants.RESTAURANT_APPROVED_KEY + " INTEGER," +
                Constants.RESTAURANT_DELETED_KEY + " INTEGER," +
                Constants.RESTAURANT_PROFILE_PATH_KEY + " TEXT" +
                ")";

        String CREATE_USER_TABLE = "CREATE TABLE " + Constants.USER_TABLE_NAME + "(" +
                Constants.ID_KEY + " INTEGER PRIMARY KEY," +
                Constants.USER_NAME_KEY + " TEXT," +
                Constants.USER_USERNAME_KEY + " TEXT," +
                Constants.USER_PASSWORD_KEY + " TEXT," +
                Constants.USER_MOBILE_KEY + " TEXT," +
                Constants.USER_MAIL_KEY + " TEXT," +
                Constants.USER_ADDRESS_KEY + " TEXT," +
                Constants.USER_PROFILEPATH_KEY + " TEXT" +
                ")";

        String CREATE_DONATION_TABLE = "CREATE TABLE " + Constants.DONATION_TABLE_NAME + "(" +
                Constants.ID_KEY + " INTEGER PRIMARY KEY," +
                Constants.FOOD_TYPE_KEY + " TEXT," +
                Constants.DONATION_DATE_KEY + " TEXT," +
                Constants.NUMBER_OF_PERSONS_KEY + " INTEGER," +
                Constants.RESTAURANT_ID_KEY + " INTEGER" +
                ")";


        db.execSQL(CREATE_ORGANIZATION_TABLE);
        db.execSQL(CREATE_FEEDBACK_TABLE);
        db.execSQL(CREATE_RESTAURANT_TABLE);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_DONATION_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addOrganization(Organization organization)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // Inserting Row to User table
        values.put(Constants.ORGANIZATION_NAME_KEY, organization.getOrganizationname());
        values.put(Constants.ORGANIZATION_USERNAME_KEY, organization.getUsername());
        values.put(Constants.ORGANIZATION_PASSWORD_KEY, organization.getPassword());
        values.put(Constants.ORGANIZATION_MAIL_KEY, organization.getEmail());
        values.put(Constants.ORGANIZATION_APPROVED_KEY,organization.isApproved());
        values.put(Constants.ORGANIZATION_PROFILEPATH_KEY, organization.getProfilePath());
        values.put(Constants.ORGANIZATION_MOBILE_KEY, organization.getPhno());
        values.put(Constants.ORGANIZATION_ADDRESS_KEY,organization.getAddress());
        values.put(Constants.ORGANIZATION_DELETED_KEY,organization.isDeleted());


        long organization_id = db.insert(Constants.ORGANIZATION_TABLE_NAME, null, values);

        db.close();
        return organization_id;
    }


    public long addUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // Inserting Row to User table
        values.put(Constants.USER_NAME_KEY, user.getName());
        values.put(Constants.USER_USERNAME_KEY, user.getUsername());
        values.put(Constants.USER_PASSWORD_KEY, user.getPassword());
        values.put(Constants.USER_MAIL_KEY, user.getMail());

        values.put(Constants.USER_PROFILEPATH_KEY, user.getProfilePhoto());
        values.put(Constants.USER_MOBILE_KEY, user.getPhono());
        values.put(Constants.USER_ADDRESS_KEY,user.getAddress());


        long user_id = db.insert(Constants.USER_TABLE_NAME, null, values);

        db.close();
        return user_id;
    }
    public User getUser(String userName, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.USER_TABLE_NAME;

        String[] columns = new String[]{Constants.ID_KEY,
                Constants.USER_NAME_KEY, Constants.USER_PASSWORD_KEY,Constants.USER_ADDRESS_KEY,Constants.USER_MAIL_KEY, Constants.USER_PROFILEPATH_KEY,Constants.USER_MOBILE_KEY};

        String where = Constants.USER_USERNAME_KEY + " =?" + " AND " + Constants.USER_PASSWORD_KEY + " =?";

        Cursor cursor = db.query(table, columns, where,
                new String[]{userName, password}, null, null, null, null);
        User user = null;
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            long user_id = cursor.getLong(cursor.getColumnIndex(Constants.ID_KEY));
            String userMail = cursor.getString(cursor.getColumnIndex(Constants.USER_MAIL_KEY));
            String userMobile = cursor.getString(cursor.getColumnIndex(Constants.USER_MOBILE_KEY));
            String name=cursor.getString(cursor.getColumnIndex(Constants.USER_NAME_KEY));
            String address=cursor.getString(cursor.getColumnIndex(Constants.USER_NAME_KEY));
            String profilePath=cursor.getString(cursor.getColumnIndex(Constants.USER_PROFILEPATH_KEY));



            user = new User();
            user.setUserID(user_id);
            user.setName(name);
            user.setPassword(password);
            user.setMail(userMail);
            user.setPhono(userMobile);
            user.setProfilePhoto(profilePath);
            user.setAddress(address);
            user.setUsername(userName);

        }

        db.close();
        return user ;
    }
    public Organization getOrganization(String userName, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.ORGANIZATION_TABLE_NAME;

        String[] columns = new String[]{Constants.ID_KEY,
                Constants.ORGANIZATION_NAME_KEY,
                Constants.ORGANIZATION_PASSWORD_KEY,
                Constants.ORGANIZATION_ADDRESS_KEY,
                Constants.ORGANIZATION_APPROVED_KEY,
                Constants.ORGANIZATION_DELETED_KEY,

                Constants.ORGANIZATION_MAIL_KEY,
                Constants.ORGANIZATION_PROFILEPATH_KEY,
                Constants.ORGANIZATION_MOBILE_KEY};

        String where = Constants.ORGANIZATION_USERNAME_KEY + " =?" + " AND " + Constants.ORGANIZATION_PASSWORD_KEY + " =?";

        Cursor cursor = db.query(table, columns, where,
                new String[]{userName, password}, null, null, null, null);
        Organization organization = null;
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            long organization_id = cursor.getLong(cursor.getColumnIndex(Constants.ID_KEY));
            String organizationMail = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_MAIL_KEY));
            String organizationMobile = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_MOBILE_KEY));
            String organizationname=cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_NAME_KEY));
            String address=cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_NAME_KEY));
            String profilePath=cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_PROFILEPATH_KEY));
            boolean isApproved = (cursor.getInt(cursor.getColumnIndex(Constants.ORGANIZATION_APPROVED_KEY)) ==1);
            boolean isDeleted = (cursor.getInt(cursor.getColumnIndex(Constants.ORGANIZATION_DELETED_KEY)) ==1);



            organization = new Organization();
            organization.setOrganizationID(organization_id);
            organization.setOrganizationname(organizationname);
            organization.setPassword(password);
            organization.setEmail(organizationMail);
            organization.setPhno(organizationMobile);
            organization.setProfilePath(profilePath);
            organization.setAddress(address);
            organization.setUsername(userName);
            organization.setApproved(isApproved);
            organization.setDeleted(isDeleted);


        }

        db.close();
        return organization ;
    }
    public Organization getOrganizationWithId(long organizationID)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Organization organization = null;

        String table = Constants.ORGANIZATION_TABLE_NAME;
        String[] tableColumns = new String[]{
                Constants.ORGANIZATION_NAME_KEY,
                Constants.ORGANIZATION_MAIL_KEY,
                Constants.ORGANIZATION_MOBILE_KEY,
                Constants.ORGANIZATION_USERNAME_KEY,
                Constants.ORGANIZATION_PASSWORD_KEY,
                Constants.ORGANIZATION_ADDRESS_KEY,
                Constants.ORGANIZATION_APPROVED_KEY,
                Constants.ORGANIZATION_DELETED_KEY,

                Constants.ORGANIZATION_PROFILEPATH_KEY};

        String where_clause = Constants.ID_KEY + " =?" ;

        Cursor cursor = db.query(table, tableColumns, where_clause,
                new String[]{String.valueOf(organizationID)}, null, null, null, null);

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            String name = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_NAME_KEY));
            String mail = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_MAIL_KEY));
            String mobile = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_MOBILE_KEY));
            String username = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_USERNAME_KEY));
            String password = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_PASSWORD_KEY));
            String profilePath = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_PROFILEPATH_KEY));
            String address = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_ADDRESS_KEY));
            boolean isApproved = (cursor.getInt(cursor.getColumnIndex(Constants.ORGANIZATION_APPROVED_KEY)) ==1);
            boolean isDeleted = (cursor.getInt(cursor.getColumnIndex(Constants.ORGANIZATION_DELETED_KEY)) ==1);



            organization = new Organization();
            organization.setOrganizationID(organizationID);
            organization.setOrganizationname(name);
            organization.setEmail(mail);
            organization.setPhno(mobile);
            organization.setUsername(username);
            organization.setPassword(password);
            organization.setProfilePath(profilePath);
            organization.setAddress(address);
            organization.setApproved(isApproved);
            organization.setDeleted(isDeleted);

            organization.setProfilePath(profilePath);

        }
        db.close();
        return organization;

    }

    public int updateOrganizationStatus(Organization organization){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.ORGANIZATION_APPROVED_KEY , organization.isApproved());

        // Updating row
        return db.update(Constants.ORGANIZATION_TABLE_NAME, values, Constants.ID_KEY + "=?",
                new String[]{String.valueOf(organization.getOrganizationID())});
    }

    public ArrayList<Organization> getRegisteredOrganizationList()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.ORGANIZATION_TABLE_NAME;
        String[] columns = new String[]{Constants.ID_KEY,
                Constants.ORGANIZATION_NAME_KEY,
                Constants.ORGANIZATION_MAIL_KEY,
                Constants.ORGANIZATION_MOBILE_KEY,
                Constants.ORGANIZATION_USERNAME_KEY,
                Constants.ORGANIZATION_PASSWORD_KEY,
                Constants.ORGANIZATION_APPROVED_KEY,
                Constants.ORGANIZATION_DELETED_KEY,

                Constants.ORGANIZATION_ADDRESS_KEY,
                Constants.ORGANIZATION_PROFILEPATH_KEY};
        String where_clause = Constants.ORGANIZATION_APPROVED_KEY + " =0";

        Cursor cursor = db.query(table, columns, where_clause, null, null, null, null, null);

        ArrayList<Organization> organizationList = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                long organizationID = Long.parseLong(cursor.getString(cursor.getColumnIndex(Constants.ID_KEY)));
                String name = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_NAME_KEY));
                String mail = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_MAIL_KEY));
                String mobile = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_MOBILE_KEY));
                String username = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_USERNAME_KEY));
                String password = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_PASSWORD_KEY));
                String address= cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_ADDRESS_KEY));
                boolean isApproved = (cursor.getInt(cursor.getColumnIndex(Constants.ORGANIZATION_APPROVED_KEY)) ==1);
                String profilePath = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_PROFILEPATH_KEY));
                boolean isDeleted = (cursor.getInt(cursor.getColumnIndex(Constants.ORGANIZATION_DELETED_KEY)) ==1);

                Organization organization = new Organization();
                organization.setOrganizationID(organizationID);
                organization.setOrganizationname(name);
                organization.setEmail(mail);
                organization.setPhno(mobile);
                organization.setUsername(username);
                organization.setPassword(password);
                organization.setAddress(address);
                organization.setApproved(isApproved);
                organization.setDeleted(isDeleted);

                organization.setProfilePath(profilePath);




                organizationList.add(organization);
            } while (cursor.moveToNext());
        }
        db.close();
        return organizationList;
    }


    public int updatePasswordForOrganization(String newPassword, long organization_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.ORGANIZATION_PASSWORD_KEY , newPassword);

        // Updating row
        return db.update(Constants.ORGANIZATION_TABLE_NAME, values, Constants.ID_KEY + "=?",
                new String[]{String.valueOf(organization_ID)});
    }

    public ArrayList<Organization> getApprovedOrganizationList()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String table = Constants.ORGANIZATION_TABLE_NAME;
        String[] columns = new String[]{Constants.ID_KEY,
                Constants.ORGANIZATION_NAME_KEY,
                Constants.ORGANIZATION_MAIL_KEY,
                Constants.ORGANIZATION_MOBILE_KEY,
                Constants.ORGANIZATION_USERNAME_KEY,
                Constants.ORGANIZATION_PASSWORD_KEY,
                Constants.ORGANIZATION_ADDRESS_KEY,
                Constants.ORGANIZATION_APPROVED_KEY,
                Constants.ORGANIZATION_DELETED_KEY,

                Constants.ORGANIZATION_PROFILEPATH_KEY};
        String where_clause = Constants.ORGANIZATION_APPROVED_KEY + " =1 AND " + Constants.ORGANIZATION_DELETED_KEY + " =0";
        Cursor cursor = db.query(table, columns, where_clause, null, null, null, null, null);
        ArrayList<Organization> organizationList = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                long organizationID = Long.parseLong(cursor.getString(cursor.getColumnIndex(Constants.ID_KEY)));
                String name = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_NAME_KEY));
                String mail = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_MAIL_KEY));
                String mobile = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_MOBILE_KEY));
                String username = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_USERNAME_KEY));
                String password = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_PASSWORD_KEY));
                String address = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_ADDRESS_KEY));
                String profilePath = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_PROFILEPATH_KEY));
                boolean isApproved = (cursor.getInt(cursor.getColumnIndex(Constants.ORGANIZATION_APPROVED_KEY)) ==1);
                boolean isDeleted = (cursor.getInt(cursor.getColumnIndex(Constants.ORGANIZATION_DELETED_KEY)) ==1);

                Organization organization = new Organization();
                organization.setOrganizationID(organizationID);
                organization.setOrganizationname(name);
                organization.setEmail(mail);
                organization.setPhno(mobile);
                organization.setUsername(username);
                organization.setPassword(password);
                organization.setAddress(address);
                organization.setApproved(isApproved);
                organization.setDeleted(isDeleted);

                organization.setProfilePath(profilePath);
                organizationList.add(organization);
            } while (cursor.moveToNext());
        }
        db.close();
        return organizationList;
    }

    public ArrayList<Organization> getDeletedOrganizationList()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String table = Constants.ORGANIZATION_TABLE_NAME;
        String[] columns = new String[]{Constants.ID_KEY,
                Constants.ORGANIZATION_NAME_KEY,
                Constants.ORGANIZATION_MAIL_KEY,
                Constants.ORGANIZATION_MOBILE_KEY,
                Constants.ORGANIZATION_USERNAME_KEY,
                Constants.ORGANIZATION_PASSWORD_KEY,
                Constants.ORGANIZATION_ADDRESS_KEY,
                Constants.ORGANIZATION_APPROVED_KEY,
                Constants.ORGANIZATION_DELETED_KEY,

                Constants.ORGANIZATION_PROFILEPATH_KEY};
        String where_clause = Constants.ORGANIZATION_APPROVED_KEY + " =1 AND " + Constants.ORGANIZATION_DELETED_KEY + " =1";
        Cursor cursor = db.query(table, columns, where_clause, null, null, null, null, null);
        ArrayList<Organization> organizationList = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                long organizationID = Long.parseLong(cursor.getString(cursor.getColumnIndex(Constants.ID_KEY)));
                String name = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_NAME_KEY));
                String mail = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_MAIL_KEY));
                String mobile = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_MOBILE_KEY));
                String username = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_USERNAME_KEY));
                String password = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_PASSWORD_KEY));
                String address = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_ADDRESS_KEY));
                String profilePath = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_PROFILEPATH_KEY));
                boolean isApproved = (cursor.getInt(cursor.getColumnIndex(Constants.ORGANIZATION_APPROVED_KEY)) ==1);
                boolean isDeleted = (cursor.getInt(cursor.getColumnIndex(Constants.ORGANIZATION_DELETED_KEY)) ==1);

                Organization organization = new Organization();
                organization.setOrganizationID(organizationID);
                organization.setOrganizationname(name);
                organization.setEmail(mail);
                organization.setPhno(mobile);
                organization.setUsername(username);
                organization.setPassword(password);
                organization.setAddress(address);
                organization.setApproved(isApproved);
                organization.setDeleted(isDeleted);

                organization.setProfilePath(profilePath);
                organizationList.add(organization);
            } while (cursor.moveToNext());
        }
        db.close();
        return organizationList;
    }

    public long addFeedback(Feedback feedback)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // Inserting Row to User table
        values.put(Constants.FEEDBACK_DESCRIPTION_KEY, feedback.getDescription());
        values.put(Constants.ORGANIZATION_ID_KEY, feedback.getOrganizationID());
        values.put(Constants.RESTAURANT_ID_KEY, feedback.getRestaurantID());

        long feedbackID = db.insert(Constants.FEEDBACK_TABLE_NAME, null, values);

        db.close();
        return feedbackID;
    }

    public long addDonation(Donation donation)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String date = dateFormatter.format(donation.getDate().getTime());

        // Inserting Row to Donation table
        values.put(Constants.DONATION_DATE_KEY, date);
        values.put(Constants.RESTAURANT_ID_KEY, donation.getRestaurantID());
        values.put(Constants.NUMBER_OF_PERSONS_KEY, donation.getNumberOfPersons());
        values.put(Constants.FOOD_TYPE_KEY, donation.getFoodType());

        long donationID = db.insert(Constants.DONATION_TABLE_NAME, null, values);

        db.close();
        return donationID;
    }

    public Feedback getFeedbackWithID(long feedbackID)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.FEEDBACK_TABLE_NAME;
        String[] columns = new String[]{
                Constants.ID_KEY,
                Constants.FEEDBACK_DESCRIPTION_KEY,
                Constants.ORGANIZATION_ID_KEY,
                Constants.RESTAURANT_ID_KEY};

        String where_clause = Constants.ID_KEY + " =?" ;

        Cursor cursor = db.query(table, columns, where_clause,
                new String[]{String.valueOf(feedbackID)}, null, null, null, null);

        Feedback feedback = null;
        if (cursor.moveToFirst()) {

            String description = cursor.getString(cursor.getColumnIndex(Constants.FEEDBACK_DESCRIPTION_KEY));
            long organizationID = cursor.getLong(cursor.getColumnIndex(Constants.ORGANIZATION_ID_KEY));
            long restaurantID = cursor.getLong(cursor.getColumnIndex(Constants.RESTAURANT_ID_KEY));

            feedback = new Feedback();
            feedback.setFeedbackID(feedbackID);
            feedback.setDescription(description);
            feedback.setRestaurantID(restaurantID);
            feedback.setOrganizationID(organizationID);
        }

        db.close();
        return feedback;

    }
    public ArrayList<Feedback> getFeedbackListForRestaurantWithID(long restaurantID)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.FEEDBACK_TABLE_NAME;
        String[] columns = new String[]{
                Constants.ID_KEY,
                Constants.FEEDBACK_DESCRIPTION_KEY,
                Constants.ORGANIZATION_ID_KEY,
                Constants.RESTAURANT_ID_KEY};
        String where_clause = Constants.RESTAURANT_ID_KEY + " =?" ;

        Cursor cursor = db.query(table, columns, where_clause,
                new String[]{String.valueOf(restaurantID)}, null, null, null, null);

        ArrayList<Feedback> feedbackList = new ArrayList<Feedback>();
        if (cursor.moveToFirst()) {
            do {
                long feedbackID = cursor.getLong(cursor.getColumnIndex(Constants.ID_KEY));
                String description = cursor.getString(cursor.getColumnIndex(Constants.FEEDBACK_DESCRIPTION_KEY));
                long organizationID = cursor.getLong(cursor.getColumnIndex(Constants.ORGANIZATION_ID_KEY));

                Feedback feedback = new Feedback();
                feedback.setFeedbackID(feedbackID);
                feedback.setDescription(description);
                feedback.setOrganizationID(organizationID);
                feedback.setRestaurantID(restaurantID);
                feedbackList.add(feedback);
            } while (cursor.moveToNext());
        }

        db.close();
        return feedbackList;
    }

    public Organization getOrganizationWithOrganizationName(String name)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.ORGANIZATION_TABLE_NAME;
        String[] columns = new String[]{
                Constants.ID_KEY,
                Constants.ORGANIZATION_NAME_KEY,
                Constants.ORGANIZATION_USERNAME_KEY,
                Constants.ORGANIZATION_ADDRESS_KEY,
                Constants.ORGANIZATION_PASSWORD_KEY,
                Constants.ORGANIZATION_MAIL_KEY,
                Constants.ORGANIZATION_APPROVED_KEY,
                Constants.ORGANIZATION_DELETED_KEY,

                Constants.ORGANIZATION_PROFILEPATH_KEY,
                Constants.ORGANIZATION_MOBILE_KEY};
        String where = Constants.ORGANIZATION_NAME_KEY + " =?" ;

        Cursor cursor = db.query(table, columns, where,
                new String[]{name}, null, null, null, null);
        Organization organization = null;
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            long organization_ID = cursor.getLong(cursor.getColumnIndex(Constants.ID_KEY));
            String organizationMail = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_MAIL_KEY));
            String address = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_ADDRESS_KEY));
            String password = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_PASSWORD_KEY));
            String profilePath=cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_PROFILEPATH_KEY));
            String organizationMobile = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_MOBILE_KEY));
            String username=cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_USERNAME_KEY));
            boolean isApproved = (cursor.getInt(cursor.getColumnIndex(Constants.ORGANIZATION_APPROVED_KEY)) ==1);
            boolean isDeleted = (cursor.getInt(cursor.getColumnIndex(Constants.ORGANIZATION_DELETED_KEY)) ==1);


            organization = new Organization();
            organization.setOrganizationID(organization_ID);
            organization.setOrganizationname(name);
            organization.setPassword(password);
            organization.setProfilePath(profilePath);
            organization.setEmail(organizationMail);
            organization.setPhno(organizationMobile);
            organization.setAddress(address);
            organization.setUsername(username);
            organization.setApproved(isApproved);
            organization.setDeleted(isDeleted);

        }

        db.close();
        return organization;
    }
    public void deleteOrganization(Organization organization)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.ORGANIZATION_DELETED_KEY , organization.isDeleted());

        // Updating row
        db.update(Constants.ORGANIZATION_TABLE_NAME, values, Constants.ID_KEY + "=?",
                new String[]{String.valueOf(organization.getOrganizationID())});

        db.close();
    }

    public void deleteRestaurant(Restaurant restaurant)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.RESTAURANT_DELETED_KEY , restaurant.isDeleted());

        // Updating row
        db.update(Constants.RESTAURANT_TABLE_NAME, values, Constants.ID_KEY + "=?",
                new String[]{String.valueOf(restaurant.getRestaurantID())});

        db.close();
    }

    public Organization getOrganizationWithUserName(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.ORGANIZATION_TABLE_NAME;
        String[] columns = new String[]{
                Constants.ID_KEY,
                Constants.ORGANIZATION_NAME_KEY,
                Constants.ORGANIZATION_NAME_KEY,
                Constants.ORGANIZATION_ADDRESS_KEY,
                Constants.ORGANIZATION_PASSWORD_KEY,
                Constants.ORGANIZATION_MAIL_KEY,
                Constants.ORGANIZATION_APPROVED_KEY,
                Constants.ORGANIZATION_DELETED_KEY,

                Constants.ORGANIZATION_PROFILEPATH_KEY,
                Constants.ORGANIZATION_MOBILE_KEY};
        String where = Constants.ORGANIZATION_USERNAME_KEY + " =?" ;

        Cursor cursor = db.query(table, columns, where,
                new String[]{username}, null, null, null, null);
        Organization organization = null;
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            long organization_ID = cursor.getLong(cursor.getColumnIndex(Constants.ID_KEY));
            String organizationMail = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_MAIL_KEY));
            String address = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_ADDRESS_KEY));
            String password = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_PASSWORD_KEY));
            String profilePath=cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_PROFILEPATH_KEY));
            String organizationMobile = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_MOBILE_KEY));
            String name=cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_NAME_KEY));
            boolean isApproved = (cursor.getInt(cursor.getColumnIndex(Constants.ORGANIZATION_APPROVED_KEY)) ==1);
            boolean isDeleted = (cursor.getInt(cursor.getColumnIndex(Constants.ORGANIZATION_DELETED_KEY)) ==1);


            organization = new Organization();
            organization.setOrganizationID(organization_ID);
            organization.setOrganizationname(name);
            organization.setPassword(password);
            organization.setProfilePath(profilePath);
            organization.setEmail(organizationMail);
            organization.setPhno(organizationMobile);
            organization.setAddress(address);
            organization.setUsername(username);
            organization.setApproved(isApproved);
            organization.setDeleted(isDeleted);

        }

        db.close();
        return organization;
    }

    // This method returns the list of all the organization usernames in the database. It is used to check if a newly entered user name
    // already exists in the database.
    public ArrayList<String> getAllOrganizationUserNames()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.ORGANIZATION_TABLE_NAME;
        String[] columns = new String[]{Constants.ORGANIZATION_USERNAME_KEY};

        Cursor cursor = db.query(table, columns, null,
                null, null, null, null, null);

        ArrayList<String> organizationUserNames = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                String username = cursor.getString(cursor.getColumnIndex(Constants.ORGANIZATION_USERNAME_KEY));
                organizationUserNames.add(username);
            } while (cursor.moveToNext());
        }

        db.close();
        return organizationUserNames;
    }

    public Restaurant getRestaurantWithRestaurantName(String name)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.RESTAURANT_TABLE_NAME;
        String[] columns = new String[]{
                Constants.ID_KEY,
                Constants.RESTAURANT_NAME_KEY,
                Constants.RESTAURANT_USERNAME_KEY,
                Constants.RESTAURANT_ADDRESS_KEY,
                Constants.RESTAURANT_PASSWORD_KEY,
                Constants.RESTAURANT_EMAIL_KEY,
                Constants.RESTAURANT_MOBILE_KEY,
                Constants.RESTAURANT_APPROVED_KEY,
                Constants.RESTAURANT_DELETED_KEY,

                Constants.RESTAURANT_TYPE_KEY,
                Constants.RESTAURANT_PROFILE_PATH_KEY
                };
        String where = Constants.RESTAURANT_NAME_KEY + " =?" ;

        Cursor cursor = db.query(table, columns, where,
                new String[]{name}, null, null, null, null);
        Restaurant restaurant = null;
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            long restaurant_ID = cursor.getLong(cursor.getColumnIndex(Constants.ID_KEY));
            String mail = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_EMAIL_KEY));
            String address = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_ADDRESS_KEY));
            String password = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_PASSWORD_KEY));
            String profilePath=cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_PROFILE_PATH_KEY));
            String mobile = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_MOBILE_KEY));
            String username=cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_USERNAME_KEY));
            String type=cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_TYPE_KEY));
            boolean isApproved = (cursor.getInt(cursor.getColumnIndex(Constants.RESTAURANT_APPROVED_KEY)) ==1);
            boolean isDeleted = (cursor.getInt(cursor.getColumnIndex(Constants.RESTAURANT_DELETED_KEY)) ==1);


            restaurant = new Restaurant();
            restaurant.setRestaurantID(restaurant_ID);
            restaurant.setName(name);
            restaurant.setPassword(password);
            restaurant.setProfilePath(profilePath);
            restaurant.setMail(mail);
            restaurant.setPhno(mobile);
            restaurant.setAddress(address);
            restaurant.setUserName(username);
            restaurant.setType(type);
            restaurant.setApproved(isApproved);
            restaurant.setDeleted(isDeleted);

        }

        db.close();
        return restaurant;
    }


    public User getUserWithUsername(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.USER_TABLE_NAME;
        String[] columns = new String[]{
                Constants.ID_KEY,
                Constants.USER_NAME_KEY,
                Constants.USER_USERNAME_KEY,
                Constants.USER_ADDRESS_KEY,
                Constants.USER_PASSWORD_KEY,
                Constants.USER_MAIL_KEY,
                Constants.USER_MOBILE_KEY,
                Constants.USER_PROFILEPATH_KEY
        };
        String where = Constants.USER_USERNAME_KEY + " =?" ;

        Cursor cursor = db.query(table, columns, where,
                new String[]{username}, null, null, null, null);
        User user = null;
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            long userID = cursor.getLong(cursor.getColumnIndex(Constants.ID_KEY));
            String mail = cursor.getString(cursor.getColumnIndex(Constants.USER_MAIL_KEY));
            String address = cursor.getString(cursor.getColumnIndex(Constants.USER_ADDRESS_KEY));
            String password = cursor.getString(cursor.getColumnIndex(Constants.USER_PASSWORD_KEY));
            String profilePath=cursor.getString(cursor.getColumnIndex(Constants.USER_PROFILEPATH_KEY));
            String mobile = cursor.getString(cursor.getColumnIndex(Constants.USER_MOBILE_KEY));
            String name=cursor.getString(cursor.getColumnIndex(Constants.USER_NAME_KEY));


            user = new User();
            user.setUserID(userID);
            user.setName(name);
            user.setPassword(password);
            user.setProfilePhoto(profilePath);
            user.setMail(mail);
            user.setPhono(mobile);
            user.setAddress(address);
            user.setUsername(username);

        }

        db.close();
        return user;
    }

    public Restaurant getRestaurantWithUsername(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.RESTAURANT_TABLE_NAME;
        String[] columns = new String[]{
                Constants.ID_KEY,
                Constants.RESTAURANT_NAME_KEY,
                Constants.RESTAURANT_USERNAME_KEY,
                Constants.RESTAURANT_ADDRESS_KEY,
                Constants.RESTAURANT_PASSWORD_KEY,
                Constants.RESTAURANT_EMAIL_KEY,
                Constants.RESTAURANT_MOBILE_KEY,
                Constants.RESTAURANT_APPROVED_KEY,
                Constants.RESTAURANT_DELETED_KEY,

                Constants.RESTAURANT_TYPE_KEY,
                Constants.RESTAURANT_PROFILE_PATH_KEY
        };
        String where = Constants.RESTAURANT_USERNAME_KEY + " =?" ;

        Cursor cursor = db.query(table, columns, where,
                new String[]{username}, null, null, null, null);
        Restaurant restaurant = null;
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            long restaurant_ID = cursor.getLong(cursor.getColumnIndex(Constants.ID_KEY));
            String mail = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_EMAIL_KEY));
            String address = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_ADDRESS_KEY));
            String password = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_PASSWORD_KEY));
            String profilePath=cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_PROFILE_PATH_KEY));
            String mobile = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_MOBILE_KEY));
            String type=cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_TYPE_KEY));
            boolean isApproved = (cursor.getInt(cursor.getColumnIndex(Constants.RESTAURANT_APPROVED_KEY)) ==1);
            String name=cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_NAME_KEY));
            boolean isDeleted = (cursor.getInt(cursor.getColumnIndex(Constants.RESTAURANT_DELETED_KEY)) ==1);


            restaurant = new Restaurant();
            restaurant.setRestaurantID(restaurant_ID);
            restaurant.setName(name);
            restaurant.setPassword(password);
            restaurant.setProfilePath(profilePath);
            restaurant.setMail(mail);
            restaurant.setPhno(mobile);
            restaurant.setAddress(address);
            restaurant.setUserName(username);
            restaurant.setType(type);
            restaurant.setApproved(isApproved);
            restaurant.setDeleted(isDeleted);

        }

        db.close();
        return restaurant;
    }
    public void deleteRestaurantWithName(String restaurantname)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.RESTAURANT_TABLE_NAME, Constants.RESTAURANT_NAME_KEY + "=?",
                new String[]{restaurantname});
        db.close();
    }
    public Restaurant getRestaurantWithId(long restaurantID)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.RESTAURANT_TABLE_NAME;
        String[] columns = new String[]{
                Constants.RESTAURANT_NAME_KEY,
                Constants.RESTAURANT_EMAIL_KEY,
                Constants.RESTAURANT_MOBILE_KEY,
                Constants.RESTAURANT_USERNAME_KEY,
                Constants.RESTAURANT_PASSWORD_KEY,
                Constants.RESTAURANT_APPROVED_KEY,
                Constants.RESTAURANT_DELETED_KEY,

                Constants.RESTAURANT_TYPE_KEY,
                Constants.RESTAURANT_ADDRESS_KEY,
                Constants.RESTAURANT_PROFILE_PATH_KEY};

        String where_clause = Constants.ID_KEY + " =?";

        Cursor cursor = db.query(table, columns, where_clause, new String[]{String.valueOf(restaurantID)}, null, null, null, null);

        Restaurant restaurant = null;
        if (cursor.moveToFirst()) {

            String name = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_NAME_KEY));
            String mail = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_EMAIL_KEY));
            String mobile = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_MOBILE_KEY));
            String username = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_USERNAME_KEY));
            String password = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_PASSWORD_KEY));
            String profilePath = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_PROFILE_PATH_KEY));
            String address = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_ADDRESS_KEY));
            String type = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_TYPE_KEY));
            boolean isApproved = (cursor.getInt(cursor.getColumnIndex(Constants.RESTAURANT_APPROVED_KEY)) ==1);
            boolean isDeleted = (cursor.getInt(cursor.getColumnIndex(Constants.RESTAURANT_DELETED_KEY)) ==1);

            restaurant = new Restaurant();
            restaurant.setRestaurantID(restaurantID);
            restaurant.setName(name);
            restaurant.setMail(mail);
            restaurant.setPhno(mobile);
            restaurant.setUserName(username);
            restaurant.setPassword(password);
            restaurant.setProfilePath(profilePath);
            restaurant.setAddress(address);
            restaurant.setType(type);
            restaurant.setApproved(isApproved);
            restaurant.setDeleted(isDeleted);

        }
        db.close();
        return restaurant;
    }
    public int updateRestaurantStatus(Restaurant restaurant){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.RESTAURANT_APPROVED_KEY , restaurant.isApproved());

        // Updating row
        return db.update(Constants.RESTAURANT_TABLE_NAME, values, Constants.ID_KEY + "=?",
                new String[]{String.valueOf(restaurant.getRestaurantID())});
    }
    public Restaurant getRestaurant(String username, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.RESTAURANT_TABLE_NAME;
        String[] columns = new String[]{
                Constants.ID_KEY,
                Constants.RESTAURANT_NAME_KEY,
                Constants.RESTAURANT_EMAIL_KEY,
                Constants.RESTAURANT_MOBILE_KEY,
                Constants.RESTAURANT_ADDRESS_KEY,
                Constants.RESTAURANT_APPROVED_KEY,
                Constants.RESTAURANT_DELETED_KEY,

                Constants.RESTAURANT_TYPE_KEY,
                Constants.RESTAURANT_PROFILE_PATH_KEY};

        String where = Constants.USER_USERNAME_KEY + " =?" + " AND " + Constants.USER_PASSWORD_KEY + " =?";

        Cursor cursor = db.query(table, columns, where, new String[]{username,password}, null, null, null, null);

        Restaurant restaurant = null;
        if (cursor.moveToFirst()) {

            String name = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_NAME_KEY));
            String mail = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_EMAIL_KEY));
            String mobile = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_MOBILE_KEY));
            String profilePath = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_PROFILE_PATH_KEY));
            String address = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_ADDRESS_KEY));
            long restaurantID = Long.parseLong(cursor.getString(cursor.getColumnIndex(Constants.ID_KEY)));
            String type = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_TYPE_KEY));
            boolean isApproved = (cursor.getInt(cursor.getColumnIndex(Constants.RESTAURANT_APPROVED_KEY)) ==1);
            boolean isDeleted = (cursor.getInt(cursor.getColumnIndex(Constants.RESTAURANT_DELETED_KEY)) ==1);

            restaurant = new Restaurant();
            restaurant.setRestaurantID(restaurantID);
            restaurant.setName(name);
            restaurant.setMail(mail);
            restaurant.setPhno(mobile);
            restaurant.setUserName(username);
            restaurant.setPassword(password);
            restaurant.setProfilePath(profilePath);
            restaurant.setAddress(address);
            restaurant.setType(type);
            restaurant.setApproved(isApproved);
            restaurant.setDeleted(isDeleted);

        }
        db.close();
        return restaurant;

    }
    public ArrayList<String> getAllUserNames()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.USER_TABLE_NAME;
        String[] columns = new String[]{Constants.USER_USERNAME_KEY};

        Cursor cursor = db.query(table, columns, null,
                null, null, null, null, null);

        ArrayList<String> userNames = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(Constants.USER_USERNAME_KEY));
                userNames.add(name);
            } while (cursor.moveToNext());
        }

        db.close();
        return userNames;
    }

    public ArrayList<Restaurant> getRegisteredRestaurantList()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.RESTAURANT_TABLE_NAME;
        String[] columns = new String[]{Constants.ID_KEY,
                Constants.RESTAURANT_NAME_KEY,
                Constants.RESTAURANT_EMAIL_KEY,
                Constants.RESTAURANT_MOBILE_KEY,
                Constants.RESTAURANT_USERNAME_KEY,
                Constants.RESTAURANT_PASSWORD_KEY,
                Constants.RESTAURANT_ADDRESS_KEY,
                Constants.RESTAURANT_APPROVED_KEY,
                Constants.RESTAURANT_TYPE_KEY,
                Constants.RESTAURANT_DELETED_KEY,

                Constants.RESTAURANT_PROFILE_PATH_KEY};

        String where_clause = Constants.RESTAURANT_APPROVED_KEY + " =0";

        Cursor cursor = db.query(table, columns, where_clause, null, null, null, null, null);

        ArrayList<Restaurant> restaurantList = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                long restaurantID = Long.parseLong(cursor.getString(cursor.getColumnIndex(Constants.ID_KEY)));
                String name = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_NAME_KEY));
                String mail = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_EMAIL_KEY));
                String mobile = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_MOBILE_KEY));
                String username = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_USERNAME_KEY));
                String password = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_PASSWORD_KEY));
                String profilePath = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_PROFILE_PATH_KEY));
                String address = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_ADDRESS_KEY));
                boolean isApproved = (cursor.getInt(cursor.getColumnIndex(Constants.RESTAURANT_APPROVED_KEY)) ==1);
                String type = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_TYPE_KEY));
                boolean isDeleted = (cursor.getInt(cursor.getColumnIndex(Constants.RESTAURANT_DELETED_KEY)) ==1);

                Restaurant restaurant = new Restaurant();
                restaurant.setRestaurantID(restaurantID);
                restaurant.setName(name);
                restaurant.setMail(mail);
                restaurant.setPhno(mobile);
                restaurant.setUserName(username);
                restaurant.setPassword(password);
                restaurant.setProfilePath(profilePath);
                restaurant.setAddress(address);
                restaurant.setApproved(isApproved);
                restaurant.setDeleted(isDeleted);

                restaurant.setType(type);

                restaurantList.add(restaurant);
            } while (cursor.moveToNext());
        }
        db.close();
        return restaurantList;
    }

    public ArrayList<Restaurant> getDeletedRestaurantList()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.RESTAURANT_TABLE_NAME;
        String[] columns = new String[]{Constants.ID_KEY,
                Constants.RESTAURANT_NAME_KEY,
                Constants.RESTAURANT_EMAIL_KEY,
                Constants.RESTAURANT_MOBILE_KEY,
                Constants.RESTAURANT_USERNAME_KEY,
                Constants.RESTAURANT_PASSWORD_KEY,
                Constants.RESTAURANT_ADDRESS_KEY,
                Constants.RESTAURANT_APPROVED_KEY,
                Constants.RESTAURANT_TYPE_KEY,
                Constants.RESTAURANT_PROFILE_PATH_KEY};

        String where_clause = Constants.RESTAURANT_APPROVED_KEY + " =1 AND " + Constants.RESTAURANT_DELETED_KEY + " =1";

        Cursor cursor = db.query(table, columns, where_clause, null, null, null, null, null);

        ArrayList<Restaurant> restaurantList = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                long restaurantID = Long.parseLong(cursor.getString(cursor.getColumnIndex(Constants.ID_KEY)));
                String name = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_NAME_KEY));
                String mail = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_EMAIL_KEY));
                String mobile = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_MOBILE_KEY));
                String username = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_USERNAME_KEY));
                String password = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_PASSWORD_KEY));
                String profilePath = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_PROFILE_PATH_KEY));
                String address = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_ADDRESS_KEY));
                boolean isApproved = (cursor.getInt(cursor.getColumnIndex(Constants.RESTAURANT_APPROVED_KEY)) ==1);
                String type = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_TYPE_KEY));

                Restaurant restaurant = new Restaurant();
                restaurant.setRestaurantID(restaurantID);
                restaurant.setName(name);
                restaurant.setMail(mail);
                restaurant.setPhno(mobile);
                restaurant.setUserName(username);
                restaurant.setPassword(password);
                restaurant.setProfilePath(profilePath);
                restaurant.setAddress(address);
                restaurant.setApproved(isApproved);
                restaurant.setDeleted(true);

                restaurant.setType(type);


                restaurantList.add(restaurant);
            } while (cursor.moveToNext());
        }
        db.close();
        return restaurantList;
    }

    public ArrayList<Restaurant> getApprovedRestaurantList()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.RESTAURANT_TABLE_NAME;
        String[] columns = new String[]{Constants.ID_KEY,
                Constants.RESTAURANT_NAME_KEY,
                Constants.RESTAURANT_EMAIL_KEY,
                Constants.RESTAURANT_MOBILE_KEY,
                Constants.RESTAURANT_USERNAME_KEY,
                Constants.RESTAURANT_PASSWORD_KEY,
                Constants.RESTAURANT_ADDRESS_KEY,
                Constants.RESTAURANT_APPROVED_KEY,
                Constants.RESTAURANT_DELETED_KEY,
                Constants.RESTAURANT_TYPE_KEY,
                Constants.RESTAURANT_PROFILE_PATH_KEY};

        String where_clause = Constants.RESTAURANT_APPROVED_KEY + " =1 AND " + Constants.RESTAURANT_DELETED_KEY + " =0";

        Cursor cursor = db.query(table, columns, where_clause, null, null, null, null, null);

        ArrayList<Restaurant> restaurantList = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                long restaurantID = Long.parseLong(cursor.getString(cursor.getColumnIndex(Constants.ID_KEY)));
                String name = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_NAME_KEY));
                String mail = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_EMAIL_KEY));
                String mobile = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_MOBILE_KEY));
                String username = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_USERNAME_KEY));
                String password = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_PASSWORD_KEY));
                String profilePath = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_PROFILE_PATH_KEY));
                String address = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_ADDRESS_KEY));
                boolean isApproved = (cursor.getInt(cursor.getColumnIndex(Constants.RESTAURANT_APPROVED_KEY)) ==1);
                boolean isDeleted = (cursor.getInt(cursor.getColumnIndex(Constants.RESTAURANT_DELETED_KEY)) ==1);

                String type = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_TYPE_KEY));

                Restaurant restaurant = new Restaurant();
                restaurant.setRestaurantID(restaurantID);
                restaurant.setName(name);
                restaurant.setMail(mail);
                restaurant.setPhno(mobile);
                restaurant.setUserName(username);
                restaurant.setPassword(password);
                restaurant.setProfilePath(profilePath);
                restaurant.setAddress(address);
                restaurant.setApproved(isApproved);
                restaurant.setDeleted(isDeleted);

                restaurant.setType(type);


                restaurantList.add(restaurant);
            } while (cursor.moveToNext());
        }
        db.close();
        return restaurantList;
    }
    public long updateDetailsForUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // Inserting Row to User table
        values.put(Constants.USER_PASSWORD_KEY, user.getPassword());
        values.put(Constants.USER_MAIL_KEY, user.getMail());
        values.put(Constants.USER_MOBILE_KEY, user.getPhono());
        values.put(Constants.USER_PROFILEPATH_KEY, user.getProfilePhoto());
        values.put(Constants.USER_ADDRESS_KEY, user.getAddress());

        long userID = db.update(Constants.USER_TABLE_NAME, values, Constants.ID_KEY + "=?",
                new String[]{String.valueOf(user.getUserID())});

        db.close();
        return userID;
    }



    public long updateDetailsForRestaurant(Restaurant restaurant)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // Inserting Row to User table
        values.put(Constants.RESTAURANT_NAME_KEY, restaurant.getName());
        values.put(Constants.RESTAURANT_PASSWORD_KEY, restaurant.getPassword());
        values.put(Constants.RESTAURANT_EMAIL_KEY, restaurant.getMail());
        values.put(Constants.RESTAURANT_MOBILE_KEY, restaurant.getPhno());
        values.put(Constants.RESTAURANT_PROFILE_PATH_KEY, restaurant.getProfilePath());
        values.put(Constants.RESTAURANT_ADDRESS_KEY, restaurant.getAddress());

        long restaurantID = db.update(Constants.RESTAURANT_TABLE_NAME, values, Constants.ID_KEY + "=?",
                new String[]{String.valueOf(restaurant.getRestaurantID())});

        db.close();
        return restaurantID;
    }

    public long updateDetailsForOrganization(Organization organization)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Constants.ORGANIZATION_PASSWORD_KEY, organization.getPassword());
        values.put(Constants.ORGANIZATION_MAIL_KEY, organization.getEmail());
        values.put(Constants.ORGANIZATION_MOBILE_KEY, organization.getPhno());
        values.put(Constants.ORGANIZATION_PROFILEPATH_KEY, organization.getProfilePath());
        values.put(Constants.ORGANIZATION_ADDRESS_KEY, organization.getAddress());

        long organizationID = db.update(Constants.ORGANIZATION_TABLE_NAME, values, Constants.ID_KEY + "=?",
                new String[]{String.valueOf(organization.getOrganizationID())});

        db.close();
        return organizationID;
    }

    public int updatePasswordForRestaurant(String newPassword, long restaurantID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.RESTAURANT_PASSWORD_KEY , newPassword);

        // Updating row
        return db.update(Constants.RESTAURANT_TABLE_NAME, values, Constants.ID_KEY + "=?",
                new String[]{String.valueOf(restaurantID)});
    }
    public long addRestaurant(Restaurant restaurant)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // Inserting Row to Restaurant table
        values.put(Constants.RESTAURANT_NAME_KEY, restaurant.getName());
        values.put(Constants.RESTAURANT_USERNAME_KEY, restaurant.getUserName());
        values.put(Constants.RESTAURANT_PASSWORD_KEY, restaurant.getPassword());
        values.put(Constants.RESTAURANT_EMAIL_KEY, restaurant.getMail());
        values.put(Constants.RESTAURANT_MOBILE_KEY, restaurant.getPhno());
        values.put(Constants.RESTAURANT_PROFILE_PATH_KEY, restaurant.getProfilePath());
        values.put(Constants.RESTAURANT_ADDRESS_KEY, restaurant.getAddress());
        values.put(Constants.RESTAURANT_APPROVED_KEY, restaurant.isApproved());
        values.put(Constants.RESTAURANT_DELETED_KEY, restaurant.isDeleted());

        values.put(Constants.RESTAURANT_TYPE_KEY, restaurant.getType());

        long restaurantID = db.insert(Constants.RESTAURANT_TABLE_NAME, null, values);

        db.close();
        return restaurantID;
    }

    public ArrayList<String> getAllRestaurantUserNames()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String table = Constants.RESTAURANT_TABLE_NAME;
        String[] columns = new String[]{Constants.RESTAURANT_USERNAME_KEY};

        Cursor cursor = db.query(table, columns, null,
                null, null, null, null, null);

        ArrayList<String> restaurantUserNames = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                String username = cursor.getString(cursor.getColumnIndex(Constants.RESTAURANT_USERNAME_KEY));
                restaurantUserNames.add(username);
            } while (cursor.moveToNext());
        }

        db.close();
        return restaurantUserNames;
    }

    public ArrayList<Donation> getDonationsForRestaurantWithID(long restaurantID)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String table = Constants.DONATION_TABLE_NAME;
        String[] columns = new String[]{Constants.ID_KEY,
                    Constants.NUMBER_OF_PERSONS_KEY,
                    Constants.DONATION_DATE_KEY,
                    Constants.FOOD_TYPE_KEY};

        Cursor cursor = db.query(table, columns, null,
                null, null, null, null, null);
        ArrayList<Donation> donations = new ArrayList<Donation>();
        if (cursor.moveToFirst()) {
            do {
                String foodType = cursor.getString(cursor.getColumnIndex(Constants.FOOD_TYPE_KEY));
                int numberOfPersons = cursor.getInt(cursor.getColumnIndex(Constants.NUMBER_OF_PERSONS_KEY));
                long donationID = Long.parseLong(cursor.getString(cursor.getColumnIndex(Constants.ID_KEY)));
                String dateString = cursor.getString(cursor.getColumnIndex(Constants.DONATION_DATE_KEY));
                Calendar date = Utility.stringToDate(dateString);

                Donation donation = new Donation();
                donation.setId(donationID);
                donation.setRestaurantID(restaurantID);
                donation.setNumberOfPersons(numberOfPersons);
                donation.setFoodType(foodType);
                donation.setDate(date);

                donations.add(donation);
            } while (cursor.moveToNext());
        }

        db.close();
        return donations;

    }
}


