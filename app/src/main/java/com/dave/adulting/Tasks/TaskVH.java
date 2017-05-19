package com.dave.adulting.Tasks;

import android.view.View;
import android.widget.TextView;

import com.dave.adulting.CommonInfrastructure.CompletableVH;
import com.dave.adulting.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Dave - Work on 5/19/2017.
 */

class TaskVH extends CompletableVH {
    TextView mDescription, mDueDate;

    public TaskVH(View itemView) {
        super(itemView);

        mDescription = (TextView) itemView.findViewById(R.id.listTitle);
        mDueDate = (TextView) itemView.findViewById(R.id.listLine1);
    }

    @Override
    public void onClick(View v) {
        mAdapter.getRef(getAdapterPosition()).removeValue();
    }

    void setDescription(String description){mDescription.setText(description);}

    void setDueDate(long dueDate){mDueDate.setText(mDF.format(new Date(dueDate)));}
}
