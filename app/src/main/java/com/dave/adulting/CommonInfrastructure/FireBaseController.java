package com.dave.adulting.CommonInfrastructure;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Dave - Work on 5/23/2017.
 */

public abstract class FireBaseController extends Controller {
    protected FirebaseRecyclerAdapter mAdapter;
    protected DatabaseReference mRef;


    abstract protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
        mRef.onDisconnect();
    }
}
