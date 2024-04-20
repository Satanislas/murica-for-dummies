package com.example.murica_for_dummies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.murica_for_dummies.Mass.MassImperialToMetric;
import com.example.murica_for_dummies.Volume.VolumeImperialToMetric;

public class WelcomePage extends AppCompatActivity {

    com.example.murica_for_dummies.databinding.ActivityWelcomePageBinding binding;

    Button MassButton;
    Button VolumeButton;
    Button DistanceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = com.example.murica_for_dummies.databinding.ActivityWelcomePageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        InitAttributes();

        MassButton.setOnClickListener(v -> MassButtonCall());
        VolumeButton.setOnClickListener(v -> VolumeButtonCall());
        DistanceButton.setOnClickListener(v -> DistanceButtonCall());
    }

    private void DistanceButtonCall() {
        Toast.makeText(getApplicationContext(),"Distance Activity Here", Toast.LENGTH_SHORT).show();
    }

    private void VolumeButtonCall() {
        startActivity(VolumeImperialToMetric.IntentFactory(getApplicationContext()));
    }

    private void MassButtonCall() {
        startActivity(MassImperialToMetric.IntentFactory(getApplicationContext()));
    }

    private void InitAttributes() {
        MassButton = binding.MassButton;
        VolumeButton = binding.VolumeButton;
        DistanceButton = binding.DistanceButton;
    }

    public static Intent IntentFactory(Context context){
        return new Intent(context, WelcomePage.class);
    }
}