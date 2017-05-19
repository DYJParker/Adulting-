package com.dave.adulting.CommonInfrastructure;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dave.adulting.Perishables.Perishable;
import com.dave.adulting.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dave - Work on 5/18/2017.
 */

public abstract class CompletableVH extends RecyclerView.ViewHolder implements /*View.OnLongClickListener,*/ View.OnClickListener {
    ImageButton mCheck;
    protected DateFormat mDF;
    protected FirebaseRecyclerAdapter mAdapter;

    public CompletableVH(View itemView) {
        super(itemView);

        mDF = DateFormat.getDateInstance(DateFormat.SHORT);
        mCheck = (ImageButton)itemView.findViewById(R.id.check);
        mCheck.setOnClickListener(this);
        //itemView.setOnLongClickListener(this);
    }

    public void setAdapter(FirebaseRecyclerAdapter adapter) {
        mAdapter = adapter;
    }

    protected void setCriticalityColor(long time, TextView... textViews){
        Calendar cal = GregorianCalendar.getInstance();
        long midnightThisMorning = setMidnight(cal.getTimeInMillis());

        float x = TimeUnit.MILLISECONDS.toDays(time - midnightThisMorning);
        int textColor;
        if (x >= 0) {
            double orangeness = Math.pow(0.75,x);
            textColor = Color.rgb((int)(255*orangeness),(int)(140*orangeness),0);
        } else textColor = Color.RED;
        for(TextView text : textViews) text.setTextColor(textColor);
    }

    public static long setMidnight(long time){
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTimeInMillis(time);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        return cal.getTimeInMillis();
    }
}
