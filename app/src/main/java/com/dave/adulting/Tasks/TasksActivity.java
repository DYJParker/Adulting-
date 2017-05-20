package com.dave.adulting.Tasks;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.dave.adulting.CommonInfrastructure.InfrastructureBaseActivity;
import com.dave.adulting.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TasksActivity extends InfrastructureBaseActivity implements TaskDialoger.TaskAddListener {
    private FirebaseRecyclerAdapter mAdapter;
    private DatabaseReference mRef;

    @Override
    protected void onSpecificCreate() {
        setContentView(R.layout.activity_tasks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //toolbar.findViewById(R.id.actionTasks).setEnabled(false);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskDialoger.addDialog(TasksActivity.this, TasksActivity.this);
            }
        });

        mRef = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Tasks");

        RecyclerView rv = (RecyclerView) findViewById(R.id.taskRV);
        rv.setHasFixedSize(false);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mAdapter = new FirebaseRecyclerAdapter<Task, TaskVH>(
                Task.class, R.layout.two_line_list_item, TaskVH.class, mRef.orderByChild("expires")) {
            @Override
            protected void populateViewHolder(TaskVH VH, Task model, int position) {
                VH.setDescription(model.getDescription());
                //VH.setDueDate(model.getDueDate());
                VH.setAdapter(this);
            }
        };
        rv.setAdapter(mAdapter);
        //super.onSpecificCreate();
    }

    @Override
    public void addTask(Task task) {
        mRef.push().setValue(task);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        prepareMenu(menu, R.id.actionTasks);
        return super.onPrepareOptionsMenu(menu);
    }
}
