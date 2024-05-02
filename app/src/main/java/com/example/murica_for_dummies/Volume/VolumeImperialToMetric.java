package com.example.murica_for_dummies.Volume;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.murica_for_dummies.MainActivity;
import com.example.murica_for_dummies.R;
import com.example.murica_for_dummies.Utils.Constants;
import com.example.murica_for_dummies.Utils.GetValue;
import com.example.murica_for_dummies.Utils.MassConverters;
import com.example.murica_for_dummies.Utils.VolumeConverters;
import com.example.murica_for_dummies.Volume.VolumeMetricToImperial;
import com.example.murica_for_dummies.WelcomePage;
import com.example.murica_for_dummies.database.History.HistoryRepository;
import com.example.murica_for_dummies.database.entities.History;
import com.example.murica_for_dummies.databinding.ActivityMassImperialToMetricBinding;
import com.example.murica_for_dummies.databinding.ActivityVolumeImperialToMetricBinding;

public class VolumeImperialToMetric extends AppCompatActivity {

    ActivityVolumeImperialToMetricBinding binding;

    EditText OunceValueText;
    EditText PoundValueText;
    EditText TonValueText;
    TextView ResultText;
    Button ConvertButton;
    Button HomeButton;
    Button SwapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityVolumeImperialToMetricBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        InitAttributes();

        //Click to convert
        ConvertButton.setOnClickListener(v -> ConvertButtonCall());

        //Press enter to convert
        //OunceValueText.setOnKeyListener(this::PressEnter); DISABLED BECAUSE USER PREFER TO SWITCH
        //PoundValueText.setOnKeyListener(this::PressEnter); TO THE TWO OTHER EDITTEXT
        TonValueText.setOnKeyListener(this::PressEnter);

        //Click to come back
        HomeButton.setOnClickListener(v -> HomeButtonCall());
        //Click to swap converter
        SwapButton.setOnClickListener(v -> SwapButtonCall());
    }

    private void SwapButtonCall() {
        startActivity(VolumeMetricToImperial.IntentFactory(getApplicationContext()));
    }

    private void HomeButtonCall() {
        startActivity(WelcomePage.IntentFactory(getApplicationContext()));
    }

    private boolean PressEnter(View v, int keyCode, KeyEvent event) {
        // If the event is a key-down event on the "enter" button
        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)) {
            // Perform action on key press
            ConvertButtonCall();

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            return true;
        }
        return false;
    }

    private void InitAttributes(){
        OunceValueText = binding.EditValueOunce;
        PoundValueText = binding.EditValuePound;
        TonValueText = binding.EditValueTon;
        ResultText = binding.ResultText;
        ConvertButton = binding.ConvertButton;
        HomeButton = binding.HomeButton;
        SwapButton = binding.SwapConverter;
    }

    private void ConvertButtonCall(){
        //setup
        String OunceValueString = OunceValueText.getText().toString();
        String PoundValueString = PoundValueText.getText().toString();
        String TonValueString = TonValueText.getText().toString();

        double OunceValue = GetValue.convert(OunceValueString);
        double PoundValue = GetValue.convert(PoundValueString);
        double TonValue = GetValue.convert(TonValueString);

        //error checks
        {
            boolean error = false;
            if (OunceValue == Constants.ERROR_VALUE) {
                error = true;
                OunceValueText.setText(Constants.ERROR_TEXT);
            }
            if (PoundValue == Constants.ERROR_VALUE) {
                error = true;
                PoundValueText.setText(Constants.ERROR_TEXT);
            }
            if (TonValue == Constants.ERROR_VALUE) {
                error = true;
                TonValueText.setText(Constants.ERROR_TEXT);
            }
            if (error) {
                return;
            }
        }

        //result compute
        double result = 0;
        result += VolumeConverters.gillToLiters(OunceValue);
        result += VolumeConverters.pintToLiters(PoundValue);
        result += VolumeConverters.gallonToLiters(TonValue);

        //result print
        ResultText.setText(getString(R.string.VolumeMetricResult,result));

        try{
            History histo = new History(MainActivity.user.getLogin(), "Volume", "L", result);
            HistoryRepository.getRepository(getApplication(), "historyTable").insertHistory(histo);
        }
        catch(Exception e){
            Toast.makeText(this, "Couldn't save in history : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static Intent IntentFactory(Context context){
        return new Intent(context, VolumeImperialToMetric.class);
    }
}