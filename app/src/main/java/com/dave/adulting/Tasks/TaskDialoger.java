package com.dave.adulting.Tasks;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.dave.adulting.R;

/**
 * Created by Dave - Work on 5/19/2017.
 */

public class TaskDialoger {
    public static void addDialog(Context ctx, final TaskAddListener listener){
        View diag = ((LayoutInflater)ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_add_task,null);
        final EditText description = (EditText) diag.findViewById(R.id.taskDiaDescription);
        //final EditText estimate = (EditText) diag.findViewById(R.id.perDiaEstimate);

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(diag);
        builder.setPositiveButton("OK",null);
        builder.setNegativeButton("Cancel",null);
        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(description.getText().length()==0){
                    description.setError("Required");
                } else {
                    listener.addTask(new Task(
                            description.getText().toString(),
                            null
                    ));
                    dialog.dismiss();
                }

            }
        });
    }

    interface TaskAddListener{
        void addTask(Task task);
    }
}
