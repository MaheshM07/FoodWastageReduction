package com.project.android.activitycontrollers.organization;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.android.R;
import com.project.android.activitycontrollers.UserSelectionActivity;
import com.project.android.database.AppDatabaseHelper;
import com.project.android.model.Feedback;
import com.project.android.model.Organization;
import com.project.android.model.Restaurant;
import com.project.android.utility.AppInstance;
import com.project.android.utility.Constants;

import java.util.ArrayList;

public class GiveFeedbackActivity extends AppCompatActivity{
    private EditText descriptionET;
    private Spinner restaurantNamesSP;
    ArrayList<Restaurant> restaurantList = null;
    ArrayList<String> restaurantNames = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_givefeedback);
        initializeUIComponents();
    }

    public void initializeUIComponents()
    {
        descriptionET = findViewById(R.id.description);
        restaurantNamesSP = findViewById(R.id.restaurant);
        AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);
        restaurantList = databaseHelper.getApprovedRestaurantList();
        restaurantNames = new ArrayList<>();
        for(Restaurant restaurant: restaurantList)
        {
            restaurantNames.add(restaurant.getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter(this, R.layout.custom_spinner, restaurantNames);
        dataAdapter.setDropDownViewResource(R.layout.custom_spinner);
        restaurantNamesSP.setAdapter(dataAdapter);
    }

    public void submit(View view)
    {
        String description = descriptionET.getText().toString().trim();

        if (description.length() == 0)
        {
            descriptionET.setError(Constants.MISSING_FEEDBACK_DESCRIPTION);
            descriptionET.requestFocus();
        }
        else
        {
            Feedback feedback = new Feedback();
            feedback.setDescription(description);

            Organization organization = ((AppInstance)getApplicationContext()).getCurrentOrganization();
            feedback.setOrganizationID(organization.getOrganizationID());

            String selectedRestaurantName = restaurantNamesSP.getSelectedItem().toString();
            Restaurant restaurant  = restaurantList.get(restaurantNames.indexOf(selectedRestaurantName));
            feedback.setRestaurantID(restaurant.getRestaurantID());

            AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);
            long feedbackID = databaseHelper.addFeedback(feedback);
            feedback.setFeedbackID(feedbackID);

            Toast.makeText(getApplicationContext(), Constants.FEEDBACK_POSTED_SUCCESSSFULLY, Toast.LENGTH_SHORT).show();
            clearUI();
        }
    }

    public void clearUI()
    {
        descriptionET.setText("");
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
