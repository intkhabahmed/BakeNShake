package com.intkhabahmed.bakenshake.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit sInstance;
    private static final Object LOCK = new Object();

    public static Retrofit getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new Retrofit.Builder()
                        .baseUrl("http://go.udacity.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        }
        return sInstance;
    }
}
