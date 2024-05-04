package com.example.murica_for_dummies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.murica_for_dummies.Misc.MyAdapter;
import com.example.murica_for_dummies.Misc.ThemeSelector;
import com.example.murica_for_dummies.database.Users.UsersRepository;
import com.example.murica_for_dummies.database.entities.History;
import com.example.murica_for_dummies.databinding.ActivityHistoryBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    ActivityHistoryBinding binding;
    Button HomeButton;
    Button ClearHistoryButton;
    RecyclerView recyclerView;
    UsersRepository usersRepository;

    public static final String NothingToShowMessage = "Nothing to show";

    private static ArrayList<String> strList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeSelector.SetTheme(this);
        super.onCreate(savedInstanceState);;
        setContentView(R.layout.activity_history);

        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        usersRepository = UsersRepository.getRepository(getApplication());

        InitAttributes();

        HomeButton.setOnClickListener(v -> startActivity(WelcomePage.IntentFactory(getApplicationContext())));
        ClearHistoryButton.setOnClickListener(v -> ClearHistoryCall());
    }

    private void ClearHistoryCall() {
        List<String> strList = new ArrayList<>();

        strList.add(NothingToShowMessage);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter adapter = new MyAdapter(strList, this);
        recyclerView.setAdapter(adapter);

        Toast.makeText(getApplicationContext(),"Cleared History",Toast.LENGTH_SHORT).show();
    }

    private void InitAttributes() {
        HomeButton = binding.HomeButton;
        ClearHistoryButton = binding.ClearHistoryButton;
        recyclerView = binding.recycler;


        //filling the recycler
        strList = new ArrayList<>();
        try {
            LiveData<List<History>> res = usersRepository.getHistoryByUser(LoginActivity.actualUsername);

            res.observe(this, historyList -> {
                for (History h: historyList) {
                    strList.add(String.format(h.getUnit1Name() +
                            " --> " +
                            "%.2f" +
                            " " +
                            h.getUnit2Name(),
                            h.getValue()));
                }
                Collections.reverse(strList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                MyAdapter adapter = new MyAdapter(strList, getApplicationContext());
                recyclerView.setAdapter(adapter);
            });

        }catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
            strList = new ArrayList<>();
            strList.add(NothingToShowMessage);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            MyAdapter adapter = new MyAdapter(strList, this);
            recyclerView.setAdapter(adapter);
        }
    }

    public static Intent IntentFactory(Context context){
        return new Intent(context, HistoryActivity.class);
    }
}