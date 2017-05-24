package com.dave.adulting.Tasks;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dave.adulting.CommonInfrastructure.FireBaseController;
import com.dave.adulting.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Dave - Work on 5/23/2017.
 */

public class TasksController extends FireBaseController {
    public static final String KEY = "Tasks";

    @Override
    protected FirebaseRecyclerAdapter adapt() {
        mRef = mRef.child(KEY);
        return new FirebaseRecyclerAdapter<Task, TaskVH>(
                Task.class, R.layout.two_line_list_item, TaskVH.class, mRef.orderByChild("dueDate")) {
            @Override
            protected void populateViewHolder(TaskVH VH, Task model, int position) {
                VH.setDescription(model.getDescription());
                if(model.getDueDate()!=null)VH.setDueDate(model.getDueDate());
                VH.setAdapter(this);
            }
        };
    }

    @Override
    public void adder() {
        TaskDialoger.addDialog(getActivity(),mRef);
    }
}
