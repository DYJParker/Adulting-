package com.dave.adulting.ToBuy;

/**
 * Created by Dave - Work on 5/16/2017.
 */

public class ToBuyItem {
    private String mTitle;
    private long mAdded;

    public ToBuyItem() {
    }

    public ToBuyItem(String title, long added) {
        mTitle = title;
        mAdded = added;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public long getAdded() {
        return mAdded;
    }

    public void setAdded(long added) {
        mAdded = added;
    }
}
