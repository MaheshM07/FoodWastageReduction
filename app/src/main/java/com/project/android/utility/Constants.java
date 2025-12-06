package com.project.android.utility;

public class Constants { // Error Messages
    public static final String MISSING_NAME = "Please enter name";
    public static final String MISSING_PASSWORD = "Please enter your password";
    public static final String INVALID_ADMIN_CREDENTIALS = "Wrong Credentials";
    public static final String INVALID_USER = "Wrong Credentials";
    public static final String MISSING_MAIL = "Please enter mail";
    public static final String MISSING_PASSWORD_CONFIRMATION = "Please confirm your password";
    public static final String PASSWORD_MISMATCH = "Password does not match";
    public static final String INVALID_MOBILE = "Mobile number should be 10 digit number";
    // App Description
    public static final String APP_DESCRIPTION = "Food wastage reduction through donation using Android is a mobile application that provides a platform for donating leftover food to all needy organizations. Instead of wasting food we can put it to use by donating it to various organizations such as orphanages, old age homes, etc. The app aims at charity through donations.";

    // Database version
    public static final int DATABASE_VERSION = 1;

    // Database name
    public static final String DATABASE_NAME = "FoodWastageReductionThroughDonation";


    // Common column names
    public static final String ID_KEY = "_id";



    public static final String DRAWABLE_RESOURCE = "drawable";



    //organization table
    public static final String ORGANIZATION_TABLE_NAME ="Organization_Table" ;
    public static final String ORGANIZATION_NAME_KEY ="Name" ;
    public static final String ORGANIZATION_PASSWORD_KEY ="Password" ;
    public static final String ORGANIZATION_MAIL_KEY ="Mail";
    public static final String ORGANIZATION_MOBILE_KEY = "MblNo";
    public static final String ORGANIZATION_ADDRESS_KEY ="Address" ;
    public static final String ORGANIZATION_PROFILEPATH_KEY ="ProfilePath" ;
    public static final String ORGANIZATION_USERNAME_KEY ="Username" ;
    public static final String ORGANIZATION_APPROVED_KEY ="Approval" ;
    public static final String ORGANIZATION_DELETED_KEY ="Deleted" ;

    //Donation
    public static final String DONATION_TABLE_NAME ="Donation" ;
    public static final String FOOD_TYPE_KEY ="FoodType" ;
    public static final String DONATION_DATE_KEY ="Date" ;
    public static final String NUMBER_OF_PERSONS_KEY ="NoOfPersons" ;


    public static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    public static final String FORGOT_PASSWORD ="The registered password has been sent to your registered mobile number" ;
    public static final String INVALID_CREDENTIALS = "Wrong Credentials";


    public static final String MISSING_MOBILE ="Please enter your Mbl no" ;
    public static final String MISSING_USERNAME ="Please enter username" ;
    public static final String INVALID_EMAIL = "Please enter valid Email";
    public static final String MISSING_EMAIL="Please enter your  Mail";
    public static final String MISSING_AGE ="Please enter your age";
    public static final String INVALID_PASSWORD ="Please enter valid password" ;
    public static final int MINIMUM_PASSWORD_LENGTH = 8;
    public static final int REQUEST_CAMERA =123 ;
    public static final int SELECT_FILE =234 ;

    public static final String ADMIN_PASSWORD ="admin" ;
    public static final String ADMIN_PASSWORD_KEY ="password" ;
    public static final String ADMIN_MOBILE = "";


    public static final String FEEDBACK_SUBJECT = "Food Wastage Reduction Through Donation Feedback ";
    public static final String FEEDBACK_MAILID = "foodwastagereductionfeedback@gmail.com";
    public static final String HELP_MESSAGE = "Please mail to \nfoodwastagereductionfeedback@gmail.com\nfor any help regarding the app.";

    public static final String NO_ORGANIZATION_WITH_NAME = "Organization with that Name does not exist";
    public static final String ORGANIZATION_APPROVED_SUCCESSFULLY = "Organization is approved successfully by Admin";
    public static final String ORGANIZATION_NOT_APPROVED = "Login is not possible till the Organization is approved by the admin.";

    public static final String MISSING_OLD_PASSWORD = "Please enter your old password";
    public static final String OLD_PASSWORD_INCORRECT = "Your old password is incorrect";
    public static final String MISSING_NEW_PASSWORD = "Please enter your new password";
    public static final String PASSWORD_CHANGED_SUCCESSFULLY = "Password is changed successfully";

    public static final String MISSING_ADDRESS ="Please enter your address" ;
    public static final String MISSING_ORGANIZATION_PROFILEPHOTO ="Select Organization profile picture" ;
    public static final String ORGANIZATION_REGISTERED_SUCCESSFULLY ="Organization is registered successfully. Please wait for approval from Admin" ;

