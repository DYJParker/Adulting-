package com.dave.adulting.Perishables;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.dave.adulting.CommonInfrastructure.InfrastructureBaseActivity;
import com.dave.adulting.R;
import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PerishableActivity extends InfrastructureBaseActivity implements PerishableDialoger.PerAddListener {
    private FirebaseRecyclerAdapter mAdapter;
    private DatabaseReference mRef;
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
                PerishableDialoger.addDialog(PerishableActivity.this, PerishableActivity.this);
            }
        });

        mRef = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Perishables");

        RecyclerView rv = (RecyclerView) findViewById(R.id.perishableRV);
        rv.setHasFixedSize(false);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mAdapter = new FirebaseRecyclerAdapter<Perishable, PerishableVH>(
                Perishable.class, R.layout.three_line_list_item, PerishableVH.class, mRef.orderByChild("expires")) {
            @Override
            protected void populateViewHolder(PerishableVH VH, Perishable model, int position) {
                VH.setTitle(model.getTitle());
                VH.setLine1(model.getExpires());
                VH.setLine2(model.getAdded());
                VH.setAdapter(this);
            }

            @Override
            protected Perishable parseSnapshot(DataSnapshot snapshot) {
                return super.parseSnapshot(snapshot);
            }
        };
        rv.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
        mRef.onDisconnect();
    }

    @Override
    public void addPerishable(Perishable per) {
        mRef.push().setValue(per);
    }
}
