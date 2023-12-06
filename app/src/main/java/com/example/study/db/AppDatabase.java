package com.example.study.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.study.Deck;
import com.example.study.Flashcard;
import com.example.study.Study;
import com.example.study.User;

@Database(entities = {Study.class, User.class, Deck.class, Flashcard.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DB_NAME = "STUDY_DATABASE";
    public static final String STUDY_TABLE = "STUDY_TABLE";
    public static final String USER_TABLE = "USER_TABLE";

    public abstract StudyDAO getStudyDAO();
    public abstract DeckDAO getDeckDAO();
    public abstract FlashcardDAO getFlashcardDAO();

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class, "study_database").fallbackToDestructiveMigrationOnDowngrade().build();
        }
        return instance;
    }


}

