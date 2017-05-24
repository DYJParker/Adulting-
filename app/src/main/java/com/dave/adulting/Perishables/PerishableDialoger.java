package com.dave.adulting.Perishables;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.dave.adulting.CommonInfrastructure.CommonObject;
import com.dave.adulting.CommonInfrastructure.CompletableVH;
import com.dave.adulting.R;
import com.google.firebase.database.DatabaseReference;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Dave - Work on 5/16/2017.
 */

public class PerishableDialoger extends CommonObject {
    public static void addDialog(Context ctx, final DatabaseReference ref, @Nullable String title) {
        View diag = ((LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_add_perishable, null);
        final EditText description = (EditText) diag.findViewById(R.id.perDiaDescription);
        final EditText estimate = (EditText) diag.findViewById(R.id.perDiaEstimate);

        if(title != null) description.setText(title);

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(diag);
        builder.setPositiveButton("OK", null);
        builder.setNegativeButton("Cancel", null);
        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (description.getText().length() != 0 && estimate.getText().length() != 0) {
                    Calendar cal = GregorianCalendar.getInstance();
                    DateFormat df = DateFormat.getDateInstance(Perishable.DATE_FORMAT);
                    String added = df.format(cal.getTime());
                    cal.add(Calendar.DAY_OF_YEAR,
                            Integer.parseInt(estimate.getText().toString()));
                    ref.push().setValue(new Perishable(
                            description.getText().toString(),
                            df.format(cal.getTime()),
                            added
                    ));
                    dialog.dismiss();
                } else {
                    if (description.getText().length() == 0) {
                        description.setError("Required");
                    }
                    if (estimate.getText().length() == 0) {
                        estimate.setError("Why even bother?");
                    }
                }
            }
        });
    }
}
