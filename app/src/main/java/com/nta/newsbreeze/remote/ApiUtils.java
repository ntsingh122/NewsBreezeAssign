package com.nta.newsbreeze.remote;

public class ApiUtils {
    public static final String baseUrl = "https://newsapi.org/";

    private ApiUtils() {
    }

    public static APIService getAPIService() {
        return ApiClient.getClient(baseUrl).create(APIService.class);
    }

}