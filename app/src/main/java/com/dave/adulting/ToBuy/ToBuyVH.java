package com.dave.adulting.ToBuy;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dave.adulting.CommonInfrastructure.CompletableVH;
import com.dave.adulting.Perishables.PerishableController;
import com.dave.adulting.Perishables.PerishableDialoger;
import com.dave.adulting.R;
import com.google.firebase.database.DatabaseReference;

import java.util.Date;

/**
 * Created by Dave - Work on 5/22/2017.
 */

class ToBuyVH extends CompletableVH {
    private TextView mTitle, mDate;
    private ImageButton mBuy, mRemove;

    public ToBuyVH(View itemView) {
        super(itemView);
        mTitle = (TextView) itemView.findViewById(R.id.listTitle);
        mDate = (TextView) itemView.findViewById(R.id.listLine1);
        mBuy = (ImageButton) itemView.findViewById(R.id.cartAdd);
        mRemove = (ImageButton) itemView.findViewById(R.id.cartRemove);

        mBuy.setOnClickListener(this);
        mRemove.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() != R.id.cartRemove) {
            DatabaseReference ref = mAdapter.getRef(getAdapterPosition()).getParent().getParent()
                    .child(PerishableController.KEY);
            PerishableDialoger.addDialog(v.getContext(),ref,mTitle.getText().toString());
        }
        mAdapter.getRef(getAdapterPosition()).removeValue();
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setLine1(long added) {
        mDate.setText(mDF.format(new Date(added)));
    }
}
