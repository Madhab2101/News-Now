package com.example.newsnow;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Article> articleList = new ArrayList<>();
    NewsRecyclerAdapter adapter;
    LinearProgressIndicator progressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.news_recycler_view);
        progressIndicator = findViewById(R.id.progress_bar);

        setupRecyclerView();
        getNews();
    }

    void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsRecyclerAdapter(articleList);
        recyclerView.setAdapter(adapter);
    }

    void changeInProgress(boolean show) {
        if (show)
            progressIndicator.setVisibility(View.VISIBLE);
        else
            progressIndicator.setVisibility(View.INVISIBLE);
    }

    void getNews() {
        changeInProgress(true);

        new Thread(() -> {
            try {
                // Open and read the JSON file from the assets folder
                InputStreamReader isr = new InputStreamReader(getAssets().open("articles.json"));
                BufferedReader reader = new BufferedReader(isr);

                // Parse JSON into a list of Article objects using Gson
                Type articleListType = new TypeToken<List<Article>>() {}.getType();
                List<Article> realArticles = new Gson().fromJson(reader, articleListType);

                // Update the RecyclerView on the main thread
                runOnUiThread(() -> {
                    changeInProgress(false);
                    articleList.clear();
                    articleList.addAll(realArticles);
                    adapter.notifyDataSetChanged();
                });

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("MainActivity", "Error reading articles.json: " + e.getMessage());

                runOnUiThread(() -> changeInProgress(false));
            }
        }).start();
    }
}
