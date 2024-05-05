package com.example.murica_for_dummies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import com.example.murica_for_dummies.Misc.ThemeSelector;
import com.example.murica_for_dummies.database.Users.UsersRepository;
import com.example.murica_for_dummies.database.entities.Settings;
import com.example.murica_for_dummies.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {

    ToggleButton Default;
    ToggleButton Pink;
    ToggleButton Lime;
    ToggleButton OceanBlue;
    ToggleButton Imperial;
    ToggleButton Sunset;

    Button HomeButton;
    Button ConfirmButton;

    ActivitySettingsBinding binding;

    private UsersRepository usersRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeSelector.SetTheme(this);
        super.onCreate(savedInstanceState);

        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        usersRepository = UsersRepository.getRepository(getApplication());

        InitiateAttributes();

        HomeButton.setOnClickListener(v -> startActivity(WelcomePage.IntentFactory(getApplicationContext())));
        ConfirmButton.setOnClickListener(v -> startActivity(WelcomePage.IntentFactory(getApplicationContext())));

        Default.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) SelectTheme(R.style.Base_Theme_Murica_for_dummies,Default);
        });
        Pink.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) SelectTheme(R.style.Pink_Theme_Murica_for_dummies,Pink);
        });
        Lime.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) SelectTheme(R.style.Lime_Theme_Murica_for_dummies,Lime);
        });
        OceanBlue.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) SelectTheme(R.style.Ocean_blue_Theme_Murica_for_dummies,OceanBlue);
        });
        Imperial.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) SelectTheme(R.style.Imperial_Theme_Murica_for_dummies,Imperial);
        });
        Sunset.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) SelectTheme(R.style.Sunset_Theme_Murica_for_dummies,Sunset);
        });
    }

    private void SelectTheme(int Rid, ToggleButton pressedButton){
        Default.setChecked(false);
        Pink.setChecked(false);
        Lime.setChecked(false);
        OceanBlue.setChecked(false);
        Imperial.setChecked(false);
        Sunset.setChecked(false);

        pressedButton.setChecked(true);
        ThemeSelector.setCurrentSelectedTheme(Rid);

        String username = LoginActivity.actualUsername;

        Settings settings = new Settings(username, Rid);
        usersRepository.insertSettings(settings);


    }

    private void InitiateAttributes() {
        HomeButton = binding.HomeButton;
        ConfirmButton = binding.ConfirmButton;

        Default = binding.ColorButton1;
        Pink = binding.ColorButton2;
        Lime = binding.ColorButton3;
        OceanBlue = binding.ColorButton4;
        Imperial = binding.ColorButton5;
        Sunset = binding.ColorButton6;

        if (ThemeSelector.getSelectedTheme() == R.style.Base_Theme_Murica_for_dummies || ThemeSelector.getSelectedTheme() == 0){
            Default.setChecked(true);
        }

        if(ThemeSelector.getSelectedTheme() == R.style.Pink_Theme_Murica_for_dummies){
            Pink.setChecked(true);
        }

        if(ThemeSelector.getSelectedTheme() == R.style.Ocean_blue_Theme_Murica_for_dummies){
            OceanBlue.setChecked(true);
        }

        if(ThemeSelector.getSelectedTheme() == R.style.Lime_Theme_Murica_for_dummies){
            Lime.setChecked(true);
        }

        if(ThemeSelector.getSelectedTheme() == R.style.Imperial_Theme_Murica_for_dummies){
            Imperial.setChecked(true);
        }

        if(ThemeSelector.getSelectedTheme() == R.style.Sunset_Theme_Murica_for_dummies){
            Sunset.setChecked(true);
        }
    }

    public static Intent IntentFactory(Context context){
        return new Intent(context, SettingsActivity.class);
    }
}