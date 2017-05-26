package com.dave.adulting.Tasks;

import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.dave.adulting.CommonInfrastructure.CompletableVH;
import com.dave.adulting.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Dave - Work on 5/19/2017.
 */

//ViewHolder for Tasks.
public class TaskVH extends CompletableVH {
    private TextView mDescription, mDueDate;

    //Pass desired child layout to super, retrieve ID references on construction,
    public TaskVH(View itemView) {
        super(itemView, R.layout.two_line_list_item);
        mDescription = (TextView) itemView.findViewById(R.id.listTitle);
        mDueDate = (TextView) itemView.findViewById(R.id.listLine1);
    }

    //simple method to remove a selected list item, set in super.
    @Override
    public void onClick(View v) {
        mAdapter.getRef(getAdapterPosition()).removeValue();
    }

    void setDescription(String description) {
        mDescription.setText(description);
    }

    //sets highlight of TextViews when the duedate is set.
    void setDueDate(String dueDate) {
        mDueDate.setText(dueDate);
        setCriticalityColor(dueDate, mDescription, mDueDate);
    }
}
