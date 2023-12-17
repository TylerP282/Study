package com.example.study;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "deck_table")
public class Deck {
    @PrimaryKey(autoGenerate = true)
    private int mDeckId;
    private int mUserId;
    private String mDeckName;

    public Deck(int deckId,int userId,String deckName){
        this.mDeckId = deckId;
        this.mDeckName = deckName;
        this.mUserId = userId;
    }

    public int getDeckId() {
        return mDeckId;
    }

    public void setDeckId(int DeckId) {
        this.mDeckId = DeckId;
    }

    public int getUserId(){
        return mUserId;
    }

    public void setUserId(int userId){
        this.mUserId = userId;
    }

    public String getDeckName() {
        return mDeckName;
    }

    public void setDeckName(String DeckName) {
        this.mDeckName = DeckName;
    }

    @Override
    public String toString() {
        return "Deck{" +
                "mDeckId=" + mDeckId +
                ", mUserId=" + mUserId +
                ", mDeckName='" + mDeckName + '\'' +
                '}';
    }
}
