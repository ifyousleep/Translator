package com.baranov.translator.di.modules;

/**
 * Created by Baranov on 21.06.2017 to Translator.
 */

import android.content.Context;

import com.baranov.translator.BuildConfig;
import com.baranov.translator.model.ResponseCacheInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {

    @Provides
    @Named("serverUrl")
    String provideServerUrl() {
        return BuildConfig.SERVER_URL;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(@Named("serverUrl") String serverUrl, Retrofit.Builder builder, OkHttpClient okHttpClient) {
        return builder
                .baseUrl(serverUrl)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    Retrofit.Builder provideRetrofitBuilder(Converter.Factory converterFactory) {
        return new Retrofit.Builder()
                .addConverterFactory(converterFactory);
    }

    @Provides
    @Singleton
    Converter.Factory provideConverterFactory(Gson gson) {
        return GsonConverterFactory
                .create(gson);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .create();
    }

    @Provides
    @Singleton
    Cache provideHttpCache(Context context) {
        return new Cache(new File(context.getCacheDir(),
                "apiResponses"), 5 * 1024 * 1024);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(ResponseCacheInterceptor responseCacheInterceptor, Cache cache) {
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addNetworkInterceptor(responseCacheInterceptor)
                .cache(cache);
        return client.build();
    }

    @Provides
    @Singleton
    ResponseCacheInterceptor provideResponseCacheInterceptor() {
        return new ResponseCacheInterceptor();
    }
}