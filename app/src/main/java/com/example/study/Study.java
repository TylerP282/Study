package com.example.study;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.study.db.AppDatabase;

import java.util.Date;

@Entity(tableName = AppDatabase.STUDY_TABLE)
public class Study {

    @PrimaryKey(autoGenerate = true)
    private int mLogId;
    private int mUserId;
    //private Date mDate;

    public Study(int userId){
        mUserId = userId;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }
    public int getLogId() {
        return mLogId;
    }

    public void setLogId(int mLogId) {
        this.mLogId = mLogId;
    }


    /**
     * private int mReps;
     * private double mWeight;
     * private Date mDate;
     */

    //getter + setter + toString
}
