package com.dave.adulting.Perishables;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dave.adulting.R;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Dave - Work on 5/16/2017.
 */

class PerishableVH extends RecyclerView.ViewHolder {
    TextView mTitle, mLine1, mLine2;
    DateFormat mDF;

    public PerishableVH(View itemView) {
        super(itemView);

        mTitle = (TextView) itemView.findViewById(R.id.listTitle);
        mLine1 = (TextView) itemView.findViewById(R.id.listLine1);
        mLine2 = (TextView) itemView.findViewById(R.id.listLine2);
        mDF = DateFormat.getDateInstance(DateFormat.SHORT);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setLine1(long expires) {
        mLine1.setText(mDF.format(new Date(expires)));
    }

    public void setLine2(long added) {
        mLine2.setText(mDF.format(new Date(added)));
    }
}
