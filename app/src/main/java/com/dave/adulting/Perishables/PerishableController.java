package com.dave.adulting.Perishables;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.dave.adulting.CommonInfrastructure.FireBaseController;
import com.dave.adulting.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Dave - Work on 5/23/2017.
 */

public class PerishableController extends FireBaseController {

    public static final String KEY = "Perishables";

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View content = inflater.inflate(R.layout.content_perishable, container, false);

        mRef = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(KEY);

        RecyclerView rv = (RecyclerView) content.findViewById(R.id.perishableRV);
        rv.setHasFixedSize(false);
        rv.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false));

        mAdapter = new FirebaseRecyclerAdapter<Perishable, PerishableVH>(
                Perishable.class, R.layout.three_line_list_item, PerishableVH.class, mRef.orderByChild("expires")) {
            @Override
            protected void populateViewHolder(PerishableVH VH, Perishable model, int position) {
                VH.setTitle(model.getTitle());
                VH.setLine1(model.getExpires());
                VH.setLine2(model.getAdded());
                VH.setAdapter(this);
            }
        };
        rv.setAdapter(mAdapter);

        return content;
    }
}
