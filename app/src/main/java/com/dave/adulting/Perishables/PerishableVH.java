package com.dave.adulting.Perishables;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dave.adulting.CommonInfrastructure.CompletableVH;
import com.dave.adulting.R;
import com.dave.adulting.ToBuy.ToBuyController;
import com.dave.adulting.ToBuy.ToBuyItem;
import com.google.firebase.database.DatabaseReference;

import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Dave - Work on 5/16/2017.
 */

class PerishableVH extends CompletableVH {
    private static final String TAG = "PerishableVH";
    TextView mTitle, mLine1, mLine2;

    public PerishableVH(View itemView) {
        super(itemView);

        mTitle = (TextView) itemView.findViewById(R.id.listTitle);
        mLine1 = (TextView) itemView.findViewById(R.id.listLine1);
        mLine2 = (TextView) itemView.findViewById(R.id.listLine2);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setLine1(String expires) {
        mLine1.setText(expires);
        setCriticalityColor(expires, mTitle, mLine1);
    }

    public void setLine2(String added) {
        mLine2.setText(added);
    }

    @Override
    public void onClick(View v) {
        final Perishable temp = ((Perishable) mAdapter.getItem(getAdapterPosition()));
        final DatabaseReference shopping = mAdapter.getRef(getAdapterPosition())
                .getParent().getParent().child(ToBuyController.KEY).push();
        shopping.setValue(new ToBuyItem(
                temp.getTitle(),
                DateFormat.getDateInstance(ToBuyItem.DATE_FORMAT).format(GregorianCalendar.getInstance().getTime())
        ));

        Snackbar.make(v, "Moved " + temp.getTitle() + " to the shopping list", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shopping.getParent().getParent().child(PerishableController.KEY).push().setValue(temp);
                        shopping.removeValue();
                    }
                }).show();
        mAdapter.getRef(getAdapterPosition()).removeValue();
    }
}
