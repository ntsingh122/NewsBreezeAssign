package com.nta.newsbreeze.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.nta.newsbreeze.R;
import com.nta.newsbreeze.database.ArticleDatabase;
import com.nta.newsbreeze.database.DAO;
import com.nta.newsbreeze.model.Article;
import com.nta.newsbreeze.ui.ReadingActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<Article> articles;
    private DAO articleDao;

    public NewsAdapter(List<Article> articlesList) {
        this.articles = articlesList;

    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_rv_layout, parent, false);
        return new NewsViewHolder(itemView);
    }

    private void setupCorners(ShapeableImageView imageView) {
        imageView.setShapeAppearanceModel(
                imageView.getShapeAppearanceModel()
                        .toBuilder()
                        .setAllCorners(CornerFamily.ROUNDED, 25.0f)
                        .build()
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.headline.setText(article.getTitle());
        setupCorners(holder.thumbImageView);
        holder.content.setText(article.getDescription());
        holder.saveNewsImageButton.setChecked(isSaved(holder.mContext, article));
        holder.date.setText(article.getPublishedAt());
        //setting image
        Picasso.get().load(article.getUrlToImage()).fit().centerCrop().into(holder.thumbImageView);
        //opening news article
        holder.readButton.setOnClickListener(view -> openArticle(holder, article));

        //saving news article


        holder.saveNewsImageButton.setOnClickListener(view -> {
            if (isSaved(holder.mContext, article))
                deleteArticle(holder, article);

            else
                saveArticle(holder, article);

        });

    }

    private void deleteArticle(NewsViewHolder holder, Article article) {

        SharedPreferences preferences = holder.mContext.getSharedPreferences("state", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("buttonState" + article.getUrl(), false);
        editor.apply();

        articleDao = ArticleDatabase.getArticleDBInstance(holder.mContext).dao();
        articleDao.deleteArticle(article.getUrl());
        Toast.makeText(holder.mContext, "Article removed", Toast.LENGTH_SHORT).show();
    }

    private boolean isSaved(Context mContext, Article article) {
        SharedPreferences preferences = mContext.getSharedPreferences("state", Context.MODE_PRIVATE);
        return preferences.getBoolean("buttonState" + article.getUrl(), false);

    }

    private void saveArticle(NewsViewHolder holder, Article article) {

        SharedPreferences preferences = holder.mContext.getSharedPreferences("state", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("buttonState" + article.getUrl(), true);
        editor.apply();

//        article.setButtonState(true);
        articleDao = ArticleDatabase.getArticleDBInstance(holder.mContext).dao();
        articleDao.InsertNewsArticle(article);

        Toast.makeText(holder.mContext, "Article Saved", Toast.LENGTH_SHORT).show();
    }


    private void openArticle(NewsViewHolder holder, Article article) {
        Intent intent = new Intent(holder.mContext, ReadingActivity.class);
        intent.putExtra("article", (Parcelable) article);
        holder.mContext.startActivity(intent);

    }

    public void setNewsData(List<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        private final ShapeableImageView thumbImageView;
        private final ToggleButton saveNewsImageButton;
        private final Button readButton;
        private final TextView headline;
        private final TextView content;
        private final TextView date;
        private final CardView newsCardView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            thumbImageView = itemView.findViewById(R.id.news_img_iv);
            saveNewsImageButton = itemView.findViewById(R.id.save_img_btn);
            readButton = itemView.findViewById(R.id.read_btn);
            headline = itemView.findViewById(R.id.news_headline_tv);
            content = itemView.findViewById(R.id.news_content_tv);
            date = itemView.findViewById(R.id.news_date_tv);
            newsCardView = itemView.findViewById(R.id.news_cardView);

        }
    }
}
