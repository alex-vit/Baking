package com.alexvit.baking.data;

import android.content.Context;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alexander.vitjukov on 08.08.2017.
 */

public class RecipeServiceBuilder {

    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";
    private static final String CACHE_FILE = "response-cache";

    private Context context;
    private RecipeService service;

    public RecipeServiceBuilder(Context context) {
        this.context = context;
        this.service = build();
    }

    public RecipeService get() {
        return service;
    }

    private RecipeService build() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(buildClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(RecipeService.class);
    }

    // https://stackoverflow.com/questions/23429046/can-retrofit-with-okhttp-use-cache-data-when-offline
    private OkHttpClient buildClient() {

        final Interceptor cacheInterceptor = buildCacheInterceptor();
        final Cache cache = buildCache();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.networkInterceptors().add(cacheInterceptor);
        builder.cache(cache);

        return builder.build();
    }

    private Cache buildCache() {
        File cacheFile = new File(context.getCacheDir(), CACHE_FILE);
        int cacheSize = 10 * 1024 * 1024;
        return new Cache(cacheFile, cacheSize);
    }

    private Interceptor buildCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                int maxAge = 300; // read from cache for 5 minutes
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            }
        };
    }
}
