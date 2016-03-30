package com.example.layout;

import java.util.Calendar;

import com.example.assignmentplanner.R;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

/**
 * Dialog fragment for picking the due time
 *
 * @author cjearly
 */
public class DueTimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

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
        EditText dueTime = (EditText) getActivity().findViewById(R.id.dueTimeEditText);
        dueTime.setText(formatTime(hourOfDay, minute));
    }

    /**
     * Format the time so it looks like we want it
     *
     * @param hourOfDay the hour chosen
     * @param minute    the minute chosen
     * @return formatted time
     */
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
