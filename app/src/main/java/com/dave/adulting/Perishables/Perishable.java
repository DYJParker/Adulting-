package com.dave.adulting.Perishables;

/**
 * Created by Dave - Work on 5/16/2017.
 */

public class Perishable {
    private String mTitle, mID;
    private long mExpires, mAdded;

    public Perishable() {
    }

    public Perishable(String title, long expires, long added) {
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

    public long getExpires() {
        return mExpires;
    }

    public void setExpires(long expires) {
        mExpires = expires;
    }

    public long getAdded() {
        return mAdded;
    }

    public void setAdded(long added) {
        mAdded = added;
    }
}
