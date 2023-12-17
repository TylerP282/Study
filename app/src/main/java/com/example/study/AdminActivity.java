package com.example.study;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.study.db.AppDatabase;
import com.example.study.db.StudyDAO;

import java.util.List;

public class AdminActivity extends AppCompatActivity {
    private StudyDAO mStudyDAO;
    private ListView userListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        getDatabase();

        userListView = findViewById(R.id.userListView);

        List<String> usernames = mStudyDAO.getAllUsernames();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usernames);
        userListView.setAdapter(adapter);
    }

    private void getDatabase(){
        mStudyDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getStudyDAO();
    }
}
