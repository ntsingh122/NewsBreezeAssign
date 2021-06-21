package com.nta.newsbreeze.repository;

import com.nta.newsbreeze.model.Article;

import java.util.ArrayList;
import java.util.List;

public class NewsArticleRepo {
    private static volatile NewsArticleRepo ourInstance;

    private Article article;

    private NewsArticleRepo() {
    }

    public static synchronized NewsArticleRepo getInstance() {
        if (ourInstance == null) {
            synchronized (NewsArticleRepo.class) {
                ourInstance = new NewsArticleRepo();
            }
        }
        return ourInstance;
    }


    public void storeArticle(Article article) {
        this.article = article;

    }


    public boolean instanceIsThere() {
        return ourInstance != null;
    }

    public Article getArticle() {
        if (article == null)
            return new Article();
        return article;
    }
}
