package com.dave.adulting.Tasks;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.dave.adulting.CommonInfrastructure.CommonObject;
import com.dave.adulting.R;
import com.google.firebase.database.DatabaseReference;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static java.util.Calendar.YEAR;

/**
 * Created by Dave - Work on 5/19/2017.
 */

//Static convenience class to display & handle a dialog to add a Task to Firebase
public class TaskDialoger {
    public static void addDialog(final Context ctx, final DatabaseReference ref) {
        View diag = ((LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_add_task, null);
        final EditText description = (EditText) diag.findViewById(R.id.taskDiaDescription);
        final EditText estimate = (EditText) diag.findViewById(R.id.taskDiaDueDate);
        final DuePackage due = new DuePackage();

        View.OnClickListener date = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = GregorianCalendar.getInstance();
                new DatePickerDialog(ctx,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Calendar cal = GregorianCalendar.getInstance();
                                cal.setTimeInMillis(0);
                                cal.set(YEAR, year);
                                cal.set(Calendar.MONTH, month);
                                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                due.mDue = DateFormat.getDateInstance(Task.DATE_FORMAT).format(cal.getTime());
                                estimate.setText(due.mDue);
                                due.mSet = true;
                            }
                        },
                        cal.get(YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        };

        estimate.setOnClickListener(date);
        diag.findViewById(R.id.calendarButton).setOnClickListener(date);

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(diag);
        builder.setPositiveButton("OK", null);
        builder.setNegativeButton("Cancel", null);
        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (description.getText().length() == 0) {
                    description.setError("Required");
                } else {
                    ref.push().setValue(new Task(
                            description.getText().toString(),
                            due.mSet?due.mDue:null
                    ));
                    dialog.dismiss();
                }
            }
        });
    }

    private static class DuePackage{
        String mDue;
        boolean mSet = false;
    }
}
