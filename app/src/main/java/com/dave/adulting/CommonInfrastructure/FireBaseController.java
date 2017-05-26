package com.dave.adulting.CommonInfrastructure;

import android.os.Bundle;
import android.support.annotation.IdRes;
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

//parent class for all my Conductor Controllers (ie Fragment-alternatives)
public abstract class FireBaseController extends Controller {
    private FirebaseRecyclerAdapter mAdapter;
    private RecyclerView mRV;
    protected DatabaseReference mRef;
    private static final String TAG = "FireBaseController";

    //Provided from elsewhere, ConductorActivity in the current case.
    private static Integer sLandscapeCriterion = null;

    //Method to set the above from outside.
    public static void setLandscapeCriterion(Integer landscapeCriterion) {
        sLandscapeCriterion = landscapeCriterion;
    }

    //Inflate and set up the RecyclerView, delegating adapter setup to the concrete child class.
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View content = inflater.inflate(R.layout.controller_content, container, false);

        mRef = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        mRV = (RecyclerView) content.findViewById(R.id.pagerRV);
        mRV.setHasFixedSize(false);

        mAdapter = adapt();
        mRV.setAdapter(mAdapter);

        mRV.setId(getNewID());

        return content;
    }

    //Used to request a configured RecyclerView.Adapter from child.
    abstract protected FirebaseRecyclerAdapter adapt();

    //Used to request a per-Controller replacement ID for the identical RecyclerView.
    abstract protected @IdRes int getNewID();

    @Override
    protected void onRestoreViewState(@NonNull View view, @NonNull Bundle savedViewState) {
        super.onRestoreViewState(view, savedViewState);
    }

    //Once the Controller is attached to the ViewPager and the rest of the view tree, configure
    //number of columns according to width of screen.
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

    //cleanup method
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
        mRef.onDisconnect();
    }

    //Used to call the appropriate dialogue to add a new entry to a particular list.
    abstract public void adder();
}
