package com.example.murica_for_dummies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.murica_for_dummies.Misc.MyAdapter;
import com.example.murica_for_dummies.database.History.HistoryRepository;
import com.example.murica_for_dummies.database.entities.History;
import com.example.murica_for_dummies.databinding.ActivityHistoryBinding;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    ActivityHistoryBinding binding;
    Button HomeButton;
    Button ClearHistoryButton;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);

        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        InitAttributes();

        HomeButton.setOnClickListener(v -> startActivity(WelcomePage.IntentFactory(getApplicationContext())));
        ClearHistoryButton.setOnClickListener(v -> ClearHistoryCall());
    }

    private void ClearHistoryCall() {
        Toast.makeText(getApplicationContext(),"delete everything... EVERYTHING!",Toast.LENGTH_SHORT).show();
    }

    private void InitAttributes() {
        HomeButton = binding.HomeButton;
        ClearHistoryButton = binding.ClearHistoryButton;
        recyclerView = binding.recycler;


        //filling the recycler
        LiveData<List<History>> res = HistoryRepository.getRepository(getApplication(),"historyTable").getAllHistory();

        List<History> list = res.getValue();
        List<String> strList = new ArrayList<>();
        for(History h : list){
            strList.add(String.format(h.getUnit1Name() + " --> " + "%d.2" + " "  + h.getUnit2Name(),h.getValue() ));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter adapter = new MyAdapter(strList, this);
        recyclerView.setAdapter(adapter);

    }

    public static Intent IntentFactory(Context context){
        return new Intent(context, HistoryActivity.class);
    }
}