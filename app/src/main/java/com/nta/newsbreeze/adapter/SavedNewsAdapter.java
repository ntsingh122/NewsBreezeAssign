package com.nta.newsbreeze.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.nta.newsbreeze.R;
import com.nta.newsbreeze.ui.ReadingActivity;
import com.nta.newsbreeze.database.ArticleDatabase;
import com.nta.newsbreeze.database.DAO;
import com.nta.newsbreeze.model.Article;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SavedNewsAdapter extends RecyclerView.Adapter<SavedNewsAdapter.SavedNewsViewHolder> {
    private List<Article> articles;
    private DAO articleDao;
    public SavedNewsAdapter(List<Article> articlesList) {
        this.articles = articlesList;

    }

    @NonNull
    @Override
    public SavedNewsViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saved_news_rv_layout, parent, false);
        return new SavedNewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedNewsAdapter.SavedNewsViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.headline.setText(article.getTitle());
        holder.content.setText(article.getDescription());
        holder.date.setText(article.getPublishedAt());
        //setting image
        Picasso.get().load(article.getUrlToImage()).fit().centerCrop().into(holder.thumbImageView, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {

            }
        });
        //opening news article
        holder.readButton.setOnClickListener(view -> openArticle(holder,article));
//        holder.deleteNewsImageButton.setVisibility(View.GONE);
        holder.deleteNewsImageButton.setOnClickListener(view -> deleteArticle(holder,article,position));


    }

    private void deleteArticle(SavedNewsViewHolder holder, Article article,int position) {

        articleDao = ArticleDatabase.getArticleDBInstance(holder.mContext).dao();
        articleDao.deleteArticle(article.getUrl());
//        articles.remove(position);
        SharedPreferences preferences =  holder.mContext.getSharedPreferences("state",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("buttonState"+article.getUrl(),false);
        editor.apply();

        notifyItemRemoved(position);

        setNewsData(articleDao.getAllSavedNews());
    }


    private void openArticle(SavedNewsViewHolder holder, Article article) {
        Intent intent = new Intent(holder.mContext, ReadingActivity.class);
        intent.putExtra("article", (Parcelable) article);
        holder.mContext.startActivity(intent);

    }

    public void setNewsData(List<Article> articles){
        this.articles =  articles;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class SavedNewsViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private ShapeableImageView thumbImageView;
        private ImageButton deleteNewsImageButton;
        private Button readButton;
        private TextView headline,content,date;
        private CardView newsCardView;

        public SavedNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            mContext= itemView.getContext();
            thumbImageView = itemView.findViewById(R.id.saved_news_img_iv);
            deleteNewsImageButton = itemView.findViewById(R.id.saved_news_delete_img_btn);
            readButton = itemView.findViewById(R.id.saved_news_read_btn);
            headline = itemView.findViewById(R.id.saved_news_headline_tv);
            content = itemView.findViewById(R.id.saved_news_content_tv);
            date = itemView.findViewById(R.id.saved_news_date_tv);

        }
    }
}
