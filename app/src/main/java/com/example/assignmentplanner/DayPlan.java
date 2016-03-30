package com.example.assignmentplanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

import com.example.layout.Assignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class is the day view class that corresponds to a particular day for any
 * given click on the calendar by the user. The day view will display the
 * classes and assignments on that given day.
 *
 * @author pierotti22
 */
@SuppressWarnings("unused")
public class DayPlan extends ActionBarActivity {
    /**
     * date from calendar view
     */
    private String date;
    /**
     * assignment name from assignments file
     */
    private String assignmentName;
    /**
     * due date from assignments file
     */
    private String dueDate;
    /**
     * class name from assignments file
     */
    private String className;
    /**
     * days of the week from classes file
     */
    private String daysOfWeek;
    /**
     * start time of class from classes file
     */
    private String startTime;
    /**
     * due time for assignments from assignments file
     */
    private String dueTime;
    /**
     * additional info from assignments file
     */
    private String additionalInfo;
    /**
     * day of the week as in Monday, Tuesday, etc.
     */
    private String dayOFWeek;
    /**
     * text to be displayed in edit text
     */
    private String textToSet = "";

    @SuppressWarnings("resource")
    @SuppressLint("DefaultLocale")
    @Override
    /**
     * On create bundle sets up the day view. Also converts the day chosen by
     * the user to the actual day of the week, so that the class can include the
     * classes input the user and the assignments
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_plan);

        SetTextView();
        SetAssignmentsOnDayView();

        // converts strings to integer representation

        String Stringmonth = date.substring(0, date.indexOf(" "));//
        int intday = new Scanner(date).useDelimiter("\\D+").nextInt();//
        String Stringyear = date.substring(date.lastIndexOf(' ') + 1);
        int intYear = Integer.parseInt(Stringyear);
        int intMonth = ConvertMonthToInt(Stringmonth);

        String mm;
        String dd;
        if (intMonth < 10) {
            mm = String.format("%02d", intMonth);
        } else {
            mm = String.valueOf(intMonth);
        }
        if (intday < 10) {
            dd = String.format("%02d", intday);
        } else {
            dd = String.valueOf(intday);
        }

        String yyyy = String.valueOf(intYear);
        String toSend = yyyy + "/" + mm + "/" + dd;

        try {
            // gets correct day of the day
            dayOFWeek = getDayOfWeek(toSend);

        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        SetClassesOnDayView();
    }

    /**
     * @param stringDate, date
     * @return day of the week
     * @throws java.text.ParseException
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDayOfWeek(String stringDate) throws java.text.ParseException {
        String dayOfWeek;

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        Calendar calender = new GregorianCalendar();

        try {
            date = format.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
            dayOfWeek = null;
        }

        calender.setTime(date);

        switch (calender.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                dayOfWeek = "Monday";
                break;
            case Calendar.TUESDAY:
                dayOfWeek = "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                dayOfWeek = "Wednesday";
                break;
            case Calendar.THURSDAY:
                dayOfWeek = "Thursday";
                break;
            case Calendar.FRIDAY:
                dayOfWeek = "Friday";
                break;
            case Calendar.SATURDAY:
                dayOfWeek = "Saturday";
                break;
            case Calendar.SUNDAY:
                dayOfWeek = "Sunday";
                break;

            default:
                dayOfWeek = null;
                break;
        }

        return dayOfWeek;
    }

    @Override
    /**
     * on create options Menu
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.day_plan, menu);
        return true;
    }

    /**
     * This method takes the string version of the month and returns the integer
     * representation
     *
     * @param month, month input
     * @return integer representation of the month
     */
    public int ConvertMonthToInt(String month) {
        if (month.equals("Jan"))
            return 1;
        else if (month.equals("Feb"))
            return 2;
        else if (month.equals("Mar"))
            return 3;
        else if (month.equals("Apr"))
            return 4;
        else if (month.equals("May"))
            return 5;
        else if (month.equals("June"))
            return 6;
        else if (month.equals("July"))
            return 7;
        else if (month.equals("Aug"))
            return 8;
        else if (month.equals("Sept"))
            return 9;
        else if (month.equals("Oct"))
            return 10;
        else if (month.equals("Nov"))
            return 11;
        else if (month.equals("Dec"))
            return 12;
        else
            return 0;
    }

    /**
     * on options Item selected menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * initializes the date at the top of the day view
     */
    public void SetTextView() {
        final TextView myText = (TextView) findViewById(R.id.DateText);
        Intent intent = getIntent();
        date = intent.getStringExtra("daySelected");
        myText.setText(date);

    }

