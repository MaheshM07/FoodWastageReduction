package com.project.android.activitycontrollers.restaurant;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.android.R;
import com.project.android.activitycontrollers.UserSelectionActivity;
import com.project.android.activitycontrollers.organization.ChangePasswordActivity;
import com.project.android.adapters.FeedbackListItemAdapter;
import com.project.android.database.AppDatabaseHelper;
import com.project.android.model.Feedback;
import com.project.android.model.Restaurant;
import com.project.android.utility.AppInstance;
import com.project.android.utility.Constants;

import java.util.ArrayList;

public class ViewFeedbackActivity extends AppCompatActivity{
    private ListView listView = null;

    FeedbackListItemAdapter customAdapter;
    ArrayList<Feedback> feedbackList;
    private TextView mNoFeedbackView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewfeedback);
        populateListView();
    }

    public void populateListView()
    {
        mNoFeedbackView = findViewById(R.id.no_feedback_text);
        mNoFeedbackView.setText(Constants.NOFEEDBACK_DESCRIPTION );

        final AppDatabaseHelper databaseHelper = new AppDatabaseHelper(this);
        Restaurant restaurant = ((AppInstance) getApplicationContext()).getCurrentRestaurant();

        feedbackList = databaseHelper.getFeedbackListForRestaurantWithID(restaurant.getRestaurantID());

        if (feedbackList.size() > 0)
        {
            mNoFeedbackView.setVisibility(View.GONE);
            customAdapter = new FeedbackListItemAdapter(this, R.layout.allfeedbacklist_item);
            customAdapter.setFeedbackList(feedbackList);
            listView = findViewById(R.id.listView);
            listView.setAdapter(customAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    Feedback feedback = feedbackList.get(i);
                    Intent intent = new Intent(getApplicationContext(), FeedbackDetailsActivity.class);

                    intent.putExtra(Constants.ID_KEY, feedback.getFeedbackID());
                    startActivity(intent);
                }
            });

        } else
        {
            mNoFeedbackView.setVisibility(View.VISIBLE);
            if (customAdapter != null)
            {
                reloadData();
            }
        }
    }

    public void reloadData()
    {
        feedbackList.clear();
        customAdapter.setFeedbackList(feedbackList);
        customAdapter.notifyDataSetChanged();
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
