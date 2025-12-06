package com.project.android.activitycontrollers.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import com.project.android.R;
import com.project.android.activitycontrollers.UserSelectionActivity;
import com.project.android.activitycontrollers.organization.ChangePasswordActivity;
import com.project.android.database.AppDatabaseHelper;
import com.project.android.model.Restaurant;
import com.project.android.utility.AppInstance;
import com.project.android.utility.Constants;

public class DeleteRestaurantActivity extends AppCompatActivity {
    AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);
    ArrayAdapter<String> adapter;
    ArrayList<Restaurant> restaurantList;
    Spinner restaurantnameSP;

    TextView restaurantdetailsTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleterestaurant);

        restaurantList = databaseHelper.getApprovedRestaurantList();

        final String[] restaurantnames = new String[restaurantList.size()];
        for (int i =0; i < restaurantList.size(); i++)
        {
            restaurantnames[i] = restaurantList.get(i).getName();
        }

        restaurantnameSP =  findViewById(R.id.restaurantsnamelist);
        restaurantdetailsTV = findViewById(R.id.restaurantDetails);

        restaurantnameSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedRestaurantname = restaurantList.get(position).getName();
                Restaurant restaurant = databaseHelper.getRestaurantWithRestaurantName(selectedRestaurantname);
                if (restaurant !=null ) {
                    restaurantdetailsTV.setText("Contact: " + restaurant.getPhno() + "\nMail: " + restaurant.getMail());
                }
                else
                {
                    restaurantdetailsTV.setText("No Restaurant to delete");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        adapter = new ArrayAdapter<>(this,   android.R.layout.simple_spinner_dropdown_item, restaurantnames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        restaurantnameSP.setAdapter(adapter);

        if ( restaurantList == null || restaurantList.size() == 0 )
        {
            restaurantdetailsTV.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), Constants.NO_RESTAURANT_TO_DELETE, Toast.LENGTH_SHORT).show();
        }


    }

    public void deleteRestaurant(View view)
    {
        if ( restaurantList == null || restaurantList.size() == 0 )
        {
            Toast.makeText(getApplicationContext(), Constants.NO_RESTAURANT_TO_DELETE, Toast.LENGTH_SHORT).show();
        }
        else {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            // Setting Dialog Title
            alertDialog.setTitle("Delete Restaurant...");
            // Setting Dialog Message
            alertDialog.setMessage("Are you sure you want to delete this restaurant?");
            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.delete);
            alertDialog.setCancelable(false);
            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog
                            Restaurant selectedRestaurant = restaurantList.get(restaurantnameSP.getSelectedItemPosition());
                            selectedRestaurant.setDeleted(true);
                            databaseHelper.deleteRestaurant(selectedRestaurant);
                            Toast.makeText(getApplicationContext(), Constants.RESTAURANT_DELETED_SUCCESSFULLY, Toast.LENGTH_SHORT).show();
                            clearUI();

                        }
                    });
            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog
                            Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });

            // Showing Alert Message
            alertDialog.show();

        }
    }

    public void  clearUI()
    {
        restaurantList.clear();
        restaurantList = databaseHelper.getApprovedRestaurantList();

        final String[] restaurantnames = new String[restaurantList.size()];
        for (int i =0; i < restaurantList.size(); i++)
        {
            restaurantnames[i] = restaurantList.get(i).getName();
        }

        adapter = new ArrayAdapter<>(this,   android.R.layout.simple_spinner_dropdown_item, restaurantnames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        restaurantnameSP.setAdapter(adapter);

        if(restaurantList.size()==0)
        {
            restaurantdetailsTV.setText("No restaurants to delete");
        }

    }

    public boolean onCreateOptionsMenu(android.view.Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.about:
                android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);
                dialogBuilder.setIcon(R.drawable.logo);
                dialogBuilder.setTitle(R.string.app_name);
                dialogBuilder.setMessage(Constants.APP_DESCRIPTION);
                dialogBuilder.create();
                dialogBuilder.show();
                return true;
            case R.id.logout:
                ((AppInstance)getApplicationContext()).setAdminUser(false);
                Intent i = new Intent(this, UserSelectionActivity.class);
                startActivity(i);
                finish();
                return true;
            case R.id.changePassword:
                Intent intent = new Intent(this, ChangePasswordActivity.class);
                startActivity(intent);
                return true;


        }
        return false;
    }
}
