package com.example.study;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.study.db.AppDatabase;
import com.example.study.db.StudyDAO;

import java.util.ArrayList;
import java.util.List;

public class DeckDetailActivity extends AppCompatActivity {
    private StudyDAO mStudyDAO;
    private int deckId;

    @Override
        protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_detail);

        getDatabase();

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> onBackPressed());

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        deckId = getIntent().getIntExtra("DECK_ID",-1);

        TextView textViewDeckName = findViewById(R.id.deckDetailDeckName);
        Button btnAddCard = findViewById(R.id.btnAddCard);
        Button btnDeleteDeck = findViewById(R.id.deckDetailDeleteDeck);

        btnAddCard.setOnClickListener(v -> onAddCardClick());
        btnDeleteDeck.setOnClickListener(v -> onDeleteDeckClick());

        Deck deck = mStudyDAO.getDeckById(deckId);
        textViewDeckName.setText(deck.getDeckName());

        fetchAndDisplayFlashcards();
    }

    private void getDatabase(){
        mStudyDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getStudyDAO();
    }

    private void onAddCardClick(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Card");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_add_card,null);
        builder.setView(viewInflated);

        final EditText inputFront = viewInflated.findViewById(R.id.inputFront);
        final EditText inputBack = viewInflated.findViewById(R.id.inputBack);

        builder.setPositiveButton("Add",(dialog,which) ->{
            String front = inputFront.getText().toString();
            String back = inputBack.getText().toString();

            Flashcard newCard = new Flashcard(0,deckId,front,back);
            mStudyDAO.insertFlashcard(newCard);

            fetchAndDisplayFlashcards();

        });
        builder.setNegativeButton("Cancel",(dialog,which)->dialog.cancel());
        builder.show();
    }

    private void onDeleteDeckClick(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Deck");
        builder.setMessage("Are you sure you want to delete this deck?");

        builder.setPositiveButton("Delete",(dialog,which)->{
            mStudyDAO.deleteDeckById(deckId);
            finish();
        });
        builder.setNegativeButton("Cancel",(dialog,which)->dialog.cancel());
        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void fetchAndDisplayFlashcards(){
        List<Flashcard> flashcards = mStudyDAO.getFlashcardsByDeckId(deckId);

        ListView listView = findViewById(R.id.listViewFlashcards);

        List<String> flashcardInfoList = new ArrayList<>();
        for(Flashcard flashcard : flashcards){
            String flashcardInfo = "Front: " + flashcard.getFront() + "\nBack: " + flashcard.getBack();
            flashcardInfoList.add(flashcardInfo);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,flashcardInfoList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent,view,position,id)->{
            Flashcard clickedFlashcard = flashcards.get(position);
            showEditDeleteDialog(clickedFlashcard);
        });
    }

    private void showEditDeleteDialog(Flashcard clickedFlashcard) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit or Delete Card");

        builder.setPositiveButton("Edit",(dialog,which)->{
            showEditCardDialog(clickedFlashcard);
            dialog.dismiss();
        });

        builder.setNegativeButton("Delete",(dialog,which)->{
            mStudyDAO.deleteFlashcardById(clickedFlashcard.getFlashcardId());
            fetchAndDisplayFlashcards();
            dialog.dismiss();
        });

        builder.setNeutralButton("Cancel",(dialog,which)->{
            dialog.cancel();
        });
        builder.show();
    }

    private void showEditCardDialog(Flashcard clickedFlashcard) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Card");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_add_card,null);
        builder.setView(viewInflated);

        final EditText inputFront = viewInflated.findViewById(R.id.inputFront);
        final EditText inputBack = viewInflated.findViewById(R.id.inputBack);

        inputFront.setText(clickedFlashcard.getFront());
        inputBack.setText(clickedFlashcard.getBack());

        builder.setPositiveButton("Save",(dialog,which)->{
            String front = inputFront.getText().toString();
            String back = inputBack.getText().toString();

            clickedFlashcard.setFront(front);
            clickedFlashcard.setBack(back);
            mStudyDAO.updateFlashcard(clickedFlashcard);

            fetchAndDisplayFlashcards();
        });

        builder.setNegativeButton("Cancel",(dialog,which)->{
           dialog.cancel();
        });
        builder.show();
    }

    public static Intent intentFactory(Context context,int deckId){
        Intent intent = new Intent(context, DeckDetailActivity.class);
        intent.putExtra("DECK_ID",deckId);
        return intent;
    }
}
