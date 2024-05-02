package com.example.murica_for_dummies.Mass;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.murica_for_dummies.MainActivity;
import com.example.murica_for_dummies.R;
import com.example.murica_for_dummies.Utils.Constants;
import com.example.murica_for_dummies.Utils.GetValue;
import com.example.murica_for_dummies.Utils.MassConverters;
import com.example.murica_for_dummies.WelcomePage;
import com.example.murica_for_dummies.database.Users.UsersDatabase;
import com.example.murica_for_dummies.database.Users.UsersRepository;
import com.example.murica_for_dummies.database.entities.History;
import com.example.murica_for_dummies.databinding.ActivityMassMetricToImperialBinding;

public class MassMetricToImperial extends AppCompatActivity {


    ActivityMassMetricToImperialBinding binding;

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

        binding = ActivityMassMetricToImperialBinding.inflate(getLayoutInflater());
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
        startActivity(MassImperialToMetric.IntentFactory(getApplicationContext()));
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
        double kilo = GramValue / 1000 + KiloValue + TonValue * 1000;
        double resultOunce = MassConverters.kilogramToOunce(kilo);
        double resultPound = MassConverters.kilogramToPounds(kilo);
        double resultTon = MassConverters.kilogramToTon(kilo);

        //result print
        ResultTextOunce.setText(getString(R.string.MassOunceResult,resultOunce));
        ResultTextPound.setText(getString(R.string.MassPoundResult,resultPound));
        ResultTextTon.setText(getString(R.string.MassTonResult,resultTon));

        //adding history to database
        History histoOunce = new History(MainActivity.user.getLogin(),"Mass","Ounce",resultOunce);
        History histoPound = new History(MainActivity.user.getLogin(),"Mass","Pound",resultPound);
        History histoTon = new History(MainActivity.user.getLogin(),"Mass","Ton",resultTon);

        UsersRepository repo = UsersRepository.getRepository(getApplication());

        repo.insertHistory(histoOunce);
        repo.insertHistory(histoPound);
        repo.insertHistory(histoTon);
    }

    public static Intent IntentFactory(Context context){
        return new Intent(context, MassMetricToImperial.class);
    }


}