package com.example.study;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.study.db.AppDatabase;
import com.example.study.db.StudyDAO;

import java.util.List;

public class ReviewDecksActivity extends AppCompatActivity {
    private StudyDAO mStudyDAO;
    private List<Deck> decks;
    private ListView listView;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_decks);

        userId = getIntent().getIntExtra("USER_ID",-1);

        getDatabase();

        listView = findViewById(R.id.listViewDecks);

        fetchAndDisplayDecks(listView);

        listView.setOnItemClickListener((parent,view,position,id)->{
            Deck selectedDeck = decks.get(position);
            if(selectedDeck != null){
                openCardItemActivity(selectedDeck.getDeckId());
            }
        });

    }

    private void openCardItemActivity(int deckId) {
        Intent intent = new Intent(ReviewDecksActivity.this,CardDetailActivity.class);
        intent.putExtra("deckId",deckId);
        startActivity(intent);
    }

    private void fetchAndDisplayDecks(ListView listView) {
        decks = mStudyDAO.getUserDecks(userId);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        for (Deck deck : decks) {
            adapter.add(deck.getDeckName());
        }

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Deck clickedDeck = decks.get(position);
            if (clickedDeck != null) {
                onDeckClick(clickedDeck);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchAndDisplayDecks(listView);
    }

    private void getDatabase() {
        mStudyDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getStudyDAO();
    }

    private void onDeckClick(Deck deck) {
        Intent intent = new Intent(this,CardDetailActivity.class);
        intent.putExtra("deckId",deck.getDeckId());
        startActivity(intent);
    }

    public static Intent intentFactory(Context context, int mUserId) {
        Intent intent = new Intent(context,ReviewDecksActivity.class);
        intent.putExtra("USER_ID",mUserId);
        return intent;
    }
}

