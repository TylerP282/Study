package com.example.study;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.study.db.AppDatabase;
import com.example.study.db.StudyDAO;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.study.userIdKey";
    private static final String PREFENCES_KEY = "com.example.study.PREFENCES_KEY";
    //private TextView mMainDisplay;
    private TextView mDebug;

    private Button mSubmitButton;
    private StudyDAO mStudyDAO;
    private List<Study> mStudy;
    private int mUserId = -1;
    private SharedPreferences mPreferences = null;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getDatabase();

        checkForUser();

        loginUser(mUserId);

        TextView welcomeTextView = findViewById(R.id.textViewWelcome);
        welcomeTextView.setText("Welcome " + "\n" + mUser.getUsername());

        Button logoutButton = findViewById(R.id.buttonLogout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        //mMainDisplay = findViewById(R.id.mainGymLogDisplay);
        //mMainDisplay.setMovementMethod(new ScrollingMovementMethod());


        //mSubmitButton = findViewById(R.id.mainSubmitButton);

        refreshDisplay();

        //mSubmitButton.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        Study log = getValuesFromDisplay();
        //        mStudyDAO.insert(log);
        //        refreshDisplay();
        //    }
      //  });
    }

    private void debug(){
        mDebug = findViewById(R.id.textViewWelcome);
        mDebug.setMovementMethod(new ScrollingMovementMethod());
        List<User> users = mStudyDAO.getAllUsers();

        StringBuilder sb = new StringBuilder();

        sb.append("All users:\n");

        for(User u : users){
            sb.append(u);
            sb.append("\n");
        }

        sb.append("all Logs\n");
        List<Study> logs = mStudyDAO.getAllStudyLogs();
        for(Study log : logs){
            sb.append(log);
        }
        mDebug.setText(sb.toString());
    }

    private void loginUser(int userId){
        mUser = mStudyDAO.getUserByUserId(userId);
        addUserToPreference(userId);
        invalidateOptionsMenu();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        if(mUser != null){
            MenuItem item = menu.findItem(R.id.userMenuLogout);
            item.setTitle(mUser.getUsername());
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void addUserToPreference(int userId){
        if(mPreferences == null){
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(USER_ID_KEY,userId);
        editor.apply();
    }

    private void getDatabase(){
        mStudyDAO = Room.databaseBuilder(this, AppDatabase.class,AppDatabase.DB_NAME).allowMainThreadQueries().build().getStudyDAO();
    }

    private void checkForUser(){
        mUserId = getIntent().getIntExtra(USER_ID_KEY,-1);
        if(mUserId != -1){
            return;
        }
        if(mPreferences == null){
            getPrefs();
        }
        mUserId = mPreferences.getInt(USER_ID_KEY,-1);
        if(mUserId != -1){
            return;
        }

        List<User> users = mStudyDAO.getAllUsers();

        if(users.size() <= 0){
            User defaultUser = new User("abc","123");
            User altUser = new User("def","123");
            mStudyDAO.insert(defaultUser,altUser);
        }
        Intent intent = LoginActivity.intentFactory(this);
        startActivity(intent);
    }

    private void getPrefs(){
        mPreferences = this.getSharedPreferences(PREFENCES_KEY, Context.MODE_PRIVATE);
    }

    private void logoutUser() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage(R.string.logout);

        alertBuilder.setPositiveButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearUserFromIntent();
                        clearUserFromPref();
                        mUserId = -1;
                        checkForUser();
                    }
                });
        alertBuilder.setNegativeButton(getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        alertBuilder.create().show();

    }

    private void clearUserFromIntent(){
        getIntent().putExtra(USER_ID_KEY,-1);
    }

    private void clearUserFromPref(){
        addUserToPreference(-1);
    }

    private Study getValuesFromDisplay(){

        //todo
        return new Study(1);
    }

    private void refreshDisplay(){
        mStudy = mStudyDAO.getStudyByUserId(mUserId);

        if(mStudy.size() <= 0){
            //mMainDisplay.setText(R.string.noLogsMessage);
            return;
        }

        StringBuilder sb = new StringBuilder();
        for(Study log : mStudy){
            sb.append(log);
            sb.append("\n");
            sb.append("=-=-=-=-=-=-");
            sb.append("\n");
        }
        //mMainDisplay.setText(sb.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 0x7f0a0001: //R.id.userMenuLogout:
                logoutUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static Intent intentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID_KEY, userId);

        return intent;
    }
}