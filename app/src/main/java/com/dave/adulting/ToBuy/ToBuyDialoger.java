package com.dave.adulting.ToBuy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.dave.adulting.CommonInfrastructure.CompletableVH;
import com.dave.adulting.Perishables.Perishable;
import com.dave.adulting.R;
import com.google.firebase.database.DatabaseReference;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Dave - Work on 5/16/2017.
 */

//Static convenience class to display & handle a dialog to add a Shopping item to Firebase
public class ToBuyDialoger {
    public static void addDialog(Context ctx, final DatabaseReference ref) {
        View diag = ((LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_add_shopping, null);
        final EditText description = (EditText) diag.findViewById(R.id.shopDiaDescription);

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(diag);
        builder.setPositiveButton("OK", null);
        builder.setNegativeButton("Cancel", null);
        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (description.getText().length() != 0) {
                    Calendar cal = GregorianCalendar.getInstance();
                    ref.push().setValue(new ToBuyItem(
                            description.getText().toString(),
                            DateFormat.getDateInstance(ToBuyItem.DATE_FORMAT).format(cal.getTime())
                    ));
                    dialog.dismiss();
                } else {
                    if (description.getText().length() == 0) {
                        description.setError("Required");
                    }
                }
            }
        });
    }
}
