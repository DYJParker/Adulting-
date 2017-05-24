package com.dave.adulting.ToBuy;

import com.dave.adulting.CommonInfrastructure.CommonObject;

/**
 * Created by Dave - Work on 5/16/2017.
 */

public class ToBuyItem extends CommonObject {
    private String mTitle, mAdded;

    public ToBuyItem() {
    }

    public ToBuyItem(String title, String added) {
        mTitle = title;
        mAdded = added;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getAdded() {
        return mAdded;
    }

    public void setAdded(String added) {
        mAdded = added;
    }
}
