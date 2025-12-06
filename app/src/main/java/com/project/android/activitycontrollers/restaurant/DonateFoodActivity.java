package com.project.android.activitycontrollers.restaurant;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.android.R;
import com.project.android.activitycontrollers.UserSelectionActivity;
import com.project.android.activitycontrollers.organization.ChangePasswordActivity;
import com.project.android.database.AppDatabaseHelper;
import com.project.android.model.Donation;
import com.project.android.model.Organization;
import com.project.android.model.Restaurant;
import com.project.android.utility.AppInstance;
import com.project.android.utility.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DonateFoodActivity extends AppCompatActivity{
    private RadioGroup resTypeRG;
    private EditText hoursAvailableET;
    private Spinner quantitySP;
    String quantity;
    String hoursAvailable;
    String foodType;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantdonate);
        initializeUIComponents();
    }

    public void initializeUIComponents()
    {
        resTypeRG = findViewById(R.id.resType);
        hoursAvailableET = findViewById(R.id.duration);
        quantitySP = findViewById(R.id.quantitylist);
    }

    public void donate(View view)
    {
        RadioButton radioButton = findViewById(resTypeRG.getCheckedRadioButtonId());
        foodType = radioButton.getText().toString();
        quantity = quantitySP.getSelectedItem().toString();

        hoursAvailable = hoursAvailableET.getText().toString().trim();
        if (hoursAvailable.length() == 0) {
            hoursAvailableET.setError(Constants.MISSING_HOURS_AVAILABLE);
            hoursAvailableET.requestFocus();
        }
        else {
            Restaurant restaurant = ((AppInstance) getApplicationContext()).getCurrentRestaurant();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-YYYY", Locale.US);
            String date = dateFormatter.format(Calendar.getInstance().getTime());
            AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);


            Donation donation = new Donation();
            donation.setDate(Calendar.getInstance());
            donation.setFoodType(foodType);
            donation.setNumberOfPersons(Integer.parseInt(quantity));
            donation.setRestaurantID(restaurant.getRestaurantID());
            long donationID = databaseHelper.addDonation(donation);
            donation.setId(donationID);



            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.SEND_SMS)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.SEND_SMS},
                            Constants.MY_PERMISSIONS_REQUEST_SEND_SMS);

                    // MY_PERMISSIONS_REQUEST_SEND_SMS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                ArrayList<Organization> organizationList = databaseHelper.getApprovedOrganizationList();


                for (Organization organization:organizationList)
                {
                    sendSms(organization.getPhno(), "Restaurant with the following details is donating food using Food Wastage Reduction Through Donation App. \nName: " + restaurant.getName() + "\nContact: " + restaurant.getPhno() + "\nAddress: " + restaurant.getAddress() + "\nFood Type: " + foodType + "\nAvailable for: " + quantity + " Persons" + "\nFood Available for next: " + hoursAvailable + " hours" + "\nDate: " + date);
                }
                Toast.makeText(getApplicationContext(), Constants.SMS_SENT_TO_ORGANIZATIONS, Toast.LENGTH_SHORT).show();

            }
            finish();
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.MY_PERMISSIONS_REQUEST_SEND_SMS:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);
                    ArrayList<Organization> organizationList = databaseHelper.getApprovedOrganizationList();
                    Restaurant restaurant = ((AppInstance) getApplicationContext()).getCurrentRestaurant();
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-YYYY", Locale.US);
                    String date = dateFormatter.format(Calendar.getInstance().getTime());

                    for (Organization organization:organizationList)
                    {
                        sendSms(organization.getPhno(), "Restaurant with the following details is donating food using Food Wastage Reduction Through Donation App. \nName: " + restaurant.getName() + "\nContact: " + restaurant.getPhno() + "\nAddress: " + restaurant.getAddress() + "\nFood Type: " + foodType + "\nAvailable for: " + quantity + " Persons" + "\nFood Available for next: " + hoursAvailable + " hours" + "\nDate: " + date);
                    }
                    Toast.makeText(getApplicationContext(), Constants.SMS_SENT_TO_ORGANIZATIONS, Toast.LENGTH_SHORT).show();


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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sub_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                ((AppInstance) getApplicationContext()).setCurrentOrganization(null);
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
            case R.id.changePassword:
                Intent intent = new Intent(this, ChangePasswordActivity.class);
                startActivity(intent);
                return true;
            case R.id.help:
                android.app.AlertDialog.Builder helpDialogBuilder = new android.app.AlertDialog.Builder(this);
                helpDialogBuilder.setIcon(R.drawable.logo);
                helpDialogBuilder.setTitle(R.string.app_name);
                helpDialogBuilder.setMessage(Constants.HELP_MESSAGE);
                helpDialogBuilder.create();
                helpDialogBuilder.show();
                return true;
            case R.id.feedback:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, Constants.FEEDBACK_SUBJECT);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{Constants.FEEDBACK_MAILID});

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send feedback..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_LONG).show();
                }
                return true;

        }

        return false;
    }

}

