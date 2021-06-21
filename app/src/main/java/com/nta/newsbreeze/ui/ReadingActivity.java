package com.nta.newsbreeze.ui;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nta.newsbreeze.R;
import com.nta.newsbreeze.databinding.ActivityReadingBinding;
import com.nta.newsbreeze.model.Article;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ReadingActivity extends AppCompatActivity {

    private ActivityReadingBinding binding;
    private Article article;
    private ImageView newsImageView;
    private TextView newsContent;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityReadingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent i  = getIntent();
        article = (Article)i.getParcelableExtra("article");
//        article = NewsArticleRepo.getInstance().getArticle();
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        fab = binding.saveReadNewsFab;
        newsImageView = binding.readNewsImgIv;
        newsContent = findViewById(R.id.read_news_content_tv);

        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(article.getTitle());
        setNews(article);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Shared", Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void setNews(Article article) {
        newsContent.setText(article.getDescription());
//        Toast.makeText(this, article.getContent(), Toast.LENGTH_SHORT).show();
        Picasso.get().load(article.getUrlToImage()).fit().centerCrop().into(newsImageView, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(ReadingActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}