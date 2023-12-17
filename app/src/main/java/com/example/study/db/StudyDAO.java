package com.example.study.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.study.Deck;
import com.example.study.Flashcard;
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
    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE isAdmin = 1")
    List<User> getAdminUsers();
    @Query("UPDATE " + AppDatabase.USER_TABLE + " SET isAdmin = :isAdmin WHERE mUserId = :userId")
    void updateAdminStatus(int userId, boolean isAdmin);
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
    @Insert
    long insertDeck(Deck deck);
    @Query("SELECT * FROM deck_table")
    List<Deck> getAllDecks();
    @Query("SELECT * FROM deck_table WHERE mDeckName = :deckName LIMIT 1")
    Deck getDeckByName(String deckName);
    @Query("SELECT * FROM deck_table WHERE mDeckId = :id")
    Deck getDeckById(long id);
    @Query("DELETE FROM deck_table WHERE mDeckId = :id")
    void deleteDeckById(long id);
    @Insert
    void insertFlashcard(Flashcard flashcard);
    @Query("SELECT * FROM flashcard_table WHERE mDeckId = :deckId")
    List<Flashcard> getFlashcardsByDeckId(int deckId);
    @Query("DELETE FROM flashcard_table WHERE mFlashcardId = :flashcardId")
    void deleteFlashcardById(int flashcardId);
    @Update
    void updateFlashcard(Flashcard flashcard);
    @Query("SELECT * FROM deck_table WHERE mUserId = :userId")
    List<Deck> getUserDecks(int userId);

    @Query("SELECT * FROM flashcard_table WHERE mFlashcardId = :flashcardId")
    Flashcard getFlashcardById(int flashcardId);
    @Query("SELECT mUsername FROM user_table")
    List<String> getAllUsernames();
}
