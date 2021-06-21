package com.nta.newsbreeze.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nta.newsbreeze.R;
import com.nta.newsbreeze.adapter.SavedNewsAdapter;
import com.nta.newsbreeze.database.ArticleDatabase;
import com.nta.newsbreeze.database.DAO;
import com.nta.newsbreeze.model.Article;

import java.util.ArrayList;
import java.util.List;

public class SavedNewsActivity extends AppCompatActivity {

    private List<Article> articlesList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private SavedNewsAdapter mAdapter;
    private DAO articleDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_news);


        mRecyclerView = findViewById(R.id.saved_news_rv);
        mAdapter = new SavedNewsAdapter(articlesList);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);
        loadSavedNews();

    }

    private void loadSavedNews() {
        articleDao = ArticleDatabase.getArticleDBInstance(this).dao();
        articlesList = articleDao.getAllSavedNews();
        if (articlesList != null)
            mAdapter.setNewsData(articlesList);

    }
}