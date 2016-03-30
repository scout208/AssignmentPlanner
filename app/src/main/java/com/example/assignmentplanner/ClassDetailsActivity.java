package com.example.assignmentplanner;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.layout.Assignment;

/**
 * Activity for displaying the class details
 *
 * @author cjearly
 */
public class ClassDetailsActivity extends ActionBarActivity {

    /**
     * Name of class
     */
    private String className;
    /**
     * SH
     */
    private int SH;
    /**
     * Days of the week that the class meets
     */
    private String daysOfTheWeek;
    /**
     * The time that the class meets each day
     */
    private String time;
    /**
     * The assignments for this class
     */
    private List<Assignment> assignments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);

        assignments = new ArrayList<Assignment>();

        Intent intent = getIntent();
        className = intent.getStringExtra("className");
        SH = intent.getIntExtra("SH", 3);
        daysOfTheWeek = intent.getStringExtra("daysOfTheWeek");
        time = intent.getStringExtra("time");

        try {
            findAssignments();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setDetails();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.class_details, menu);
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
     * Set the values of text views to dsiplay the class details
     */
    private void setDetails() {
        // class name
        TextView nameTextView = (TextView) findViewById(R.id.class_details_txtName);
        nameTextView.setText(className);

        // SH
        TextView SHTextView = (TextView) findViewById(R.id.class_details_txtSH);
        SHTextView.setText(String.valueOf(SH) + " SH");

        // days of the week
        for (int i = 0; i < daysOfTheWeek.length(); i++) {
            switch (daysOfTheWeek.charAt(i)) {
                case 'M':
                    TextView monday = (TextView) findViewById(R.id.class_details_txtMonday);
                    monday.setTypeface(null, Typeface.BOLD);
                    monday.setTextColor(getResources().getColor(R.color.SkyBlue));
                    break;
                case 'T':
                    if (daysOfTheWeek.charAt(i + 1) == 'h') {
                        TextView thursday = (TextView) findViewById(R.id.class_details_txtThursday);
                        thursday.setTypeface(null, Typeface.BOLD);
                        thursday.setTextColor(getResources().getColor(R.color.SkyBlue));
                        i++;
                    } else {
                        TextView tuesday = (TextView) findViewById(R.id.class_details_txtTuesday);
                        tuesday.setTypeface(null, Typeface.BOLD);
                        tuesday.setTextColor(getResources().getColor(R.color.SkyBlue));
                    }
                    break;
                case 'W':
                    TextView wednesday = (TextView) findViewById(R.id.class_details_txtWednesday);
                    wednesday.setTypeface(null, Typeface.BOLD);
                    wednesday.setTextColor(getResources().getColor(R.color.SkyBlue));
                    break;
                case 'F':
                    TextView friday = (TextView) findViewById(R.id.class_details_txtFriday);
                    friday.setTypeface(null, Typeface.BOLD);
                    friday.setTextColor(getResources().getColor(R.color.SkyBlue));
                    break;
            }
        }

        // time
        TextView timeTextView = (TextView) findViewById(R.id.class_details_txtTime);
        timeTextView.setText(time);

        // set assignments list
        ArrayAdapter<Assignment> adapter = new myListAdapter();
        ListView list = (ListView) findViewById(R.id.class_details_listAssignments);
        list.setAdapter(adapter);
    }

    /**
     * Find assignments for this class
     *
     * @throws Exception any exception that might be caused by file IO
     */
    private void findAssignments() throws Exception {
        String assignmentName;
        String type;
        String assignmentClass;
        String dueDate;
        String dueTime;
        String additionalInfo;

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
            fileReader.close();
            inputStream.close();

            // get the assignments for this class
            if (assignmentClass.equals(className)) {
                assignments.add(new Assignment(assignmentName, type, assignmentClass, dueDate, dueTime, additionalInfo));
            }
        }
    }

    /**
     * Custom adapter for populating the class details assignment list view
     *
     * @author cjearly
     */
    private class myListAdapter extends ArrayAdapter<Assignment> {
        public myListAdapter() {
            super(ClassDetailsActivity.this, R.layout.class_details_assignments_list, assignments);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.class_details_assignments_list, parent, false);
            }

            // find the assignment to work with.
            Assignment currentAssignment = assignments.get(position);

            // fill the view

            // assignment name:
            TextView assignmentNameTextView = (TextView) itemView.findViewById(R.id.assignment_txtName);
            assignmentNameTextView.setText(currentAssignment.getName());

            // due date:
            TextView dueDateTextView = (TextView) itemView.findViewById(R.id.class_details_assignment_txtDueDate);
            dueDateTextView.setText(currentAssignment.getDueDate());

            // type:
            TextView typeTextView = (TextView) itemView.findViewById(R.id.class_details_assignment_txtType);
            typeTextView.setText(currentAssignment.getType());


            return itemView;
        }
    }
}
