package com.project.android.activitycontrollers.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.project.android.R;
import com.project.android.activitycontrollers.UserSelectionActivity;
import com.project.android.adapters.OrganizationListItemAdapter;
import com.project.android.database.AppDatabaseHelper;
import com.project.android.model.Organization;
import com.project.android.utility.AppInstance;
import com.project.android.utility.Constants;

import java.util.ArrayList;

public class ViewDeletedOrganizationActivity extends AppCompatActivity {
    private ListView listView = null;

    OrganizationListItemAdapter customAdapter;
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
        mNoOrganizationsView.setText(Constants.NODELETEDORGANIZATIONS_DESCRIPTION);

        final AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);

        organizationList = databaseHelper.getDeletedOrganizationList();
        if (organizationList.size() > 0) {
            mNoOrganizationsView.setVisibility(View.GONE);
            customAdapter = new OrganizationListItemAdapter(this, R.layout.allorganizationslist_item);
            customAdapter.setOrganizationList(organizationList);
            listView = findViewById(R.id.listView);
            listView.setAdapter(customAdapter);
        } else {
            mNoOrganizationsView.setVisibility(View.VISIBLE);
            if (customAdapter != null) {
                reloadData();
            }
        }
    }

    public void reloadData() {
        organizationList.clear();
        customAdapter.setOrganizationList(organizationList);
        customAdapter.notifyDataSetChanged();
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
