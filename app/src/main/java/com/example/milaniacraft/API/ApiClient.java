package com.example.milaniacraft.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://192.168.1.4/bucket/";
    public static final String IMAGES_URL = "http://192.168.1.4/bucket/Assets/";
    public static final String IMAGES_PROF = "http://192.168.1.4/bucket/image_profile/";

    private static Retrofit retrofit;

    public static Retrofit getClient() {

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
