package com.example.layout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

import com.example.assignmentplanner.R;

/**
 * Fragment for selecting the type of assignment
 *
 * @author cjearly
 */
public class TypeListFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Instantiate an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set the dialog characteristics
        builder.setTitle(R.string.assignment_type)
                .setItems(R.array.types_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        EditText type = (EditText) getActivity().findViewById(R.id.typeEditText);
                        type.setText(getResources().getStringArray(R.array.types_array)[which]);
                    }
                });

        // retrun the AlertDialog created by the builder
        return builder.create();
    }
}
