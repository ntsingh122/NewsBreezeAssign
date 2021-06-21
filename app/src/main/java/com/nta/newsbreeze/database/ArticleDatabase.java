package com.nta.newsbreeze.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.Room;
import android.content.Context;

import com.nta.newsbreeze.model.Article;

@Database(entities = Article.class,
          version = 1,
          exportSchema = false  )
public abstract class ArticleDatabase extends RoomDatabase {
    private static ArticleDatabase articleDB = null;

    public abstract DAO dao();

    public static ArticleDatabase getArticleDBInstance(Context context) {
        if (articleDB == null){
            articleDB = Room.databaseBuilder(context.getApplicationContext(),
                    ArticleDatabase.class,
                    "articledb")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return articleDB;
    }
}
