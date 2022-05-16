package com.anusha.books;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class MainActivity3 extends AppCompatActivity {
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        recyclerView = findViewById(R.id.rv);

        CustomAdapter adapter = new CustomAdapter(this,CategoryFilteredListHolder.filteredList);
        recyclerView.setAdapter(adapter);

    }

}