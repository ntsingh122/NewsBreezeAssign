package com.nta.newsbreeze.remote;

import com.nta.newsbreeze.R;
import com.nta.newsbreeze.model.NewsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface APIService {
//    String apiKey =  "d352076c8282431dae5080a315554b6c";
  //  https://newsapi.org/v2/top-headlines?country=us&apiKey=API_KEY

    @GET("v2/top-headlines")
    Call<NewsModel> getNewsHeadlines(@Query("country") String country,@Query("apiKey") String apiKey  );

    @GET("v2/everything")
    Call<NewsModel> searchNewsHeadlines(@Query("qInTitle") String queryInTitle,@Query("apiKey") String apiKey ,@Query("pageSize") int pageSize  );




}
