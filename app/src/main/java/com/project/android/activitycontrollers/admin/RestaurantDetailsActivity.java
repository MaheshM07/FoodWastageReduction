package com.project.android.activitycontrollers.admin;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;

import com.project.android.R;
import com.project.android.activitycontrollers.UserSelectionActivity;
import com.project.android.database.AppDatabaseHelper;
import com.project.android.model.Restaurant;
import com.project.android.utility.AppInstance;
import com.project.android.utility.Constants;

public class RestaurantDetailsActivity extends AppCompatActivity {
    private TextView nameTV, mailTV, mobileTV, addressTV, typeTV;

    long restaurantID;
    Restaurant restaurant = null;
    private ImageView profileIV;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantdetails);
        initializeUIComponents();
        restaurantID = getIntent().getLongExtra(Constants.ID_KEY, 0);

        displayData();
    }

    public void initializeUIComponents()
    {
        nameTV = findViewById(R.id.name);
        mailTV = findViewById(R.id.mail);
        mobileTV = findViewById(R.id.mobile);
        addressTV = findViewById(R.id.address);
        typeTV = findViewById(R.id.foodType);
        profileIV = findViewById(R.id.profile);
    }

    public void displayData()
    {
        AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);
        restaurant = databaseHelper.getRestaurantWithId(restaurantID);

        if (restaurant != null)
        {
            nameTV.setText(restaurant.getName());
            mailTV.setText(restaurant.getMail());
            mobileTV.setText(restaurant.getPhno());
            addressTV.setText(restaurant.getAddress());
            typeTV.setText(restaurant.getType());


            String path = restaurant.getProfilePath();
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            if (null != bitmap) {
                profileIV.setImageBitmap(bitmap);
            }
            else
            {
                int resID = this.getResources().getIdentifier("noimage", Constants.DRAWABLE_RESOURCE, this.getPackageName());
                profileIV.setImageResource(resID);
            }

        }
        else
        {
            Toast.makeText(getApplicationContext(), Constants.NO_USER, Toast.LENGTH_LONG).show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.changePassword:
                Intent intent = new Intent(this, AdminChangePasswordActivity.class);
                startActivity(intent);
                return true;

            case R.id.logout:
                ((AppInstance)getApplicationContext()).setAdminUser(false);
                Intent i = new Intent(this, UserSelectionActivity.class);
                startActivity(i);
                finish();
                return true;
            case R.id.about:
                android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
                dialogBuilder.setIcon(R.drawable.logo);
                dialogBuilder.setTitle(R.string.app_name);
                dialogBuilder.setMessage(Constants.APP_DESCRIPTION);
                dialogBuilder.create();
                dialogBuilder.show();
                return true;

        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.MY_PERMISSIONS_REQUEST_SEND_SMS:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    sendSms(restaurant.getPhno(),  "Admin has registered your restaurant using Food Wastage Reduction Through DonationApp.");
                    // Permission has already been granted


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;

        }
    }

    private void sendSms(String phonenumber, String message)
    {
        try {

            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> msgArray = smsManager.divideMessage(message);

            smsManager.sendMultipartTextMessage(phonenumber, null,msgArray, null, null);
            //Toast.makeText(getApplicationContext(), "Message Sent",Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

}