    /**
     * this method reads the assignments in from a file and if the due date is
     * equal to the date chosen by the user, the assignment will be displayed on
     * the day view
     */
    public void SetAssignmentsOnDayView() {

        FileInputStream inputStream;

        try {
            inputStream = openFileInput("assignments");
            Scanner fileReader = new Scanner(inputStream);
            File file = getFileStreamPath("assignments");

            if (file.length() != 0) {
                while (fileReader.hasNextLine()) {
                    assignmentName = fileReader.nextLine();
                    fileReader.nextLine();
                    fileReader.nextLine();
                    dueDate = fileReader.nextLine();
                    dueTime = fileReader.nextLine();

                    int flag = 0;
                    while (flag != -1) {
                        String nextLine = fileReader.nextLine();
                        if (!nextLine.equals("end")) {
                            additionalInfo += nextLine + "\n";
                        } else
                            flag = -1;
                    }
                    if (dueDate.equals(date)) {
                        textToSet = (" " + assignmentName + " is due ");
                        setBlock();
                    }
                }
            }
            fileReader.close();
            inputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method reads in the classes that user is taking from the classes
     * file, and if the day from the calendar selected corresponds to the
     * correct day of the week then the name of the class will be displayed.
     */
    public void SetClassesOnDayView() {

        FileInputStream inputStream;

        try {
            inputStream = openFileInput("classes");
            Scanner fileReader = new Scanner(inputStream);
            File file = getFileStreamPath("classes");

            if (file.length() != 0) {
                while (fileReader.hasNextLine()) {
                    className = fileReader.nextLine();
                    fileReader.nextLine();
                    daysOfWeek = fileReader.nextLine();
                    startTime = fileReader.nextLine();
                    fileReader.nextLine();

                    dueTime = startTime;
                    textToSet = className;
                    char[] cArray = daysOfWeek.toCharArray();

                    for (int i = 0; i < cArray.length; i++) {
                        if ((cArray[i] == 'M') && (dayOFWeek.equals("Monday")))
                            setBlock();
                        if ((cArray[i] == 'T') && (dayOFWeek.equals("Tuesday")))
                            setBlock();
                        if ((cArray[i] == 'W') && (dayOFWeek.equals("Wednesday")))
                            setBlock();
                        if ((cArray[i] == 'T') && (cArray[i + 1] == 'h') && (dayOFWeek.equals("Thursday")))
                            setBlock();
                        if ((cArray[i] == 'F') && (dayOFWeek.equals("Friday")))
                            setBlock();
                    }
                }
            }
            fileReader.close();
            inputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * this method initializes the text field in the dayview. The method decides
     * where the text should go based off of the private instance variable
     * dueTime.
     */
    public void setBlock() {
        String amOrPm;
        String timeOfDay;

        amOrPm = dueTime.substring(Math.max(dueTime.length() - 2, 0));
        timeOfDay = dueTime.substring(0, dueTime.indexOf(":"));

        if (timeOfDay.equals("8") && amOrPm.equals("AM")) {
            final EditText myText = (EditText) findViewById(R.id.eightmorningedittext);
            myText.setText(textToSet);
        } else if (timeOfDay.equals("9") && amOrPm.equals("AM")) {
            final EditText myText = (EditText) findViewById(R.id.ninemorningedittext);
            myText.setText(textToSet);
        } else if (timeOfDay.equals("10") && amOrPm.equals("AM")) {
            final EditText myText = (EditText) findViewById(R.id.tenmorningedittext);
            myText.setText(textToSet);
        } else if (timeOfDay.equals("11") && amOrPm.equals("AM")) {
            final EditText myText = (EditText) findViewById(R.id.elevenmorningedittext);
            myText.setText(textToSet);
        } else if (timeOfDay.equals("12") && amOrPm.equals("PM")) {
            final EditText myText = (EditText) findViewById(R.id.noonmorningedittext);
            myText.setText(textToSet);
        } else if (timeOfDay.equals("1") && amOrPm.equals("PM")) {
            final EditText myText = (EditText) findViewById(R.id.oneafternoonedittext);
            myText.setText(textToSet);
        } else if (timeOfDay.equals("2") && amOrPm.equals("PM")) {
            final EditText myText = (EditText) findViewById(R.id.twoafternoonedittext);
            myText.setText(textToSet);
        } else if (timeOfDay.equals("3") && amOrPm.equals("PM")) {
            final EditText myText = (EditText) findViewById(R.id.threeafternoonedittext);
            myText.setText(textToSet);
        } else if (timeOfDay.equals("4") && amOrPm.equals("PM")) {
            final EditText myText = (EditText) findViewById(R.id.fourafternoonedittext);
            myText.setText(textToSet);
        } else if (timeOfDay.equals("5") && amOrPm.equals("PM")) {
            final EditText myText = (EditText) findViewById(R.id.fiveafternoonedittext);
            myText.setText(textToSet);
        } else if (timeOfDay.equals("6") && amOrPm.equals("PM")) {
            final EditText myText = (EditText) findViewById(R.id.sixafternoonedittext);
            myText.setText(textToSet);
        } else if (timeOfDay.equals("7") && amOrPm.equals("PM")) {
            final EditText myText = (EditText) findViewById(R.id.sevenafternoonedittext);
            myText.setText(textToSet);
        } else if (timeOfDay.equals("8") && amOrPm.equals("PM")) {
            final EditText myText = (EditText) findViewById(R.id.eightafternoonedittext);
            myText.setText(textToSet);
        } else if (timeOfDay.equals("9") && amOrPm.equals("PM")) {
            final EditText myText = (EditText) findViewById(R.id.nineafternoonedittext);
            myText.setText(textToSet);
        } else if (timeOfDay.equals("10") && amOrPm.equals("PM")) {
            final EditText myText = (EditText) findViewById(R.id.tenafternoonedittext);
            myText.setText(textToSet);
        } else if (timeOfDay.equals("11") && amOrPm.equals("PM")) {
            final EditText myText = (EditText) findViewById(R.id.elvenafternoonedittext);
            myText.setText(textToSet);
        } else if (timeOfDay.equals("12") && amOrPm.equals("AM")) {
            final EditText myText = (EditText) findViewById(R.id.midnightafternoonedittext);
            myText.setText(textToSet);
        } else if (timeOfDay.equals("1") && amOrPm.equals("AM")) {
            final EditText myText = (EditText) findViewById(R.id.onemorningedittext);
            myText.setText(textToSet);
        }
    }
}
