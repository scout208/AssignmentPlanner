package com.example.layout;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.assignmentplanner.R;

/**
 * Date picker fragment for picking the due date
 *
 * @author cjearly
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

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
        EditText dueDate = (EditText) getActivity().findViewById(R.id.dueDateEditText);
        dueDate.setText(formatDate(year, month, day));
    }

    /**
     * Format the date
     *
     * @param year  the year
     * @param month the month
     * @param day   the day
     * @return formatted date
     */
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
