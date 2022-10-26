package com.migros.mkolay.network;


import android.app.Activity;
import android.content.Context;

import androidx.core.content.ContextCompat;

import com.migros.mkolay.R;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Activity activity;

    public static final String HEADER_CACHE_CONTROL = "Cache-Control";



    private static RetrofitClient instance;
    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    private static final long cacheSize = 30 * 1024 * 1024; //30mb

    private static Retrofit retrofit(String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .cache(cache())
                .addNetworkInterceptor(networkInterceptor()) // only used when network is on
                .addInterceptor(offlineInterceptor())
                .build();
    }

    private static Cache cache() {
        return new Cache(new File(com.migros.mkolay.AnalyticsApplication.getInstance().getCacheDir(), "MMKolay"), cacheSize);
    }

    private static Interceptor offlineInterceptor() {
        return new Interceptor() {
            @androidx.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.M)
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                /**
                 *  prevent caching when network is on. For that we use the "networkInterceptor"
                 */
                if (!com.migros.mkolay.AnalyticsApplication.hasNetwork()) {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build();

                    request = request.newBuilder()
                            .removeHeader(HEADER_CACHE_CONTROL)
                            .cacheControl(cacheControl)
                            .build();
                }

                return chain.proceed(request);
            }
        };
    }

    private static Interceptor networkInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Response response = chain.proceed(chain.request());

                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(1, TimeUnit.SECONDS)
                        .build();

                return response.newBuilder()
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                        .build();
            }
        };
    }





    public APIInterface getApi(Activity current) {
        this.activity = current;

        final String BASE_URL = activity.getResources().getString(R.string.apiWay);
        return retrofit(BASE_URL).create(APIInterface.class);
    }



}
