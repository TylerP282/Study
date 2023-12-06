package com.example.study.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.study.Study;
import com.example.study.User;

import java.util.List;

@Dao
public interface StudyDAO {
    @Insert
    void insert(Study... studyLogs);
    @Update
    void update(Study... studyLogs);
    @Delete
    void delete(Study studyLog);
    @Query("SELECT * FROM " + AppDatabase.STUDY_TABLE)
    List<Study> getAllStudyLogs();
    @Query("SELECT * FROM " + AppDatabase.STUDY_TABLE + " WHERE mLogId = :logId")
    List<Study> getStudyById(int logId);
    @Query("SELECT * FROM " + AppDatabase.STUDY_TABLE + " WHERE mUserId = :userId")
    List<Study> getStudyByUserId(int userId);
    @Insert
    void insert(User... users);
    @Update
    void update(User... users);
    @Delete
    void delete(User user);
    @Query("SELECT * FROM " + AppDatabase.USER_TABLE)
    List<User> getAllUsers();

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUserName = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUserId = :userId")
    User getUserByUserId(int userId);
}
