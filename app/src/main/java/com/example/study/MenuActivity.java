package com.example.study;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.study.db.AppDatabase;
import com.example.study.db.StudyDAO;

import java.util.List;

/**
 * this entire class is a mystery to me...
 * any code I write here seems to have no effect on the app
 * but when i copy the code here and paste it to mainActivity it works fine
 * so im not sure what the purpose of having this class is
 * but at this point im too afraid to delete anything
 */
public class MenuActivity extends AppCompatActivity {
    private StudyDAO mStudyDAO;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mStudyDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getStudyDAO();

        //userId = mStudyDAO.getStudyByUserId();

        Button viewDecksButton = findViewById(R.id.buttonCreateDeck);
        Button reviewDecksButton = findViewById(R.id.buttonReviewDecks);
        Button adminButton = findViewById(R.id.buttonAdmin);


        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAdminButtonClick();
            }
        });
        viewDecksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startViewDecksActivity();
            }
        });
        reviewDecksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startReviewDecksActivity();
            }
        });

    }

    private void onAdminButtonClick() {
        List<String> usernames = mStudyDAO.getAllUsernames();
        showUserListDialog(usernames);
    }


    private void showUserListDialog(List<String> usernames) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
        builder.setTitle("List of Users");
        builder.setItems(usernames.toArray(new String[0]), null); // null click listener as it's just for display
        builder.show();
    }


    private void startViewDecksActivity(){
        Intent intent = ViewDecksActivity.intentFactory(getApplicationContext(),userId);
        startActivity(intent);
    }

    private void startReviewDecksActivity(){
        Intent intent = ReviewDecksActivity.intentFactory(getApplicationContext(), userId);
        startActivity(intent);
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context,MenuActivity.class);
        return intent;
    }
}
