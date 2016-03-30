package com.example.assignmentplanner;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Activity to display the details of a selected assignment.
 *
 * @author cjearly
 */
public class AssignmentDetailsActivity extends ActionBarActivity {

    /**
     * Name of the assignment
     */
    private String name;
    /**
     * The assignment due date
     */
    private String dueDate;
    /**
     * The assignment's class
     */
    private String assignmentClass;
    /**
     * Type of assignment
     */
    private String type;
    /**
     * Additional info
     */
    private String additionalInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_details);

        Intent intent = getIntent();
        name = intent.getStringExtra("assignmentName");
        dueDate = intent.getStringExtra("dueDate");
        assignmentClass = intent.getStringExtra("class");
        type = intent.getStringExtra("type");
        additionalInfo = intent.getStringExtra("additionalInfo");

        setDetails();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.assignment_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Sets the text views for displaying assignment details
     */
    private void setDetails() {
        // assignment name
        TextView nameTextView = (TextView) findViewById(R.id.assignment_details_txtName);
        nameTextView.setText(name);

        // due date
        TextView dueDateTextView = (TextView) findViewById(R.id.assignment_details_txtDueDate);
        dueDateTextView.setText(dueDate);

        // class
        TextView classTextView = (TextView) findViewById(R.id.assignment_details_txtClass);
        classTextView.setText(assignmentClass);

        // type
        TextView typeTextView = (TextView) findViewById(R.id.assignment_details_txtType);
        typeTextView.setText(type);

        // additional info
        TextView additionalInfoTextView = (TextView) findViewById(R.id.assignment_details_txtAdditionalInfo);
        additionalInfoTextView.setText(additionalInfo);
    }
}
