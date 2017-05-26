package com.dave.adulting.CommonInfrastructure;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewStub;
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

    //parent constructor that takes an additional "R.id..." parameter, which is then inflated into
    //the provided view's ViewStub, allowing uniform wrapping CardViews.
    public CompletableVH(View itemView, @LayoutRes int id) {
        super(itemView);
        ViewStub slot = ((ViewStub) itemView.findViewById(R.id.slot));
        slot.setLayoutResource(id);
        slot.inflate();
        //itemView.findViewById(R.id.content).requestLayout();
        mDF = DateFormat.getDateInstance(DateFormat.SHORT);
        mCheck = (ImageButton) itemView.findViewById(R.id.check);
        if (id != R.layout.shopping_list_item) mCheck.setOnClickListener(this);
        else mCheck.setVisibility(View.GONE);

        //((RecyclerView)itemView.getParent()).getLayoutManager()
        //itemView.setOnLongClickListener(this);
    }

    public void setAdapter(FirebaseRecyclerAdapter adapter) {
        mAdapter = adapter;
    }

    //utility method to allow concrete ViewHolders to conveniently and discretionarily highlight
    //views based on how close the represented item is to its deadline.
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
