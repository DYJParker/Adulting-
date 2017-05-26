package com.dave.adulting.Tasks;

import com.dave.adulting.CommonInfrastructure.CommonObject;

/**
 * Created by Dave - Work on 5/19/2017.
 */

//Boring POJO (BOJO??) to represent a task.
public class Task extends CommonObject{
    private String mDescription, mDueDate;

    public Task() {
    }

    public Task(String description, String dueDate) {
        mDescription = description;
        mDueDate = dueDate;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDueDate() {
        return mDueDate;
    }

    public void setDueDate(String dueDate) {
        mDueDate = dueDate;
    }
}
