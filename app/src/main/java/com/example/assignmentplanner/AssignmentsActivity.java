package com.example.assignmentplanner;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.layout.Assignment;

public class AssignmentsActivity extends ActionBarActivity {

    /**
     * Assignments to display
     */
    private List<Assignment> assignments = new ArrayList<Assignment>();
    /**
     * Name of assignment
     */
    private String assignmentName;
    /**
     * Type of assignment
     */
    private String type;
    /**
     * Assignment's class
     */
    private String assignmentClass;
    /**
     * Assignment due date
     */
    private String dueDate;
    /**
     * Assignment due time
     */
    private String dueTime;
    /**
     * Additional info
     */
    private String additionalInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);

        try {
            populateList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        populateListView();
        registerClickCallback();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.assignments, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_addNewAssignment:
                openAddNew(item);
                return true;
            case R.id.action_settingsAssignment:
                //openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Called when the user clicks the add button in the action bar
     *
     * @param item menu item selected
     */
    private void openAddNew(MenuItem item) {
        Intent intent = new Intent(this, AddNewAssignmentActivity.class);
        startActivity(intent);
    }

    /**
     * Get the assignments by reading from internal storage and add them to array list.
     *
     * @throws Exception any exceptions that might result from file IO
     */
    private void populateList() throws Exception {

        // get the file input stream
        FileInputStream inputStream = openFileInput("assignments");

        // create scanner for reading
        Scanner fileReader = new Scanner(inputStream);

        while (fileReader.hasNextLine()) {
            additionalInfo = ""; // clear additional info for next loop
            // get assignment name, type, class, due date and time
            assignmentName = fileReader.nextLine();
            type = fileReader.nextLine();
            assignmentClass = fileReader.nextLine();
            dueDate = fileReader.nextLine();
            dueTime = fileReader.nextLine();

            // get all the additional info (everything until "end")
            int flag = 0;
            while (flag != -1) {
                String nextLine = fileReader.nextLine();
                if (!nextLine.equals("end")) {
                    additionalInfo += nextLine + "\n";
                } else
                    flag = -1;
            }
            assignments.add(new Assignment(assignmentName, type, assignmentClass, dueDate, dueTime, additionalInfo));
        }

        fileReader.close();
        inputStream.close();
    }

    /**
     * Add the assignments to the list view using myListAdapter
     */
    private void populateListView() {
        ArrayAdapter<Assignment> adapter = new myListAdapter();
        ListView list = (ListView) findViewById(R.id.assignmentsList);
        list.setAdapter(adapter);
    }

    /**
     * Register listeners for each item to navigate to assignment details when clicked.
     */
    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.assignmentsList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long id) {

                // Get the assignment selected
                Assignment clickedAssignment = assignments.get(position);

                // pass the data to the details activity
                Intent intent = new Intent(AssignmentsActivity.this, AssignmentDetailsActivity.class);
                intent.putExtra("assignmentName", clickedAssignment.getName());
                intent.putExtra("class", clickedAssignment.getClassName());
                intent.putExtra("dueDate", clickedAssignment.getDueDate());
                intent.putExtra("type", clickedAssignment.getType());
                intent.putExtra("additionalInfo", clickedAssignment.getAdditionalInfo());
                startActivity(intent);
            }
        });
    }

    /**
     * Custom adapter for adding values to the assignment list view
     *
     * @author cjearly
     */
    private class myListAdapter extends ArrayAdapter<Assignment> {
        public myListAdapter() {
            super(AssignmentsActivity.this, R.layout.assignment_list_item_view, assignments);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.assignment_list_item_view, parent, false);
            }

            // find the assignment to work with.
            Assignment currentAssignment = assignments.get(position);

            // fill the view
            // assignment name:
            TextView assignmentName = (TextView) itemView.findViewById(R.id.assignment_list_txtName);
            assignmentName.setText(currentAssignment.getName());

            // due date:
            TextView dueDate = (TextView) itemView.findViewById(R.id.assignment_list_txtDueDate);
            dueDate.setText(currentAssignment.getDueDate());

            // class:
            TextView assignmentClass = (TextView) itemView.findViewById(R.id.assignment_list_txtClass);
            assignmentClass.setText(currentAssignment.getClassName());

            // type:
            TextView type = (TextView) itemView.findViewById(R.id.assignment_list_txtType);
            type.setText(currentAssignment.getType());

            return itemView;
        }
    }
}
