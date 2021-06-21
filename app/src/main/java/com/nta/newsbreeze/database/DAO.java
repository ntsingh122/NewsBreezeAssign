package com.nta.newsbreeze.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.nta.newsbreeze.model.Article;

import java.util.List;

//The dao for the news database
@Dao
public interface DAO {
    @Insert
    void InsertNewsArticle(Article article);

    @Query("SELECT * FROM Articles order by artId desc")
    List<Article> getAllSavedNews();

    @Query("DELETE FROM Articles WHERE url =:url")
    void deleteArticle(String url);

    @Update
    void updateArticleStatus(Article article);

    @Delete
    void deleteNews(Article article);
}