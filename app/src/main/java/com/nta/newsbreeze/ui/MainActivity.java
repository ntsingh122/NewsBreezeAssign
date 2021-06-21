package com.nta.newsbreeze.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchText = findViewById(R.id.search_box);
        progressBar = findViewById(R.id.progress_circular_bar);

        setupSearchBox();

        loadNews();

        mRecyclerView = findViewById(R.id.rv_news);
        mAdapter = new NewsAdapter(articlesList);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


    }

    private void setupSearchBox() {
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getTextFromSearchBox(searchText);
                    handled = true;
                }
                return handled;
            }
        });

    }

    private void getTextFromSearchBox(EditText searchText) {
        if (!TextUtils.isEmpty(searchText.getText())) {
            try {
                searchQuery(searchText.getText());

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else
            Toast.makeText(this, "Please Enter Search Text", Toast.LENGTH_SHORT).show();
    }

    private void searchQuery(Editable text) {
        progressBar.setVisibility(View.VISIBLE);
        ApiUtils.getAPIService().searchNewsHeadlines(text.toString(), getString(R.string.apiKey), 20).enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                if (response.body().getStatus().equalsIgnoreCase("ok") && !response.body().getArticles().isEmpty()) {
                    articlesList = response.body().getArticles();
                    mAdapter.setNewsData(articlesList);
                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
//                Snackbar.make(mRecyclerView,"Failed"+t.getLocalizedMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }


    private void loadNews() {
        articlesList = new ArrayList<>();
        ApiUtils.getAPIService().getNewsHeadlines("in", getString(R.string.apiKey)).enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, @NonNull Response<NewsModel> response) {
                if (response.body().getStatus() == "error")
                    onFailure(call, new Throwable("unable to fetch data"));
                else {
//                    NewsArticleRepo.getInstance().storeArticle(response.body());
                    articlesList = response.body().getArticles();
                }
                mAdapter.setNewsData(articlesList);
                progressBar.setVisibility(View.INVISIBLE);
//                Toast.makeText(MainActivity.this, "loaded successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error :" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tb_menu, menu);
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