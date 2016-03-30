package com.example.assignmentplanner;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * Activity to add a new class.
 *
 * @author cjearly
 */
public class AddNewClassActivity extends ActionBarActivity {

    /**
     * Name of class
     */
    private String name;
    /**
     * Number of SH
     */
    private int SH;
    /**
     * Days of the week that the class meets
     */
    private String daysOfTheWeek;
    /**
     * Start time
     */
    private String startTime;
    /**
     * End time
     */
    private String endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_class);

        setSHListener();
        setDaysOfTheWeekListener();
        setStartTimeListener();
        setEndTimeListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_new_class, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_accept:
                try {
                    addClass();
                } catch (Exception e) {
                    Toast.makeText(this, "Error. Please try again.", Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(this, ClassesActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Set listener to determine when days of the week text view has focus
     */
    private void setDaysOfTheWeekListener() {
        EditText daysOfTheWeek = (EditText) findViewById(R.id.daysOfTheWeekEditText);

        daysOfTheWeek.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDaySelectorDialog(v);
                }
            }
        });
    }

    /**
     * Set listener to determine when start time text view has focus
     */
    private void setStartTimeListener() {
        EditText startTime = (EditText) findViewById(R.id.startTimeEditText);

        startTime.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showStartTimePickerDialog(v);
                }
            }
        });
    }

    /**
     * Set listener to determine when end time text view has focus
     */
    private void setEndTimeListener() {
        EditText endTime = (EditText) findViewById(R.id.endTimeEditText);

        endTime.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showStopTimePickerDialog(v);
                }
            }
        });
    }

    /**
     * Show the day selector dialog
     *
     * @param view the view
     */
    public void showDaySelectorDialog(View view) {
        DialogFragment dayPicker = new daySelectorFragment();
        dayPicker.show(getSupportFragmentManager(), "dayPicker");
    }

    /**
     * Show the start time picker dialog
     *
     * @param view the view
     */
    public void showStartTimePickerDialog(View view) {
        DialogFragment startTimePicker = new startTimePickerFragment();
        startTimePicker.show(getSupportFragmentManager(), "startTimePicker");
    }

    /**
     * Show the stop time picker dialog
     *
     * @param view the view
     */
    public void showStopTimePickerDialog(View view) {
        DialogFragment stopTimePicker = new stopTimePickerFragment();
        stopTimePicker.show(getSupportFragmentManager(), "stopTimePicker");
    }

    /**
     * Sets listener to determine when SH is set
     */
    private void setSHListener() {
        Spinner shSpinner = (Spinner) findViewById(R.id.SHSpinner);
        shSpinner.setOnItemSelectedListener(new ItemSelectedListener());
    }


    /**
     * Add the class to the classes file saved in internal storage
     */
    private void addClass() throws Exception {
        // Gets the classes file
        File file = getBaseContext().getFileStreamPath("classes");
        // if the file doesn't exist, create it
        if (!(file.exists())) {
            new File(getBaseContext().getFilesDir(), "classes");
        }

        name = ((EditText) findViewById(R.id.classNameEditText)).getText().toString();
        daysOfTheWeek = ((EditText) findViewById(R.id.daysOfTheWeekEditText)).getText().toString();
        startTime = ((EditText) findViewById(R.id.startTimeEditText)).getText().toString();
        endTime = ((EditText) findViewById(R.id.endTimeEditText)).getText().toString();

        // write the assignment info to file
        FileOutputStream output = openFileOutput("classes", Context.MODE_APPEND);
        Formatter writer = new Formatter(output);
        writer.format(name + "\n");
        writer.format(String.valueOf(SH) + "\n");
        writer.format(daysOfTheWeek + "\n");
        writer.format(startTime + "\n");
        writer.format(endTime + "\n");
        writer.close();
        output.close();

        Toast.makeText(this, "Class added.", Toast.LENGTH_LONG).show();
    }

    /**
     * Dialog fragment for selecting the days of the week
     *
     * @author cjearly
     */
    private class daySelectorFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final List<Integer> mSelectedItems = new ArrayList<Integer>();  // Where we track the selected items
            // Create the dialog builder
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    AddNewClassActivity.this);

            // Set the dialog title
            builder.setTitle(R.string.days_of_the_week);

            // set the list of days
            builder.setMultiChoiceItems(R.array.daysOfTheWeek, null,
                    new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which,
                                            boolean isChecked) {
                            if (isChecked) {
                                // If the user checked the item, add it to the
                                // selected items
                                mSelectedItems.add(which);
                            } else if (mSelectedItems.contains(which)) {
                                // Else, if the item is already in the array,
                                // remove it
                                mSelectedItems.remove(Integer.valueOf(which));
                            }
                        }
                    });

            // Add action buttons
            builder.setPositiveButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            String daysOfTheWeek = ""; // store the days of the weeek selected to display
                            // days of the week
                            String[] daysArray = getResources().getStringArray(R.array.daysOfTheWeek);
                            // add the days to daysOfTheWeek
                            for (int i = 0; i < mSelectedItems.size(); i++) {
                                int itemSelected = (Integer) mSelectedItems.get(i); // item index
                                String day = daysArray[itemSelected]; // day selected
                                if (!day.equals("Thursday")) { // if it's not Thursday, use the first char
                                    daysOfTheWeek += day.charAt(0);
                                } else // Thursday uses the first two characters
                                    daysOfTheWeek += day.substring(0, 2);
                            }
                            // set the edit text
                            EditText meetingDays = (EditText) findViewById(R.id.daysOfTheWeekEditText);
                            meetingDays.setText(daysOfTheWeek);
                        }
                    }).setNegativeButton(android.R.string.cancel,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            daySelectorFragment.this.getDialog().cancel();
                        }
                    });

            return builder.create();
        }

    }

    /**
     * Dialog fragment for picking the start time
     *
     * @author cjearly
     */
    private class startTimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Store the time in the due date field for that assignment
            EditText startTime = (EditText) getActivity().findViewById(R.id.startTimeEditText);
            startTime.setText(formatTime(hourOfDay, minute));
        }

        private String formatTime(int hourOfDay, int minute) {
            String time = "";
            String minuteString = "" + minute;
            String am_pm = "AM";

            // If it's past 12, we use PM
            if (hourOfDay >= 12) {
                hourOfDay -= 12;
                am_pm = "PM";
            }
            // if it's midnight, we use 12
            if (hourOfDay == 0) {
                hourOfDay = 12;
            }
            // add the hour
            time += hourOfDay + ":";

            // format for minutes below 10
            if (minute < 10) {
                minuteString = "0" + minute;
            }
            // add the minute
            time += minuteString;
            // add am_pm
            time += " " + am_pm;

            return time;
        }
    }

    /**
     * Dialog fragment for picking the end time.
     *
     * @author cjearly
     */
    private class stopTimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Store the time in the due date field for that assignment
            EditText stopTime = (EditText) getActivity().findViewById(R.id.endTimeEditText);
            stopTime.setText(formatTime(hourOfDay, minute));
        }

        private String formatTime(int hourOfDay, int minute) {
            String time = "";
            String minuteString = "" + minute;
            String am_pm = "AM";

            // If it's past 12, we use PM
            if (hourOfDay >= 12) {
                hourOfDay -= 12;
                am_pm = "PM";
            }
            // if it's midnight, we use 12
            if (hourOfDay == 0) {
                hourOfDay = 12;
            }
            // add the hour
            time += hourOfDay + ":";

            // format for minutes below 10
            if (minute < 10) {
                minuteString = "0" + minute;
            }
            // add the minute
            time += minuteString;
            // add am_pm
            time += " " + am_pm;

            return time;
        }
    }

    /**
     * Listener for the spinner
     *
     * @author cjearly
     */
    private class ItemSelectedListener implements OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {

            String selected = parent.getItemAtPosition(position).toString();
            SH = Integer.parseInt(String.valueOf(selected.charAt(0)));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub

        }

    }
}
