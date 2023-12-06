package com.example.study.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.study.Flashcard;

import java.util.List;

@Dao
public interface FlashcardDAO {
    @Insert
    long insertFlashcard(Flashcard flashcard);
    @Update
    void updateFlashcard(Flashcard flashcard);
    @Delete
    void deleteFlashcard(Flashcard flashcard);
    @Query("SELECT * FROM flashcard_table WHERE mDeckId = :deckId")
    List<Flashcard> getFlashcardsByDeckId(int deckId);
}
