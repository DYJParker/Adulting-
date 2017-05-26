package com.dave.adulting.ToBuy;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.test.mock.MockContext;
import android.view.View;
import android.view.ViewStub;
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

//ViewHolder for Shopping items.
public class ToBuyVH extends CompletableVH {
    private TextView mTitle, mDate;
    private ImageButton mBuy, mRemove;

    //Pass desired child layout to super, retrieve ID references on construction, sets listener for
    //uniquely paired completion buttons.
    public ToBuyVH(View itemView) {
        super(itemView, R.layout.shopping_list_item);
        mTitle = (TextView) itemView.findViewById(R.id.listTitle);
        mDate = (TextView) itemView.findViewById(R.id.listLine1);
        mBuy = (ImageButton) itemView.findViewById(R.id.cartAdd);
        mRemove = (ImageButton) itemView.findViewById(R.id.cartRemove);

        mBuy.setOnClickListener(this);
        mRemove.setOnClickListener(this);
    }

    //Remove it from the list regardless, start a dialog to add it back to the Perishable list if appropriate.
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

    public void setLine1(String added) {
        mDate.setText(added);
    }
}
