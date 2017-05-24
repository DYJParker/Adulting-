package com.dave.adulting.CommonInfrastructure;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dave.adulting.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dave - Work on 5/18/2017.
 */

public abstract class CompletableVH extends RecyclerView.ViewHolder implements /*View.OnLongClickListener,*/ View.OnClickListener {
    protected ImageButton mCheck;
    protected DateFormat mDF;
    protected FirebaseRecyclerAdapter mAdapter;
    private static final String TAG = "CompletableVH";

    public CompletableVH(View itemView) {
        super(itemView);

        mDF = DateFormat.getDateInstance(DateFormat.SHORT);
        if (itemView.findViewById(R.id.check) != null) {
            mCheck = (ImageButton) itemView.findViewById(R.id.check);
            mCheck.setOnClickListener(this);
        }
        //itemView.setOnLongClickListener(this);
    }

    public void setAdapter(FirebaseRecyclerAdapter adapter) {
        mAdapter = adapter;
    }

    protected void setCriticalityColor(String sDate, TextView... textViews) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        try {
            Date date = DateFormat.getDateInstance(CommonObject.DATE_FORMAT).parse(sDate);
            long dif = date.getTime() - cal.getTimeInMillis();
            int textColor;
            if (dif >= 0) {
                float x = TimeUnit.MILLISECONDS.toDays(dif);
                float ratio = (float) Math.pow(0.75, x);
                int[] attr = new int[]{android.R.attr.textColorPrimary};
                TypedArray a = textViews[0].getContext().obtainStyledAttributes(attr);
                int stockColor = a.getColor(0, Color.WHITE);
                a.recycle();
                textColor = ColorUtils.blendARGB(stockColor, Color.rgb(255, 140, 0), ratio);
            } else textColor = Color.RED;
            for (TextView text : textViews) {
                text.setTextColor(textColor);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
