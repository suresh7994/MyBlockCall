package com.example.myblockcall;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    //    public static final String Register_URL="https://mebhichokidar.herokuapp.com/user/";
    public static RetrofitClient minstace;
    Retrofit retrofit;

    private RetrofitClient(String base_url) {
        retrofit = new Retrofit.Builder().baseUrl(base_url).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized RetrofitClient getInstance(String base_url) {
        if (minstace == null) {
            minstace = new RetrofitClient(base_url);
        }
        return minstace;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
