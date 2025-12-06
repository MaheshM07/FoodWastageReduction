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


import java.util.ArrayList;

import com.project.android.R;
import com.project.android.activitycontrollers.UserSelectionActivity;
import com.project.android.adapters.OrganizationListItemAdapter;
import com.project.android.adapters.RegisteredOrganizationListItemAdapter;
import com.project.android.database.AppDatabaseHelper;
import com.project.android.model.Organization;
import com.project.android.utility.AppInstance;
import com.project.android.utility.Constants;

public class ApproveOrganizationListActivity extends AppCompatActivity
{
    private ListView listView = null;

    RegisteredOrganizationListItemAdapter customAdapter;
    ArrayList<Organization> organizationList;
    private TextView mNoOrganizationsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizationlist);
        populateListView();
    }

    public void populateListView() {
        mNoOrganizationsView = findViewById(R.id.no_organization_text);
        mNoOrganizationsView.setText(Constants.NOREGISTEREDORGANIZATIONS_DESCRIPTION);

        final AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);

        organizationList = databaseHelper.getRegisteredOrganizationList();
        if (organizationList.size() > 0) {
            mNoOrganizationsView.setVisibility(View.GONE);
            customAdapter = new RegisteredOrganizationListItemAdapter(this, R.layout.registeredorganizationslist_item);
            customAdapter.setOrganizationList(organizationList);
            listView = findViewById(R.id.listView);
            listView.setAdapter(customAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    Organization organization = organizationList.get(i);
                    Intent intent = new Intent(getApplicationContext(), ApproveOrganizationActivity.class);
                    intent.putExtra(Constants.ID_KEY, organization.getOrganizationID());
                    startActivity(intent);
                }
            });


        } else {
            mNoOrganizationsView.setVisibility(View.VISIBLE);
            if (customAdapter != null) {
                reloadData();
            }
        }
    }

    public void reloadData() {
        organizationList.clear();
        AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);
        organizationList = databaseHelper.getRegisteredOrganizationList();
        if (organizationList.size() <= 0) {
            mNoOrganizationsView.setVisibility(View.VISIBLE);
        }

        customAdapter.setOrganizationList(organizationList);
        customAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        reloadData();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.changePassword:
                Intent intent = new Intent(this, AdminChangePasswordActivity.class);
                startActivity(intent);
                return true;

            case R.id.logout:
                ((AppInstance) getApplicationContext()).setAdminUser(false);
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


