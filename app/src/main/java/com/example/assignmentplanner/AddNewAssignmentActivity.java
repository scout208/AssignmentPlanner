package com.example.assignmentplanner;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Formatter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.layout.ClassListFragment;
import com.example.layout.TypeListFragment;

/**
 * Activity to add a new assignment.
 *
 * @author cjearly
 */
public class AddNewAssignmentActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_assignment);

        setTypeListener();
        setClassListener();
        setDueDateListener();
        setDueTimeListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_new_assignment, menu);
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
                    addAssignment();
                } catch (Exception e) {
                    Toast.makeText(this, "Error. Please try again.",
                            Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(this, AssignmentsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Show the type list dialog. Called when the assignment type text view is
     * focused on.
     */
    public void setTypeListener() {
        EditText type = (EditText) findViewById(R.id.typeEditText);

        type.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showTypePickerDialog(v);
                }
            }
        });
    }

    /**
     * Show the class list dialog. Called when the class text view is focused
     * on.
     */
    public void setClassListener() {
        EditText assignmentClass = (EditText) findViewById(R.id.classEditText);

        assignmentClass.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showClassPickerDialog(v);
                }
            }
        });
    }

    /**
     * Show the date picker dialog. Called when the due date text view is
     * focused on.
     */
    public void setDueDateListener() {
        EditText dueDate = (EditText) findViewById(R.id.dueDateEditText);

        dueDate.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDueDatePickerDialog(v);
                }
            }
        });
    }

    /**
     * Show the time picker dialog. Called when the due time text view is
     * clicked.
     */
    public void setDueTimeListener() {
        EditText dueTime = (EditText) findViewById(R.id.dueTimeEditText);

        dueTime.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDueTimePickerDialog(v);
                }
            }
        });
    }

    /**
     * Shows the type picker dialog
     *
     * @param view the view
     */
    public void showTypePickerDialog(View view) {
        DialogFragment newFragment = new TypeListFragment();
        newFragment.show(getSupportFragmentManager(), "typeList");
    }

    /**
     * Shows the class picker dialog
     *
     * @param view the view
     */
    public void showClassPickerDialog(View view) {
        DialogFragment newFragment = new ClassListFragment();
        newFragment.show(getSupportFragmentManager(), "classList");
    }

    /**
     * Shows the due date picker dialog
     *
     * @param view the view
     */
    public void showDueDatePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * Shows the due time picker dialog
     *
     * @param view the view
     */
    public void showDueTimePickerDialog(View view) {
        DialogFragment newFragment = new DueTimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    /**
     *
     */
    private void addAssignment() throws Exception {
        // Gets the assignments file
        File file = getBaseContext().getFileStreamPath("assignments");
        // if the file doesn't exist, create it
        if (!(file.exists())) {
            new File(getBaseContext().getFilesDir(), "assignments");
        }
        // write the assignment info to file
        FileOutputStream output = openFileOutput("assignments",
                Context.MODE_APPEND);
        Formatter writer = new Formatter(output);
        writer.format(((EditText) findViewById(R.id.assignmentNameEditText))
                .getText().toString() + "\n");
        writer.format(((EditText) findViewById(R.id.typeEditText)).getText()
                .toString() + "\n");
        writer.format(((EditText) findViewById(R.id.classEditText)).getText()
                .toString() + "\n");
        writer.format(((EditText) findViewById(R.id.dueDateEditText)).getText()
                .toString() + "\n");
        writer.format(((EditText) findViewById(R.id.dueTimeEditText)).getText()
                .toString() + "\n");
        writer.format(((EditText) findViewById(R.id.additionalInfoEditText))
                .getText().toString() + "\n");
        writer.format("end" + "\n");
        writer.close();
        output.close();

        Toast.makeText(this, "Assignment added.", Toast.LENGTH_LONG).show();
    }

    private class DueTimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

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
            EditText dueTime = (EditText) getActivity().findViewById(
                    R.id.dueTimeEditText);
            dueTime.setText(formatTime(hourOfDay, minute));
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

    private class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            EditText dueDate = (EditText) getActivity().findViewById(
                    R.id.dueDateEditText);
            dueDate.setText(formatDate(year, month, day));
        }

        private String formatDate(int year, int month, int day) {
            String date = "";

            switch (month) {
                case 0:
                    date += "Jan ";
                    break;
                case 1:
                    date += "Feb ";
                    break;
                case 2:
                    date += "Mar ";
                    break;
                case 3:
                    date += "Apr ";
                    break;
                case 4:
                    date += "May ";
                    break;
                case 5:
                    date += "June ";
                    break;
                case 6:
                    date += "July ";
                    break;
                case 7:
                    date += "Aug ";
                    break;
                case 8:
                    date += "Sep ";
                    break;
                case 9:
                    date += "Oct ";
                    break;
                case 10:
                    date += "Nov ";
                    break;
                case 11:
                    date += "Dec ";
                    break;
            }

            date += day + ", " + year;

            return date;
        }
    }

}
