package com.dave.adulting.ToBuy;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dave.adulting.CommonInfrastructure.InfrastructureBaseActivity;
import com.dave.adulting.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ToBuyActivity extends InfrastructureBaseActivity {
    private FirebaseRecyclerAdapter mAdapter;
    private DatabaseReference mRef;
    public static final String SHOPPING_LIST = "ShoppingList";

    @Override
    protected void onSpecificCreate() {
        ICON_ID = R.id.actionToBuy;
        setContentView(R.layout.activity_to_buy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRef = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(SHOPPING_LIST);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToBuyDialoger.addDialog(ToBuyActivity.this,mRef);
            }
        });

        RecyclerView rv = (RecyclerView) findViewById(R.id.toBuyRV);
        rv.setHasFixedSize(false);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mAdapter = new FirebaseRecyclerAdapter<ToBuyItem, ToBuyVH>(
                ToBuyItem.class, R.layout.shopping_list_item, ToBuyVH.class, mRef) {
            @Override
            protected void populateViewHolder(ToBuyVH VH, ToBuyItem model, int position) {
                VH.setTitle(model.getTitle());
                VH.setLine1(model.getAdded());
                VH.setAdapter(this);
            }
        };
        rv.setAdapter(mAdapter);
    }
}
