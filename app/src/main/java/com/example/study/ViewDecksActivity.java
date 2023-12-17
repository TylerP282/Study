package com.example.study;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.study.db.AppDatabase;
import com.example.study.db.StudyDAO;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ViewDecksActivity extends AppCompatActivity implements OnDeckClickListener{
    private StudyDAO mStudyDAO;
    private List<Deck> decks;
    private ListView listView;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_decks);

        userId = getIntent().getIntExtra("USER_ID",-1);

        getDatabase();

        listView = findViewById(R.id.listView);

        fetchAndDisplayDecks(listView);


        ImageButton btnAddDeck = findViewById(R.id.btnAddDeck);
        btnAddDeck.setOnClickListener(v -> showAddDeckDialog());

    }


    @Override
    public void onDeckClick(Deck deck){
        Intent intent = DeckDetailActivity.intentFactory(this,deck.getDeckId());
        startActivity(intent);
    }

    private void showAddDeckDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Deck");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String deckName = input.getText().toString();
            if (!TextUtils.isEmpty(deckName)) {
                addDeckToDatabase(deckName);
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void getDatabase(){
        mStudyDAO = Room.databaseBuilder(this,AppDatabase.class,AppDatabase.DB_NAME).allowMainThreadQueries().build().getStudyDAO();
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

    private void addDeckToDatabase(String deckName){
        Deck newDeck = new Deck(0,userId,deckName);
        long deckId = mStudyDAO.insertDeck(newDeck);
        fetchAndDisplayDecks((ListView) findViewById(R.id.listView));
    }

    @Override
    protected void onResume(){
        super.onResume();
        fetchAndDisplayDecks(findViewById(R.id.listView));
    }

    public static Intent intentFactory(Context context,int userId){
        Intent intent = new Intent(context,ViewDecksActivity.class);
        intent.putExtra("USER_ID",userId);
        return intent;
    }

    private class FetchDecksAsyncTask extends AsyncTask<Void, Void, List<Deck>> {
        private WeakReference<ViewDecksActivity> activityReference;

        FetchDecksAsyncTask(ViewDecksActivity activity){
            activityReference = new WeakReference<>(activity);
        }
        @Override
        protected List<Deck> doInBackground(Void... voids) {
            return mStudyDAO.getAllDecks();
        }

        @Override
        protected void onPostExecute(List<Deck> fetchedDecks) {
            updateUI(fetchedDecks);
        }
    }

    private void updateUI(List<Deck> fetchedDecks) {
        //Not needed
        List<String> deckNames = new ArrayList<>();
        for (Deck deck : fetchedDecks) {
            deckNames.add(deck.getDeckName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, deckNames);
        listView.setAdapter(adapter);
    }
}
