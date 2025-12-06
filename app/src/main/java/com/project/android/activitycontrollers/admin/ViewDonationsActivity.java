package com.project.android.activitycontrollers.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.project.android.R;
import com.project.android.activitycontrollers.UserSelectionActivity;
import com.project.android.adapters.DonationListItemAdapter;
import com.project.android.adapters.RegisteredRestaurantListItemAdapter;
import com.project.android.database.AppDatabaseHelper;
import com.project.android.model.Donation;
import com.project.android.model.Restaurant;
import com.project.android.utility.AppInstance;
import com.project.android.utility.Constants;

import java.util.ArrayList;

public class ViewDonationsActivity extends AppCompatActivity {
    AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);
    ArrayAdapter<String> adapter;
    ArrayList<Restaurant> restaurantList;
    Spinner restaurantnameSP;
    Restaurant restaurant = null;
    ArrayList<Donation> donationList;
    DonationListItemAdapter customAdapter;
    private ListView listView = null;
    private TextView mNoDonationsView;
    private LinearLayout detailsLL;
    TextView restaurantDetailsTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdonations);
        listView = findViewById(R.id.listView);
        detailsLL = findViewById(R.id.donationDetails);
        restaurantDetailsTV = findViewById(R.id.restaurantDetails);
        restaurantnameSP =  findViewById(R.id.restaurantsnamelist);

        restaurantList = databaseHelper.getApprovedRestaurantList();

        if (restaurantList.size()>0) {
            final String[] restaurantnames = new String[restaurantList.size()];
            for (int i = 0; i < restaurantList.size(); i++) {
                restaurantnames[i] = restaurantList.get(i).getName();
            }

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, restaurantnames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
            restaurantnameSP.setAdapter(adapter);
        }
        else
        {
            restaurantDetailsTV.setText("There are no restaurants");
            detailsLL.setVisibility(View.GONE);
        }

    }

    public void view(View view)
    {
        if (restaurantList.size()>0)
            {
                restaurant = restaurantList.get(restaurantnameSP.getSelectedItemPosition());
                if (restaurant != null) {
                    detailsLL.setVisibility(View.VISIBLE);
                    populateListView();
                }
            }
        else
        {
            restaurantDetailsTV.setText("There are no restaurants");
            detailsLL.setVisibility(View.GONE);
        }
    }

    public void populateListView() {
        mNoDonationsView = findViewById(R.id.no_donation_text);
        mNoDonationsView.setText(Constants.NO_DONATIONS_DESCRIPTION);

        final AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);

        donationList = databaseHelper.getDonationsForRestaurantWithID(restaurant.getRestaurantID());
        if (donationList.size() > 0) {
            mNoDonationsView.setVisibility(View.GONE);
            customAdapter = new DonationListItemAdapter(this, R.layout.alldonationslist_item);
            customAdapter.setDonationList(donationList);
            listView = findViewById(R.id.listView);
            listView.setAdapter(customAdapter);
        } else {
            mNoDonationsView.setVisibility(View.VISIBLE);
            if (customAdapter != null) {
                reloadData();
            }
        }
    }

    public void reloadData() {
        donationList.clear();
        AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);
        donationList = databaseHelper.getDonationsForRestaurantWithID(restaurant.getRestaurantID());
        customAdapter.setDonationList(donationList);
        customAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (customAdapter != null) {
            reloadData();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
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

}
