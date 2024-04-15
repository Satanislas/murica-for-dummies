package com.example.murica_for_dummies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.murica_for_dummies.Utils.Constants;
import com.example.murica_for_dummies.Utils.GetValue;
import com.example.murica_for_dummies.Utils.MassConverters;
import com.example.murica_for_dummies.databinding.ActivityMassImperialToMetricBinding;

public class MassImperialToMetric extends AppCompatActivity {

    ActivityMassImperialToMetricBinding binding;

    EditText OunceValueText;
    EditText PoundValueText;
    EditText TonValueText;
    TextView ResultText;
    Button ConvertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMassImperialToMetricBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        InitAttributes();

        ConvertButton.setOnClickListener(v -> ConvertButtonCall());
    }

    private void InitAttributes(){
        OunceValueText = binding.EditValueOunce;
        PoundValueText = binding.EditValuePound;
        TonValueText = binding.EditValueTon;
        ResultText = binding.ResultText;
        ConvertButton = binding.ConvertButton;
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
        result += MassConverters.ounceToKilograms(OunceValue);
        result += MassConverters.poundToKilograms(PoundValue);
        result += MassConverters.tonToKilograms(TonValue);

        //result print
        ResultText.setText(getString(R.string.MassMetricResult,result));
    }

    public static Intent IntentFactory(Context context){
        return new Intent(context, MassImperialToMetric.class);
    }
}