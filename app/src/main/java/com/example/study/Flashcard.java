package com.example.study;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "flashcard_table")
public class Flashcard {
    @PrimaryKey(autoGenerate = true)
    private int mFlashcardId;
    private int mDeckId;
    private String mFront;
    private String mBack;

    public Flashcard(int flashcardId, int deckId, String front, String back){
        mFlashcardId = flashcardId;
        mDeckId = deckId;
        mFront = front;
        mBack = back;
    }


    public int getFlashcardId() {
        return mFlashcardId;
    }

    public void setFlashcardId(int FlashcardId) {
        this.mFlashcardId = FlashcardId;
    }

    public int getDeckId() {
        return mDeckId;
    }

    public void setDeckId(int DeckId) {
        this.mDeckId = DeckId;
    }

    public String getFront() {
        return mFront;
    }

    public void setFront(String Front) {
        this.mFront = Front;
    }

    public String getBack() {
        return mBack;
    }

    public void setBack(String Back) {
        this.mBack = Back;
    }
}
