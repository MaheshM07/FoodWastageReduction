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
import com.project.android.database.AppDatabaseHelper;
import com.project.android.model.Organization;
import com.project.android.utility.AppInstance;
import com.project.android.utility.Constants;

public class DeleteOrganizationActivity extends AppCompatActivity {
    AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);
    ArrayAdapter<String> adapter;
    ArrayList<Organization> organizationList;
    Spinner organizationnameSP;

    TextView organizationdetailsTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleteorganization);

        organizationList = databaseHelper.getApprovedOrganizationList();

        final String[] organizationnames = new String[organizationList.size()];
        for (int i =0; i < organizationList.size(); i++)
        {
            organizationnames[i] = organizationList.get(i).getOrganizationname();
        }

        organizationnameSP = findViewById(R.id.organizationsnamelist);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter(this, R.layout.custom_spinner, organizationnames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        organizationnameSP.setAdapter(dataAdapter);
        organizationnameSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedOrganizationname = organizationList.get(position).getOrganizationname();
                Organization organization = databaseHelper.getOrganizationWithOrganizationName(selectedOrganizationname);
                if (organization !=null ) {
                    organizationdetailsTV.setText("Contact: " + organization.getPhno() + "\nMail: " + organization.getEmail());
                }
                else
                {
                    organizationdetailsTV.setText("No Organization to delete");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        organizationdetailsTV = (TextView) findViewById(R.id.organizationDetails);


        adapter = new ArrayAdapter<>(this,   android.R.layout.simple_spinner_dropdown_item, organizationnames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        organizationnameSP.setAdapter(adapter);

        if ( organizationList == null || organizationList.size() == 0 )
        {
            Toast.makeText(getApplicationContext(), Constants.NO_ORGANIZATION_TO_DELETE, Toast.LENGTH_SHORT).show();
        }


    }

    public void deleteOrganization(View view)
    {
        if ( organizationList == null || organizationList.size() == 0 )
        {
            Toast.makeText(getApplicationContext(), Constants.NO_ORGANIZATION_TO_DELETE, Toast.LENGTH_SHORT).show();
        }
        else {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            // Setting Dialog Title
            alertDialog.setTitle("Delete Organization...");

            // Setting Dialog Message
            alertDialog.setMessage("Are you sure you want to delete this organization?");

            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.delete);
            alertDialog.setCancelable(false);
            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {
                            // Write your code here to execute after dialog
                            Organization selectedOrganization = organizationList.get(organizationnameSP.getSelectedItemPosition());
                            selectedOrganization.setDeleted(true);
                            databaseHelper.deleteOrganization(selectedOrganization);
                            Toast.makeText(getApplicationContext(), Constants.ORGANIZATION_DELETED_SUCCESSFULLY, Toast.LENGTH_SHORT).show();
                            clearUI();

                        }
                    });
            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,	int which) {
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
        organizationList.clear();
        organizationList = databaseHelper.getApprovedOrganizationList();

        final String[] organizationnames = new String[organizationList.size()];
        for (int i =0; i < organizationList.size(); i++)
        {
            organizationnames[i] = organizationList.get(i).getOrganizationname();
        }

        adapter = new ArrayAdapter<>(this,   android.R.layout.simple_spinner_dropdown_item, organizationnames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        organizationnameSP.setAdapter(adapter);

        if(organizationList.size()==0)
        {
            organizationdetailsTV.setText("No organizations to delete");
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
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
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
                Intent intent = new Intent(this, AdminChangePasswordActivity.class);
                startActivity(intent);
                return true;


        }
        return false;
    }
}


