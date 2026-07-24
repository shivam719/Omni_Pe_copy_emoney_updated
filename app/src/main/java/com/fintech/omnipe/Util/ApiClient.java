package com.fintech.omnipe.Util;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Lalit on 20-01-2017.
 */

public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
                    .readTimeout(10, TimeUnit.MINUTES)
                    .connectTimeout(10, TimeUnit.MINUTES)
                    .writeTimeout(10, TimeUnit.MINUTES)
                    .build();
            ////////////////////////////////////////////////////
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApplicationConstant.INSTANCE.baseUrl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;

    }
}
