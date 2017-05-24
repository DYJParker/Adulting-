package com.dave.adulting.Perishables;

import com.dave.adulting.CommonInfrastructure.CommonObject;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Dave - Work on 5/16/2017.
 */

public class Perishable extends CommonObject {
    private String mTitle, mExpires, mAdded;

    public Perishable() {
    }

    public Perishable(String title, String expires, String added) {
        mTitle = title;
        mExpires = expires;
        mAdded = added;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getExpires() {
        return mExpires;
    }

    public void setExpires(String expires) {
        mExpires = expires;
    }

    public String getAdded() {
        return mAdded;
    }

    public void setAdded(String added) {
        mAdded = added;
    }
}
