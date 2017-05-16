package com.dave.adulting.Perishables;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dave.adulting.R;

/**
 * Created by Dave - Work on 5/16/2017.
 */

class PerishableVH extends RecyclerView.ViewHolder {
    TextView mTitle, mLine1, mLine2;

    public PerishableVH(View itemView) {
        super(itemView);

        mTitle = (TextView) itemView.findViewById(R.id.listTitle);
        mLine1 = (TextView) itemView.findViewById(R.id.listLine1);
        mLine2 = (TextView) itemView.findViewById(R.id.listLine2);
    }
}
