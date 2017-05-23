package com.dave.adulting.Tasks;

/**
 * Created by Dave - Work on 5/19/2017.
 */

public class Task {
    private String mDescription;
    private Long mDueDate;

    public Task() {
    }

    public Task(String description, Long dueDate) {
        mDescription = description;
        mDueDate = dueDate;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Long getDueDate() {
        return mDueDate;
    }

    public void setDueDate(Long dueDate) {
        mDueDate = dueDate;
    }
}
