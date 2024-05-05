package com.example.murica_for_dummies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.example.murica_for_dummies.database.Users.UsersRepository;
import com.example.murica_for_dummies.database.entities.History;
import com.example.murica_for_dummies.database.entities.Users;

public class MainActivity extends AppCompatActivity {


    private static final String MAIN_ACTIVITY_USER_ID = "com.example.murica_for_dummies.MAIN_ACTIVITY_USER_ID";
    private static final int LOGGED_OUT = -1;
    private static final String SHARED_PREFERENCE_USERID_VALUE = "com.example.murica_for_dummies.SHARED_PREFERENCE_USERID_VALUE";
    int loggedInUserId = -1;
    public static Users user;

    private UsersRepository repository;

    public static final String SHARED_PREFERENCE_USERID_KEY = "com.example.murica_for_dummies.SHARED_PREFERENCE_USERID_KEY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        addDefaultUsers();
        loginUser();
        repository = UsersRepository.getRepository(getApplication());
        repository.getUserByUserId(loggedInUserId).observe(this, user -> {
            if (user != null) {
                MainActivity.user = user;
                invalidateOptionsMenu();
            }
        });


        if(loggedInUserId == -1)
        {
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }

        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
         */

        //startActivity(WelcomePage.IntentFactory(getApplicationContext()));   //STAN
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext())); //RAYAN
    }

    private void loginUser() {
        //check shared preference for logged in user
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFERENCE_USERID_KEY,
                Context.MODE_PRIVATE);
        loggedInUserId = sharedPreferences.getInt(SHARED_PREFERENCE_USERID_VALUE, LOGGED_OUT);
        if(loggedInUserId != LOGGED_OUT)
        {
            return;
        }
        //check intent for logged in user
        loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        if(loggedInUserId == LOGGED_OUT){
            return;
        }

        LiveData<Users> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            if(user != null){

                return;
            }
            else{
                invalidateOptionsMenu();
            }
        });

    }

    static Intent mainActivityIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, WelcomePage.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }

    public Users getUser() {
        return user;
    }

    private void addDefaultUsers() {
        UsersRepository repository = UsersRepository.getRepository(getApplication());
        repository.deleteAllUsers();
        Users admin = new Users("admin1", "admin1", true);
        repository.insertUser(admin);

        Users testUser1 = new Users("testuser1", "testuser1", false);
        repository.insertUser(testUser1);

        /*
        History history = new History("admin1", "unit1", "unit2", 2);
        repository.insertHistory(history);

        history = new History("testuser1", "gyat", "suppakippa", 45);
        repository.insertHistory(history);
         */

    }
}