package com.dave.adulting.Perishables;

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

public class PerishableController extends FireBaseController {

    public static final String KEY = "Perishables";

    //Provides appropriately configured adapter back to super when super is setting up the RV.
    @Override
    protected FirebaseRecyclerAdapter adapt() {
        mRef = mRef.child(KEY);
        return new FirebaseRecyclerAdapter<Perishable, PerishableVH>(
                Perishable.class, R.layout.list_card, PerishableVH.class, mRef.orderByChild("expires")) {
            @Override
            protected void populateViewHolder(PerishableVH VH, Perishable model, int position) {
                VH.setTitle(model.getTitle());
                VH.setLine1(model.getExpires());
                VH.setLine2(model.getAdded());
                VH.setAdapter(this);
            }
        };
    }

    //Provides a /specific/ @IdRes to super, so the RV is specifically addressable for testing.
    @Override
    protected int getNewID() {
        return R.id.perishableRV;
    }

    //sibling-common method to add an item to its respective list.
    @Override
    public void adder() {
        PerishableDialoger.addDialog(getActivity(),mRef,null);
    }
}
