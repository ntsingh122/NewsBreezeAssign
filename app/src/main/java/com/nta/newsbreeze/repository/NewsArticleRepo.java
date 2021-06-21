package com.nta.newsbreeze.repository;

import com.nta.newsbreeze.model.NewsModel;

public class NewsArticleRepo {
    private static volatile NewsArticleRepo ourInstance;

    private NewsModel newsArticle;

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


    public void storeArticle(NewsModel article) {
        this.newsArticle = article;

    }


    public boolean instanceIsThere() {
        return ourInstance != null;
    }

    public NewsModel getArticle() {
        if (newsArticle == null)
            return new NewsModel();
        return newsArticle;
    }
}
