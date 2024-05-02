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
import com.example.murica_for_dummies.Mass.MassImperialToMetric;
import com.example.murica_for_dummies.R;
import com.example.murica_for_dummies.Utils.Constants;
import com.example.murica_for_dummies.Utils.GetValue;
import com.example.murica_for_dummies.Utils.MassConverters;
import com.example.murica_for_dummies.Utils.VolumeConverters;
import com.example.murica_for_dummies.WelcomePage;
import com.example.murica_for_dummies.database.Users.UsersRepository;
import com.example.murica_for_dummies.database.entities.History;
import com.example.murica_for_dummies.databinding.ActivityMassMetricToImperialBinding;
import com.example.murica_for_dummies.databinding.ActivityVolumeMetricToImperialBinding;

public class VolumeMetricToImperial extends AppCompatActivity {

    ActivityVolumeMetricToImperialBinding binding;

    EditText GramValueText;
    EditText KiloValueText;
    EditText TonValueText;
    TextView ResultTextOunce;
    TextView ResultTextPound;
    TextView ResultTextTon;
    Button ConvertButton;
    Button HomeButton;
    Button SwapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityVolumeMetricToImperialBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        InitAttributes();

        //Click to convert
        ConvertButton.setOnClickListener(v -> ConvertButtonCall());

        //Press enter to convert
        //GramValueText.setOnKeyListener(this::PressEnter); DISABLED BECAUSE USER PREFER TO SWITCH
        //KiloValueText.setOnKeyListener(this::PressEnter); TO THE TWO OTHER EDITTEXT
        TonValueText.setOnKeyListener(this::PressEnter);

        //Click to come back
        HomeButton.setOnClickListener(v -> HomeButtonCall());
        //Click to swap
        SwapButton.setOnClickListener(v -> SwapButtonCall());
    }

    private void SwapButtonCall() {
        startActivity(VolumeImperialToMetric.IntentFactory(getApplicationContext()));
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
        GramValueText = binding.EditValueGram;
        KiloValueText = binding.EditValueKilo;
        TonValueText = binding.EditValueTon;
        ResultTextOunce = binding.ResultTextOunce;
        ResultTextPound = binding.ResultTextPound;
        ResultTextTon = binding.ResultTextTon;
        ConvertButton = binding.ConvertButton;
        HomeButton = binding.HomeButton;
        SwapButton = binding.SwapConverter;
    }

    private void ConvertButtonCall(){
        //setup
        String GramValueString = GramValueText.getText().toString();
        String KiloValueString = KiloValueText.getText().toString();
        String TonValueString = TonValueText.getText().toString();

        double GramValue = GetValue.convert(GramValueString);
        double KiloValue = GetValue.convert(KiloValueString);
        double TonValue = GetValue.convert(TonValueString);

        //error checks
        {
            boolean error = false;
            if (GramValue == Constants.ERROR_VALUE) {
                error = true;
                GramValueText.setText(Constants.ERROR_TEXT);
            }
            if (KiloValue == Constants.ERROR_VALUE) {
                error = true;
                KiloValueText.setText(Constants.ERROR_TEXT);
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
        double kilo = GramValue / 1000 + KiloValue / 100 + TonValue;
        double resultOunce = VolumeConverters.litersToGill(kilo);
        double resultPound = VolumeConverters.litersToPint(kilo);
        double resultTon = VolumeConverters.litersToGallon(kilo);

        //result print
        ResultTextOunce.setText(getString(R.string.VolumeGillResult,resultOunce));
        ResultTextPound.setText(getString(R.string.VolumePintResult,resultPound));
        ResultTextTon.setText(getString(R.string.VolumeGallonResult,resultTon));

        try {
            History histoOunce = new History(MainActivity.user.getLogin(), "Volume", "Gill", resultOunce);
            History histoPound = new History(MainActivity.user.getLogin(), "Volume", "Pint", resultPound);
            History histoTon = new History(MainActivity.user.getLogin(), "Volume", "Gallon", resultTon);

            UsersRepository repo = UsersRepository.getRepository(getApplication());

            repo.insertHistory(histoOunce);
            repo.insertHistory(histoPound);
            repo.insertHistory(histoTon);
        }
        catch(Exception e){
            Toast.makeText(this, "Couldn't save history : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static Intent IntentFactory(Context context){
        return new Intent(context, VolumeMetricToImperial.class);
    }
}