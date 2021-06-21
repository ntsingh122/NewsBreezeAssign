package com.nta.newsbreeze.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nta.newsbreeze.R;
import com.nta.newsbreeze.adapter.NewsAdapter;
import com.nta.newsbreeze.model.Article;
import com.nta.newsbreeze.model.NewsModel;
import com.nta.newsbreeze.remote.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<Article> articlesList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private NewsAdapter mAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = findViewById(R.id.my_toolbar);
//        setSupportActionBar(toolbar);

        loadNews();
        progressBar = findViewById(R.id.progress_circular_bar);
        mRecyclerView = findViewById(R.id.rv_news);
        mAdapter = new NewsAdapter(articlesList);
        mLayoutManager  = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);



    }

    private void loadNews() {
        articlesList = new ArrayList<>();
        ApiUtils.getAPIService().getNewsHeadlines("in",getString(R.string.apiKey)).enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, @NonNull Response<NewsModel> response) {
                if (response.body().getStatus()=="error")
                    onFailure(call,new Throwable("unable to fetch data"));
                else
                    articlesList = response.body().getArticles();
                mAdapter.setNewsData(articlesList);
                progressBar.setVisibility(View.GONE);
//                Toast.makeText(MainActivity.this, "loaded successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error :" +t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tb_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.saved_news_btn) {
            goToActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToActivity() {

        Intent intent = new Intent(this, SavedNewsActivity.class);
        startActivity(intent);
//        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }
}