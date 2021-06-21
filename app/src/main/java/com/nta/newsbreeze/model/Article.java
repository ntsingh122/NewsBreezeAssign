package com.nta.newsbreeze.model;

import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "Articles")
public class Article implements Serializable, Parcelable {


    public final static Creator<Article> CREATOR = new Creator<Article>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Article createFromParcel(android.os.Parcel in) {
            return new Article(in);
        }

        public Article[] newArray(int size) {
            return (new Article[size]);
        }

    };
    private final static long serialVersionUID = -970323314293711197L;
    @PrimaryKey(autoGenerate = true)
    int artId;
    @ColumnInfo(name = "author")
    @SerializedName("author")
    @Expose
    private String author;
    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    private String title;
    @ColumnInfo(name = "description")
    @SerializedName("description")
    @Expose
    private String description;
    @ColumnInfo(name = "url")
    @SerializedName("url")
    @Expose
    private String url;
    @ColumnInfo(name = "urlToImage")
    @SerializedName("urlToImage")
    @Expose
    private String urlToImage;
    @ColumnInfo(name = "publishedAt")
    @SerializedName("publishedAt")
    @Expose
    private String publishedAt;
    @ColumnInfo(name = "content")
    @SerializedName("content")
    @Expose
    private String content;
    @ColumnInfo(name = "buttonState")
    @SerializedName("buttonState")
    private Boolean buttonState;

    protected Article(android.os.Parcel in) {
//        this.source = ((Source) in.readValue((Source.class.getClassLoader())));
        this.author = ((String) in.readValue((String.class.getClassLoader())));
//        this.buttonState = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.urlToImage = ((String) in.readValue((String.class.getClassLoader())));
        this.publishedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.content = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Article() {
    }

//    public Source getSource() {
//        return source;
//    }
//
//    public void setSource(Source source) {
//        this.source = source;
//    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt.substring(0, 10);
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getArtId() {
        return artId;
    }

    public void setArtId(int artId) {
        this.artId = artId;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
//        dest.writeValue(source);
        dest.writeValue(author);
        dest.writeValue(title);
        dest.writeValue(description);
        dest.writeValue(url);
        dest.writeValue(urlToImage);
        dest.writeValue(publishedAt);
        dest.writeValue(content);
//        dest.writeValue(buttonState);
    }

    public int describeContents() {
        return 0;
    }

    public Boolean getButtonState() {
        return buttonState;
    }

    public void setButtonState(Boolean buttonState) {
        this.buttonState = buttonState;
    }
}
