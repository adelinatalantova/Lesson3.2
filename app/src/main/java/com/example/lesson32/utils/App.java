package com.example.lesson32.utils;

import android.app.Application;

import com.example.lesson32.data.remote.PostApi;
import com.example.lesson32.data.remote.RetrofitClient;

public class App extends Application {

    private RetrofitClient client;
    public static PostApi api;

    @Override
    public void onCreate() {
        super.onCreate();
        client = new RetrofitClient();
        api = client.provideApi();
    }
}
