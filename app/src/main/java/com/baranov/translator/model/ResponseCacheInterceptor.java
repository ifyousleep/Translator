package com.baranov.translator.model;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;

/**
 * Created by Baranov on 21.06.2017 to Translator.
 */

public class ResponseCacheInterceptor implements Interceptor {

    public ResponseCacheInterceptor() {

    }

    @Override
    public okhttp3.Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        okhttp3.Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .header("Cache-Control", "public, max-age=" + 60)
                .build();
    }
}