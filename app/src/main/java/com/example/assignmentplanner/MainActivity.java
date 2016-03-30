package com.example.assignmentplanner;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Main activity for the application to start
 *
 * @author pierotti22
 */
public class MainActivity extends ActionBarActivity {

    /**
     * initializes the view for the start of the app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * on create options menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * On options item selected from the action bar. A
     * button for the calendar, assignmnets, and settings.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case R.id.CalendarMenuButton:
                Intent intent = new Intent(this, AddNewClassActivity.class);
                startActivity(intent);
                return true;

            case R.id.AssignmentMenuButton:
                Intent intent2 = new Intent(this, DayPlan.class);
                startActivity(intent2);
                return true;

            case R.id.SettingsMenuButton:
                Intent intent3 = new Intent(this, FreeTime.class);
                startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method is the main navigation for the application.
     * There a button for the main profile, calendar, assignments,
     * free time, and classes. If the user selects that particular button,
     * then that particular activity is generated.
     *
     * @param view, view from screen
     */
    public void ButtonCommand(View view) {
        Intent intent;

        switch (view.getId()) {


            case R.id.MainProfile:
                intent = new Intent(this, MainProfile.class);
                startActivity(intent);
                break;

            case R.id.Today:
                break;

            case R.id.CalendarButton:
                intent = new Intent(this, CalendarSimple.class);
                startActivity(intent);
                break;

            case R.id.ClassesButton:

                intent = new Intent(this, ClassesActivity.class);
                startActivity(intent);
                break;

            case R.id.AssignmentsButton:
                intent = new Intent(this, AssignmentsActivity.class);
                startActivity(intent);
                break;

            case R.id.FreeTime:
                intent = new Intent(this, FreeTime.class);
                startActivity(intent);
                break;

        }
    }
}

	

