package com.nta.newsbreeze.model;


import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class NewsModel implements Serializable, Parcelable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("totalResults")
    @Expose
    private Integer totalResults;
    @SerializedName("articles")
    @Expose
    private List<Article> articles = null;
    public final static Creator<NewsModel> CREATOR = new Creator<NewsModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public NewsModel createFromParcel(android.os.Parcel in) {
            return new NewsModel(in);
        }

        public NewsModel[] newArray(int size) {
            return (new NewsModel[size]);
        }

    }
            ;
    private final static long serialVersionUID = -2903543598617270610L;

    protected NewsModel(android.os.Parcel in) {
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.totalResults = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.articles, (com.nta.newsbreeze.model.Article.class.getClassLoader()));
    }

    public NewsModel() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(totalResults);
        dest.writeList(articles);
    }

    public int describeContents() {
        return  0;
    }

}
