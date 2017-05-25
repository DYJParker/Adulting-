package com.dave.adulting.CommonInfrastructure;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
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
    private RecyclerView mRV;
    protected DatabaseReference mRef;
    private static final String TAG = "FireBaseController";

    private static Integer sLandscapeCriterion = null;

    public static void setLandscapeCriterion(Integer landscapeCriterion) {
        sLandscapeCriterion = landscapeCriterion;
    }

    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View content = inflater.inflate(R.layout.controller_content, container, false);

        mRef = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        mRV = (RecyclerView) content.findViewById(R.id.perishableRV);
        mRV.setHasFixedSize(false);

        mAdapter = adapt();
        mRV.setAdapter(mAdapter);

        return content;
    }

    abstract protected FirebaseRecyclerAdapter adapt();

    @Override
    protected void onRestoreViewState(@NonNull View view, @NonNull Bundle savedViewState) {
        super.onRestoreViewState(view, savedViewState);
    }

    @Override
    protected void onAttach(@NonNull View view) {
        int measuredWidth = ((ViewGroup)view.getParent()).getMeasuredWidth();
        DisplayMetrics metrics = view.getContext().getResources().getDisplayMetrics();
        //parent.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
        //        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        Log.d(TAG, "onAttach, viewWidth: " + measuredWidth + ", density: " + metrics.density);
        float dpWidth = measuredWidth / metrics.density;
        if (sLandscapeCriterion != null && dpWidth > sLandscapeCriterion * 2) {
            mRV.setLayoutManager(new GridLayoutManager(view.getContext(), (int) (dpWidth / sLandscapeCriterion), GridLayoutManager.VERTICAL, false));
        } else
            mRV.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        super.onAttach(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
        mRef.onDisconnect();
    }

    abstract public void adder();
}
