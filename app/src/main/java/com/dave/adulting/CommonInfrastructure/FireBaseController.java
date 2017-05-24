package com.dave.adulting.CommonInfrastructure;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.dave.adulting.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Dave - Work on 5/23/2017.
 */

public abstract class FireBaseController extends Controller {
    private FirebaseRecyclerAdapter mAdapter;
    protected DatabaseReference mRef;

    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View content = inflater.inflate(R.layout.controller_content, container, false);

        mRef = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("testing");

        RecyclerView rv = (RecyclerView) content.findViewById(R.id.perishableRV);
        rv.setHasFixedSize(false);
        rv.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false));

        mAdapter = adapt();
        rv.setAdapter(mAdapter);

        return content;
    }

    abstract protected FirebaseRecyclerAdapter adapt();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
        mRef.onDisconnect();
    }

    abstract public void adder();
}
