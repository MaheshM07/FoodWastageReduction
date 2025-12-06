package com.project.android.activitycontrollers.organization;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.project.android.R;
import com.project.android.activitycontrollers.UserSelectionActivity;
import com.project.android.database.AppDatabaseHelper;
import com.project.android.model.Organization;
import com.project.android.utility.Constants;
import com.project.android.utility.ImageFilePath;
import com.project.android.utility.Utility;

public class OrganizationSignUpActivity extends AppCompatActivity {
    AppDatabaseHelper databaseHelper;
    private EditText nameET, usernameET, passwordET, emailET, mobileET,addressET,confirmpasswordET;
    private ImageView iconIV;
    private String realPath =null;

    Organization organization =null;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charityorganizationsignup);
        databaseHelper = new AppDatabaseHelper(this);
        initializeUIComponents();

    }
    public void initializeUIComponents()
    {

        usernameET =  findViewById(R.id.userName);
        passwordET = findViewById(R.id.password);
        emailET = findViewById(R.id.mailID);
        mobileET = findViewById(R.id.phnNo);
        nameET =findViewById(R.id.name);
        addressET=findViewById(R.id.address);
        confirmpasswordET=findViewById(R.id.confirmpassword);
        iconIV=findViewById(R.id.image);
    }
    public void submit(View view)
    {

        String username = usernameET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        String name =nameET.getText().toString().trim();
        String address =addressET.getText().toString().trim();
        String confirmpassword =confirmpasswordET.getText().toString().trim();
        String email = emailET.getText().toString().trim();
        String mobile = mobileET.getText().toString().trim();

        ArrayList<String> userNames = databaseHelper.getAllOrganizationUserNames();

        if(name.length()==0)
        {
            nameET.setError(Constants.MISSING_NAME);
            nameET.requestFocus();
        }
        else if (TextUtils.isEmpty(email))

        {
            emailET.setError(Constants.MISSING_EMAIL);
            emailET.requestFocus();
        }
        else if(false== Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailET.setError(Constants.INVALID_EMAIL);
            emailET.requestFocus();
        }
        else if (mobile.length() == 0)
        {
            mobileET.setError(Constants.MISSING_MOBILE);
            mobileET.requestFocus();
        }
        else if (mobile.length() != 10)
        {
            mobileET.setError(Constants.INVALID_MOBILE);
            mobileET.requestFocus();
        }
        else if(address.length()==0)
        {
            addressET.setError(Constants.MISSING_AGE);
            addressET.requestFocus();
        }
        else if (username.length() == 0)
        {
            usernameET.setError(Constants.MISSING_USERNAME);
            usernameET.requestFocus();
        }
        else if (userNames.size()>0 && userNames.contains(username)){
            usernameET.setError(Constants.DUPLICATE_USERNAME);
            usernameET.requestFocus();
        }
        else if (password.length() == 0)
        {
            passwordET.setError(Constants.MISSING_PASSWORD);
            passwordET.requestFocus();
        }
        else if(password.length()<Constants.MINIMUM_PASSWORD_LENGTH)
        {
            passwordET.setError(Constants.INVALID_PASSWORD);
            passwordET.requestFocus();
        }
        else if (confirmpassword.length() == 0) {
            confirmpasswordET.setError(Constants.MISSING_PASSWORD_CONFIRMATION);
            confirmpasswordET.requestFocus();
        }
        else if (!password.equals(confirmpassword)) {
            confirmpasswordET.setError(Constants.PASSWORD_MISMATCH);
            confirmpasswordET.requestFocus();
        }
        else if (false == isValidPassword(password))
        {
            passwordET.setError(Constants.INVALID_PASSWORD);
            passwordET.requestFocus();
        }

        else if(realPath ==null)
        {
            Toast.makeText(getApplicationContext(),Constants.MISSING_ORGANIZATION_PROFILEPHOTO,Toast.LENGTH_SHORT).show();
        }
        else
        {
            organization = new Organization();
            organization.setOrganizationname(name);
            organization.setConfirmPassword(confirmpassword);
            organization.setEmail(email);
            organization.setAddress(address);
            organization.setPhno(mobile);
            organization.setPassword(password);
            organization.setUsername(username);
            organization.setProfilePath(realPath);
            organization.setApproved(false);
            AppDatabaseHelper databaseHelper=new AppDatabaseHelper(this);
            long organization_id= databaseHelper.addOrganization(organization);
            organization.setOrganizationID(organization_id);
            Intent intent=new Intent(this,UserSelectionActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(getApplicationContext(), Constants.ORGANIZATION_REGISTERED_SUCCESSFULLY, Toast.LENGTH_SHORT).show();
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED)
            {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.SEND_SMS))
                {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                }
                else
                {

                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.SEND_SMS},
                            Constants.MY_PERMISSIONS_REQUEST_SEND_SMS);

                    // MY_PERMISSIONS_REQUEST_SEND_SMS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
            else
            {

                sendSms(Constants.ADMIN_MOBILE,  "Organization with the following details has registered using Food Wastage Reduction Through Donation App. \nName: " + organization.getOrganizationname() + "\nContact: " + organization.getPhno());
            }

            // Permission has already been granted
        }

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
                    sendSms(Constants.ADMIN_MOBILE,  "Organization with the following details has registered using Food Wastage Reduction Through Donation App. \nName: " + organization.getOrganizationname() + "\nContact: " + organization.getPhno());
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



    public boolean isValidPassword(final String password)

    {

        Pattern pattern;

        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[*@#$%^&+=!])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);

        matcher = pattern.matcher(password);

        return matcher.matches();

    }



    public void selectPhoto(View view)
    {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                String userChoosenTask;

                boolean result= Utility.checkPermission(OrganizationSignUpActivity.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask="Take Photo";
                    if(result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask="Choose from Library";
                    if(result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Constants.REQUEST_CAMERA);
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),Constants.SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == Constants.REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Uri uri = data.getData();


        realPath = ImageFilePath.getPath(this, data.getData());

        try
        {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

            iconIV.setImageBitmap(bitmap);
            iconIV.setVisibility(View.VISIBLE);

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            Uri fileUri = Uri.fromFile(destination);
            realPath = ImageFilePath.getPath(this, fileUri);
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        iconIV.setImageBitmap(thumbnail);
        iconIV.setVisibility(View.VISIBLE);
    }
    public boolean onCreateOptionsMenu(android.view.Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.about_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.about:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                dialogBuilder.setIcon(R.drawable.logo);
                dialogBuilder.setTitle(R.string.app_name);
                dialogBuilder.setMessage(Constants.APP_DESCRIPTION);
                dialogBuilder.create();
                dialogBuilder.show();
                return true;
        }
        return false;
    }

}
