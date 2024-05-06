package com.example.murica_for_dummies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.murica_for_dummies.Misc.ThemeSelector;
import com.example.murica_for_dummies.database.Users.UsersDAO;
import com.example.murica_for_dummies.database.Users.UsersRepository;
import com.example.murica_for_dummies.database.entities.Users;
import com.example.murica_for_dummies.databinding.ActivityAdminPageBinding;
import com.example.murica_for_dummies.databinding.ActivityCreateAccountBinding;

public class AdminPageActivity extends AppCompatActivity {

    ActivityAdminPageBinding binding;

    private UsersRepository usersRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeSelector.SetTheme(this);
        binding = ActivityAdminPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        usersRepository = UsersRepository.getRepository(getApplication());

        binding.createUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewUser(binding.userNameLoginEditText.getText().toString(), binding.passwordLoginEditText.getText().toString(), binding.confirmPasswordLoginEditText.getText().toString());
            }
        });

        binding.createAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAdmin(binding.userNameLoginEditText.getText().toString(), binding.passwordLoginEditText.getText().toString(), binding.confirmPasswordLoginEditText.getText().toString());
            }
        });

        binding.deleteUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteUser(binding.UsernameDelete.getText().toString());
            }
        });
    }


    private void createNewUser(String username, String password, String confirmPassword) {
        if (!userAlreadyExists(username)) {
            if (passwordCorrect(password, confirmPassword)) {
                Users user = new Users(username, password, false);
                usersRepository.insertUser(user);
                Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
                startActivity(intent);
                finish();
            }
        }
    }

    private void createNewAdmin(String username, String password, String confirmPassword) {
        if (!userAlreadyExists(username)) {
            if (passwordCorrect(password, confirmPassword)) {
                Users user = new Users(username, password, true);
                usersRepository.insertUser(user);
                Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
                startActivity(intent);
                finish();
            }
        }
    }

    private boolean userAlreadyExists(String username) {
        Users user = usersRepository.getUserByUserName(username).getValue();
        if (user != null) {
            toastMaker("User already exists");
            return true;
        }
        return false;
    }

    private void deleteUser(String username){
        Users user = usersRepository.getUserByUserName(username).getValue();
        if (user == null) {
            toastMaker("User don't exists");
        } else if (user.getLogin().equals(LoginActivity.actualUsername)) {
            toastMaker("Don't try to send yourself to the shadow realm..");
        } else {
            usersRepository.deleteUserByUsername(username);
        }
    }

    private boolean passwordCorrect(String password, String confirmPassword) {
        if (password.isEmpty()) {
            toastMaker("Password not created");
            return false;
        } else if (password.length() < 5) {
            toastMaker("Password should be at least more than 5 characters");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            toastMaker("Passwords are not the same");
            return false;
        }
        return true;
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent adminPageIntentFactory(Context context) {
        return new Intent(context, AdminPageActivity.class);
    }
}