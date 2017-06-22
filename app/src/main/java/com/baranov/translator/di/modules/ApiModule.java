package com.baranov.translator.di.modules;

/**
 * Created by Baranov on 21.06.2017 to Translator.
 */

import com.baranov.translator.app.YandexApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {RetrofitModule.class})
public class ApiModule {

    @Provides
    @Singleton
    public YandexApi provideApi(Retrofit retrofit) {
        return retrofit.create(YandexApi.class);
    }
}
