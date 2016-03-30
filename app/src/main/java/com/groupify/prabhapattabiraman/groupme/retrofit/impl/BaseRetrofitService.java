package com.groupify.prabhapattabiraman.groupme.retrofit.impl;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class BaseRetrofitService {

    private Retrofit baseRetrofitClient;

    public BaseRetrofitService() {
        baseRetrofitClient = new Retrofit.Builder()
                .baseUrl("http://shrouded-everglades-40862.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getBaseClient() {
        return baseRetrofitClient;
    }
}
