package com.example.newsapp;

import retrofit2.Retrofit;

public class ApiClientLogin {
    public static Retrofit retrofit;
    public static final String BASE_URL = "https://mediadwi.com/api/latihan/";
    public static Retrofit getRetrofitInstance(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .build();
        }
        return retrofit;
    }

}
