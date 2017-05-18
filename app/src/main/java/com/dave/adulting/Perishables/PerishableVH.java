package com.dave.adulting.Perishables;

import android.graphics.Paint;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dave.adulting.CommonInfrastructure.CompletableVH;
import com.dave.adulting.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Dave - Work on 5/16/2017.
 */

class PerishableVH extends CompletableVH {
    TextView mTitle, mLine1, mLine2;
    DateFormat mDF;
    FirebaseRecyclerAdapter mAdapter;

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

    public void setAdapter(FirebaseRecyclerAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void onClick(View v) {
        mAdapter.getRef(getAdapterPosition()).removeValue();
    }
}
