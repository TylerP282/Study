package com.example.study;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.study.db.AppDatabase;
import com.example.study.db.FlashcardDAO;
import com.example.study.db.StudyDAO;

import java.util.List;

/**
 * ive spent many hours trying to display the text on the back of the card
 * according to the logcat the app is properly handling the front and back text
 * the problem must be with the back text not being visible
 */
public class CardDetailActivity extends AppCompatActivity {
    private StudyDAO mStudyDAO;
    private int deckId;
    private List<Flashcard> flashcards;
    private int currentCardIndex = 0;

    private TextView frontTextView;
    private TextView backTextView;
    private Button flipCardButton;
    private Button prevCardButton;
    private Button nextCardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_detail_layout);

        getDatabase();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        deckId = getIntent().getIntExtra("deckId", -1);

        frontTextView = findViewById(R.id.frontTextView);
        backTextView = findViewById(R.id.backTextView);
        flipCardButton = findViewById(R.id.btnFlipCard);
        prevCardButton = findViewById(R.id.btnPreviousCard);
        nextCardButton = findViewById(R.id.btnNextCard);

        fetchAndDisplayFlashcards();

        flipCardButton.setOnClickListener(v -> flipCard());
        prevCardButton.setOnClickListener(v -> showPreviousCard());
        nextCardButton.setOnClickListener(v -> showNextCard());
    }

    private void getDatabase() {
        mStudyDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getStudyDAO();
    }

    private void fetchAndDisplayFlashcards() {
        flashcards = mStudyDAO.getFlashcardsByDeckId(deckId);
        if (!flashcards.isEmpty()) {
            showCardAtIndex(currentCardIndex);
        }
    }

    private void showCardAtIndex(int index) {
        Flashcard currentCard = flashcards.get(index);
        Log.d("CardDetailActivity", "Front text: " + currentCard.getFront());
        Log.d("CardDetailActivity", "Back text: " + currentCard.getBack());

        frontTextView.setText(currentCard.getFront());
        backTextView.setText(currentCard.getBack());


        frontTextView.setVisibility(View.VISIBLE);
        backTextView.setVisibility(View.GONE);
    }



    private void flipCard() {
        boolean isFrontVisible = (frontTextView.getVisibility() == View.VISIBLE);

        frontTextView.setVisibility(isFrontVisible ? View.GONE : View.VISIBLE);
        backTextView.setVisibility(isFrontVisible ? View.VISIBLE : View.GONE);
    }


    private void showPreviousCard() {
        if (currentCardIndex > 0) {
            currentCardIndex--;
            showCardAtIndex(currentCardIndex);
        }
    }

    private void showNextCard() {
        if (currentCardIndex < flashcards.size() - 1) {
            currentCardIndex++;
            showCardAtIndex(currentCardIndex);
        }
    }
}


