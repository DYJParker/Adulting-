package com.dave.adulting.ToBuy;

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

public class ToBuyController extends FireBaseController {
    public static final String KEY = "Shopping";

    @Override
    protected FirebaseRecyclerAdapter adapt() {
        mRef = mRef.child(KEY);
        return new FirebaseRecyclerAdapter<ToBuyItem, ToBuyVH>(
                ToBuyItem.class, R.layout.shopping_list_item, ToBuyVH.class, mRef) {
            @Override
            protected void populateViewHolder(ToBuyVH VH, ToBuyItem model, int position) {
                VH.setTitle(model.getTitle());
                VH.setLine1(model.getAdded());
                VH.setAdapter(this);
            }
        };
    }

    @Override
    public void adder() {
        ToBuyDialoger.addDialog(getActivity(),mRef);
    }
}
