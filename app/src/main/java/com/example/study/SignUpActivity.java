package com.example.study;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.study.db.AppDatabase;
import com.example.study.db.StudyDAO;

public class SignUpActivity extends AppCompatActivity {
    private EditText mUsernameField;
    private EditText mPasswordField;
    private EditText mPasswordRepeat;
    private Button buttonSignup;
    private StudyDAO mStudyDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mStudyDAO = Room.databaseBuilder(this, AppDatabase.class,AppDatabase.DB_NAME).allowMainThreadQueries().build().getStudyDAO();

        mUsernameField = findViewById(R.id.editTextSignUpUsername);
        mPasswordField = findViewById(R.id.editTextSignUpPassword);
        mPasswordRepeat = findViewById(R.id.editTextSignUpConfirmPassword);
        buttonSignup = findViewById(R.id.signUpSubmitButton);

        Button signUpSubmitButton = findViewById(R.id.signUpSubmitButton);
        signUpSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

    }

    private void signUp(){
        String username = mUsernameField.getText().toString();
        String password = mPasswordField.getText().toString();
        String repeatPassword = mPasswordRepeat.getText().toString();

        if(!password.equals(repeatPassword)){
            Toast.makeText(this,"Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User(username,password);
        mStudyDAO.insert(user);
        Toast.makeText(this,"Signup successful",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        finish();
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context,SignUpActivity.class);
        return intent;
    }
}
