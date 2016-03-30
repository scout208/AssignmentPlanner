package com.example.layout;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignmentplanner.AddNewClassActivity;
import com.example.assignmentplanner.R;

/**
 * Dialog fragment for choosing the class
 *
 * @author cjearly
 */
public class ClassListFragment extends DialogFragment {

    /**
     * Classes to display
     */
    private List<String> classes = new ArrayList<String>();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Instantiate an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        try {
            populateList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        classes.add("Add a new class");

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, classes);

        // Set the dialog characteristics
        builder.setTitle(R.string.assignment_class).setAdapter(adapter,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (!(adapter.getItem(which).equals("Add a new class"))) {
                            EditText classEditText = (EditText) getActivity().findViewById(R.id.classEditText);
                            classEditText.setText(adapter.getItem(which));
                        } else if (adapter.getItem(which).equals("Add a new class")) {
                            Intent intent = new Intent(getActivity(), AddNewClassActivity.class);
                            startActivity(intent);
                        } else
                            Toast.makeText(getActivity(), "Error. Please try again.", Toast.LENGTH_LONG).show();
                    }
                });

        // return the AlertDialog created by the builder
        return builder.create();

    }

    /**
     * Read the classes from the internal storage to add them to the list
     *
     * @throws Exception any exception caused by file IO
     */
    private void populateList() throws Exception {
        // add each class in the file to the array
        FileInputStream inputStream = getActivity().openFileInput("classes");
        Scanner fileReader = new Scanner(inputStream);
        while (fileReader.hasNextLine()) {
            classes.add(fileReader.nextLine());
            fileReader.nextLine();
            fileReader.nextLine();
            fileReader.nextLine();
            fileReader.nextLine();
        }
        fileReader.close();
        inputStream.close();
    }
}
