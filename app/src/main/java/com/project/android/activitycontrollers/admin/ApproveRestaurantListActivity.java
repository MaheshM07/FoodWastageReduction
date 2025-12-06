package com.project.android.activitycontrollers.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.android.R;
import com.project.android.activitycontrollers.UserSelectionActivity;
import com.project.android.adapters.RegisteredRestaurantListItemAdapter;
import com.project.android.adapters.RestaurantListItemAdapter;
import com.project.android.database.AppDatabaseHelper;
import com.project.android.model.Restaurant;
import com.project.android.utility.AppInstance;
import com.project.android.utility.Constants;

import java.util.ArrayList;

public class ApproveRestaurantListActivity extends AppCompatActivity {
    private ListView listView = null;

    RegisteredRestaurantListItemAdapter customAdapter;
    ArrayList<Restaurant> restaurantList;
    private TextView mNoRestaurantsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantlist);
        populateListView();
    }

    public void populateListView() {
        mNoRestaurantsView = findViewById(R.id.no_restaurant_text);
        mNoRestaurantsView.setText(Constants.NO_REGISTERED_RESTAURANTS_DESCRIPTION);

        final AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);

        restaurantList = databaseHelper.getRegisteredRestaurantList();
        if (restaurantList.size() > 0) {
            mNoRestaurantsView.setVisibility(View.GONE);
            customAdapter = new RegisteredRestaurantListItemAdapter(this, R.layout.registeredrestaurantslist_item);
            customAdapter.setRestaurantList(restaurantList);
            listView = findViewById(R.id.listView);
            listView.setAdapter(customAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    Restaurant restaurant = restaurantList.get(i);
                    Intent intent = new Intent(getApplicationContext(), ApproveRestaurantActivity.class);
                    intent.putExtra(Constants.ID_KEY, restaurant.getRestaurantID());
                    startActivity(intent);
                }
            }) ;
        } else {
            mNoRestaurantsView.setVisibility(View.VISIBLE);
            if (customAdapter != null) {
                reloadData();
            }
        }
    }

    public void reloadData() {
        restaurantList.clear();
        AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);
        restaurantList = databaseHelper.getRegisteredRestaurantList();
        customAdapter.setRestaurantList(restaurantList);
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
