package com.example.study;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.study.db.AppDatabase;

@Entity(tableName = AppDatabase.USER_TABLE)
public class User {

    @PrimaryKey(autoGenerate = true)
    private int mUserId;
    private String mUsername;
    private String mPassword;
    private boolean isAdmin;

    public User(String username, String password){
        mUsername = username;
        mPassword = password;
    }

    public boolean isAdmin(){
        return isAdmin;
    }
    public void setAdmin(boolean admin){
        isAdmin = admin;
    }
    public int getUserId(){
        return mUserId;
    }
    public void setUserId(int userId){
        mUserId = userId;
    }
    public String getUsername(){
        return mUsername;
    }
    public void setUsername(String username){
        mUsername = username;
    }
    public String getPassword(){
        return mPassword;
    }
    public void setPassword(String password){
        mPassword = password;
    }
    @Override
    public String toString() {
        return "User{" +
                "mUserId=" + mUserId +
                ", mUserName='" + mUsername + '\'' +
                ", mPassword='" + mPassword + '\'' +
                '}';
    }
}