    public static final String NOREGISTEREDORGANIZATIONS_DESCRIPTION = "Currently there are no registered organizations";
    public static final String FEEDBACK_TABLE_NAME = "Feedback";
    public static final String FEEDBACK_DESCRIPTION_KEY = "Feedback_Decription";
    public static final String RESTAURANT_ID_KEY = "restaurant_id";
    public static final String ORGANIZATION_ID_KEY = "organization_id";
    public static final String NOFEEDBACK_DESCRIPTION = "There is no feedback by any of the charity organizations";

    public static final String NO_ORGANIZATION_TO_DELETE ="There are no organizations to delete" ;
    public static final String ORGANIZATION_DELETED_SUCCESSFULLY ="Organization is deleted Successfully" ;
    public static final String INVALID_MAIL = "Please enter valid mail";

    public static final String NOAPPROVEDORGANIZATIONS_DESCRIPTION = "Currently there are no approved organizations";
    public static final String UNREGISTERED_ORGANIZATIONNAME = "You are not registered with the app. Please register to continue";
    public static final String DUPLICATE_USERNAME = "Username already exists. Please select a different username";
    public static final String NO_REGISTERED_RESTAURANTS_DESCRIPTION = "There are no registered restaurants";



    public static final String NO_RESTAURANT_TO_DELETE ="There are no restaurants to delete" ;
    public static final String RESTAURANT_DELETED_SUCCESSFULLY ="Restaurant is deleted successfully" ;

    public static final String NO_USER = "User does not exist";

    public static final String RESTAURANT_TABLE_NAME = "Restaurant";
    // Restaurant Table
    public static final String RESTAURANT_NAME_KEY = "name";
    public static final String RESTAURANT_PASSWORD_KEY = "password";
    public static final String RESTAURANT_EMAIL_KEY = "email";
    public static final String RESTAURANT_MOBILE_KEY = "mobile";
    public static final String RESTAURANT_USERNAME_KEY = "username";
    public static final String RESTAURANT_PROFILE_PATH_KEY = "profile_path";
    public static final String RESTAURANT_ADDRESS_KEY = "address";
    public static final String RESTAURANT_APPROVED_KEY = "approved";
    public static final String RESTAURANT_TYPE_KEY = "type";
    public static final String RESTAURANT_DELETED_KEY = "deleted";

    public static final String NO_RESTAURANT_WITH_NAME = "Restaurant with that Name does not exist";
    public static final String RESTAURANT_APPROVED_SUCCESSFULLY = "Restaurant is approved successfully by Admin";
    public static final String RESTAURANT_NOT_APPROVED = "Login is not possible till the Restaurant is approved by the admin.";


    //user
    public static final String USER_REGISTERED_SUCCESSFULLY ="User is registered successfully" ;
    public static final String MISSING_USER_PROFILEPHOTO ="Select User profile picture" ;
    public static final String USER_TABLE_NAME ="User_Table" ;
    public static final String USER_NAME_KEY ="Name" ;
    public static final String USER_PASSWORD_KEY ="Password" ;
    public static final String USER_MAIL_KEY ="Mail";
    public static final String USER_MOBILE_KEY = "MblNo";
    public static final String USER_ADDRESS_KEY ="Address" ;
    public static final String USER_PROFILEPATH_KEY ="ProfilePath" ;
    public static final String USER_USERNAME_KEY ="Username" ;

    public static final String RESTAURANT_EDITED_SUCCESSFULLY = "Restaurant details are edited successfullly";
    public static final String USER_EDITED_SUCCESSFULLY = "User details are edited successfullly";
    public static final String RESTAURANT_REGISTERED_SUCCESSFULLY = "Restaurant is registered successfully. Please wait for approval from Admin" ;
    public static final String MISSING_FEEDBACK_DESCRIPTION = "Please enter feedback description";
    public static final String FEEDBACK_POSTED_SUCCESSSFULLY = "Feedback is posted successfully";
    public static final String INVALID_RESTAURANT = "Restaurant is not registered with the app";
    public static final String NO_RESTAURANTS_FOR_FEEDBACK = "There are no restaurants to give feedback";
    public static final String MISSING_HOURS_AVAILABLE = "Please enter the number of hours for which food will be available";
    public static final String SMS_SENT_TO_ORGANIZATIONS = "SMS about food availability is sent to all the approved organizations";
    public static final String NODELETEDORGANIZATIONS_DESCRIPTION = "There are no deleted organizations";
    public static final String NODELETEDRESTAURANTS_DESCRIPTION = "There are no deleted restaurants";

    public static final String NO_DONATIONS_DESCRIPTION = "This restaurant has not donated any food";
    public static final String NO_APPROVED_RESTAURANTS_DESCRIPTION = "There are no approved restaurants";
    public static final String RESTAURANT_DELETED = "This restaurant is deleted by Admin";
    public static final String ORGANIZATION_DELETED = "This organization is deleted by Admin";
}
