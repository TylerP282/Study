package com.example.study;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "deck_table")
public class Deck {
    @PrimaryKey(autoGenerate = true)
    private int mDeckId;
    private String mDeckName;

    public Deck(int deckId,String deckName){
        mDeckId = deckId;
        mDeckName = deckName;
    }

    public int getDeckId() {
        return mDeckId;
    }

    public void setDeckId(int DeckId) {
        this.mDeckId = DeckId;
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
                ", mDeckName='" + mDeckName + '\'' +
                '}';
    }
}
