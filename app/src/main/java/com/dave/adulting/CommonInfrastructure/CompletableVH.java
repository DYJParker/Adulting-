package com.dave.adulting.CommonInfrastructure;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.dave.adulting.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import java.text.DateFormat;

/**
 * Created by Dave - Work on 5/18/2017.
 */

public abstract class CompletableVH extends RecyclerView.ViewHolder implements /*View.OnLongClickListener,*/ View.OnClickListener {
    ImageButton mCheck;
    protected DateFormat mDF;
    protected FirebaseRecyclerAdapter mAdapter;

    public CompletableVH(View itemView) {
        super(itemView);

        mDF = DateFormat.getDateInstance(DateFormat.SHORT);
        mCheck = (ImageButton)itemView.findViewById(R.id.check);
        mCheck.setOnClickListener(this);
        //itemView.setOnLongClickListener(this);
    }

    public void setAdapter(FirebaseRecyclerAdapter adapter) {
        mAdapter = adapter;
    }


}
