package com.example.assignmentplanner;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Formatter;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * this class is for the user to enter which times he
 * or she would like to study, then they are written
 * to a file called free time
 *
 * @author pierotti22
 */
public class FreeTime extends ActionBarActivity {

    /**
     * spinner for monday
     */
    private Spinner Monday;
    /**
     * spinner for tuesday
     */
    private Spinner Tuesday;
    /**
     * spinner for wednesday
     */
    private Spinner Wednesday;
    /**
     * spinner for thursday
     */
    private Spinner Thursday;
    /**
     * spinner for friday
     */
    private Spinner Friday;
    /**
     * spinner for saturday
     */
    private Spinner Saturday;
    /**
     * spinner for sunday
     */
    private Spinner Sunday;
    /**
     * default string for monday
     */
    private String mondayStudyTime = "I Don't Care";
    /**
     * default string for tuesday
     */
    private String tuesdayStudyTime = "I Don't Care";
    /**
     * default string for wednesday
     */
    private String wednesdayStudyTime = "I Don't Care";
    /**
     * default string for thursday
     */
    private String thursdayStudyTime = "I Don't Care";
    /**
     * default string for friday
     */
    private String fridayStudyTime = "I Don't Care";
    /**
     * default string for saturday
     */
    private String saturdayStudyTime = "I Don't Care";
    /**
     * default string for sunday
     */
    private String sundayStudyTime = "I Don't Care";


    /**
     * The onCreateBundle initializes all of the spinners to
     * their appropriate IDs. Also a listener is added to each
     * spinner.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_time);

        Monday = (Spinner) findViewById(R.id.spinner1);
        Monday.setOnItemSelectedListener(new ItemSelectedListener());
        Tuesday = (Spinner) findViewById(R.id.spinner2);
        Tuesday.setOnItemSelectedListener(new ItemSelectedListener());
        Wednesday = (Spinner) findViewById(R.id.spinner3);
        Wednesday.setOnItemSelectedListener(new ItemSelectedListener());
        Thursday = (Spinner) findViewById(R.id.spinner4);
        Thursday.setOnItemSelectedListener(new ItemSelectedListener());
        Friday = (Spinner) findViewById(R.id.spinner5);
        Friday.setOnItemSelectedListener(new ItemSelectedListener());
        Saturday = (Spinner) findViewById(R.id.spinner6);
        Saturday.setOnItemSelectedListener(new ItemSelectedListener());
        Sunday = (Spinner) findViewById(R.id.spinner7);
        Sunday.setOnItemSelectedListener(new ItemSelectedListener());


    }

    /**
     * on create options menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.free_time, menu);
        return true;
    }

    /**
     * on options item selected
     */
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
     * this method is a listener for each spinner, implements
     * on item selected listener
     *
     * @author pierotti22
     */
    private class ItemSelectedListener implements OnItemSelectedListener {
        @Override
        /**
         * gets the item selected by the listener.
         */
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            String selected = parent.getItemAtPosition(position).toString();

            //Toast.makeText(FreeTime.this, selected, Toast.LENGTH_SHORT).show();


            switch (parent.getId()) {

                case R.id.spinner1:
                    mondayStudyTime = selected;
                    break;

                case R.id.spinner2:
                    tuesdayStudyTime = selected;
                    break;

                case R.id.spinner3:
                    wednesdayStudyTime = selected;
                    break;

                case R.id.spinner4:
                    thursdayStudyTime = selected;
                    break;

                case R.id.spinner5:
                    fridayStudyTime = selected;
                    break;

                case R.id.spinner6:
                    saturdayStudyTime = selected;
                    break;

                case R.id.spinner7:
                    sundayStudyTime = selected;
                    break;

            }

            try {
                updateStudyTimesOnFile();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        /**
         * on nothing selected for the spinner (default values will be assigned)
         */
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

        /**
         * writes the values from the spinners to the FreeTime file
         *
         * @throws Exception generic exception
         */
        public void updateStudyTimesOnFile() throws Exception {

            File file = getBaseContext().getFileStreamPath("FreeTime");

            if (!(file.exists())) {
                new File(getBaseContext().getFilesDir(), "FreeTime");
            }

            try {
                FileOutputStream output = openFileOutput("FreeTime", Context.MODE_PRIVATE);
                Formatter write = new Formatter(output);
                write.format(mondayStudyTime + "\n");
                write.format(tuesdayStudyTime + "\n");
                write.format(wednesdayStudyTime + "\n");
                write.format(thursdayStudyTime + "\n");
                write.format(fridayStudyTime + "\n");
                write.format(saturdayStudyTime + "\n");
                write.format(sundayStudyTime + "\n");
                write.close();
                output.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

