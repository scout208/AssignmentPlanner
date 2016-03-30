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

import com.example.layout.Class;

/**
 * Activity for displaying the classes
 *
 * @author cjearly
 */
public class ClassesActivity extends ActionBarActivity {

    /**
     * The classes
     */
    private List<Class> classes = new ArrayList<Class>();
    /**
     * Class name
     */
    private String className;
    /**
     * semester hours
     */
    private int SH;
    /**
     * The days of the week that the class meets
     */
    private String daysOfTheWeek;
    /**
     * The time that the class starts each day.
     */
    private String startTime;
    /**
     * The time that the class ends each day
     */
    private String endTime;
    /**
     * The time range that the class meets
     */
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);

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
        getMenuInflater().inflate(R.menu.classes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_addNewClass:
                openAddNew(item);
                return true;
            case R.id.action_settingsClass:
                //openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Called when the user clicks the add button in the action bar. Start activity to add a class.
     *
     * @param item menu item selected
     */
    private void openAddNew(MenuItem item) {
        Intent intent = new Intent(this, AddNewClassActivity.class);
        startActivity(intent);
    }

    /**
     * Find classes by reading from the classes file and adding them to the array
     *
     * @throws Exception any exception caused by file IO
     */
    private void populateList() throws Exception {

        // get the file input stream
        FileInputStream inputStream = openFileInput("classes");

        // create scanner for reading
        Scanner fileReader = new Scanner(inputStream);

        while (fileReader.hasNextLine()) {
            // get class name, SH, daysOfTheWeek,and times
            className = fileReader.nextLine();
            SH = Integer.parseInt(fileReader.nextLine());
            daysOfTheWeek = fileReader.nextLine();
            startTime = fileReader.nextLine();
            endTime = fileReader.nextLine();

            // add the class to the list
            classes.add(new Class(className, SH, daysOfTheWeek, startTime, endTime));
        }

        fileReader.close();
        inputStream.close();
    }

    /**
     * Add the classes to the list view using myListAdapter
     */
    private void populateListView() {
        ArrayAdapter<Class> adapter = new myListAdapter();
        ListView list = (ListView) findViewById(R.id.classesList);
        list.setAdapter(adapter);
    }

    /**
     * Handle the events to display the class details
     */
    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.classesList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long id) {

                // Get the assignment selected
                Class clickedClass = classes.get(position);

                // pass the data to the details activity
                Intent intent = new Intent(ClassesActivity.this, ClassDetailsActivity.class);
                intent.putExtra("className", clickedClass.getClassName());
                intent.putExtra("SH", clickedClass.getSH());
                intent.putExtra("daysOfTheWeek", clickedClass.getDaysOfTheWeek());
                intent.putExtra("time", time);
                startActivity(intent);
            }
        });
    }

    /**
     * Custom adapter for inflating the layout for the list view
     *
     * @author anime_000
     */
    private class myListAdapter extends ArrayAdapter<Class> {
        public myListAdapter() {
            super(ClassesActivity.this, R.layout.class_list_item_view, classes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.class_list_item_view, parent, false);
            }

            // find the assignment to work with.
            Class currentClass = classes.get(position);

            // fill the view
            // class name:
            TextView classNameTextView = (TextView) itemView.findViewById(R.id.item_txtClassName);
            classNameTextView.setText(currentClass.getClassName());

            // SH:
            TextView SHTextView = (TextView) itemView.findViewById(R.id.item_txtSH);
            SHTextView.setText(String.valueOf(currentClass.getSH()) + " SH");

            // Day of the week:
            TextView daysOfTheWeekTextView = (TextView) itemView.findViewById(R.id.item_txtDaysOfTheWeek);
            daysOfTheWeekTextView.setText(currentClass.getDaysOfTheWeek());

            // time:
            TextView timeTextView = (TextView) itemView.findViewById(R.id.item_txtTime);
            startTime = currentClass.getStartTime();
            endTime = currentClass.getEndTime();
            time = startTime + " - " + endTime;
            timeTextView.setText(time);

            return itemView;
        }

    }
}
