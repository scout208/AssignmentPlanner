package com.example.assignmentplanner;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Formatter;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuItem;
import android.view.accessibility.*;


public class CalendarSimple extends Activity {

    /**
     * holds year from calendar view
     */
    private int thisYear;
    /**
     * holds month from calendar view
     */
    private int thisMonth;
    /**
     * holds day from calendar view
     */
    private int thisDay;
    /**
     * sends with intent to dayPlan
     */
    private String DateToSend;

    @Override
    /**
     * sets up the calendarView and adds a listener to the calendar.
     *
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);

        final CalendarView calendarView = (CalendarView) findViewById(R.id.calendar);
        calendarView.setShowWeekNumber(false);


        calendarView.setOnDateChangeListener(new OnDateChangeListener() {

            @Override
            /**
             * anonymous inner class to add a listener to the calendar view.
             */
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                thisYear = year;
                thisMonth = month;
                thisDay = dayOfMonth;
                DateToSend = formatDate(thisYear, thisMonth, thisDay);


                Toast.makeText(view.getContext(), "year " + year + "month " + month + "day " + dayOfMonth, Toast.LENGTH_LONG).show();

                try {
                    startDayActivity();
                } catch (Exception exception) {

                }
            }

        });
    }

    @Override
    /**
     * on create options menu
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calendar_view, menu);
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
     * starts the day plan activity
     */
    public void startDayActivity() {
        Intent intent = new Intent(this, DayPlan.class);
        intent.putExtra("daySelected", DateToSend);
        startActivity(intent);
    }

    /**
     * Formats the inputs and sends them back as one date
     *
     * @param year,  year from calendar
     * @param month, month from calendar
     * @param day,   day from calendar
     * @return all three combined into one date
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
                date += "Sept ";
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
