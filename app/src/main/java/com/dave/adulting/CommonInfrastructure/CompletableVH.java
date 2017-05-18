package com.dave.adulting.CommonInfrastructure;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.dave.adulting.R;

/**
 * Created by Dave - Work on 5/18/2017.
 */

public abstract class CompletableVH extends RecyclerView.ViewHolder implements /*View.OnLongClickListener,*/ View.OnClickListener {
    protected ImageButton mCheck;

    public CompletableVH(View itemView) {
        super(itemView);

        mCheck = (ImageButton)itemView.findViewById(R.id.check);
        mCheck.setOnClickListener(this);
        //itemView.setOnLongClickListener(this);
    }


}
