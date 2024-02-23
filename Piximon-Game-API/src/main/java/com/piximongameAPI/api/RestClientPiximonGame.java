package com.piximongameAPI.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClientPiximonGame {

    private static IAPIServicePiximonGame apiInstance;
    private static final String BASE_URL = "https://localhost:8082/";

    private RestClientPiximonGame() {
    }

    public synchronized static IAPIServicePiximonGame getApiServiceInstance() {
        if (apiInstance == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiInstance = retrofit.create(IAPIServicePiximonGame.class);
        }
        return apiInstance;
    }



}
