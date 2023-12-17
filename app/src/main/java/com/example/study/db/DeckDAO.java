package com.example.study.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.study.Deck;

import java.util.List;

@Dao

public interface DeckDAO {
    @Insert
    long insertDeck(Deck deck);
    @Update
    void updateDeck(Deck deck);
    @Delete
    void deleteDeck(Deck deck);
    @Query("SELECT * FROM deck_table")
    List<Deck> getAllDecks();
    @Query("SELECT * FROM deck_table WHERE mUserId = :userId")
    List<Deck> getUserDecks(int userId);
}
