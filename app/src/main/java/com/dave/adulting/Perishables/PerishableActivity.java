package com.dave.adulting.Perishables;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.dave.adulting.CommonInfrastructure.InfrastructureBaseActivity;
import com.dave.adulting.R;

public class PerishableActivity extends InfrastructureBaseActivity {
    private RecyclerView.Adapter mAdapter;
    private static final String TAG = "PerishableActivity";

    @Override
    protected void onSpecificCreate() {
        setContentView(R.layout.activity_perishable);

        Log.d(TAG, "onSpecificCreate happened!");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }
}
