package com.baranov.translator.di.modules;

import com.baranov.translator.app.YandexApi;
import com.baranov.translator.model.YandexService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Baranov on 21.06.2017 to Translator.
 */


@Module(includes = {ApiModule.class})
public class YandexModule {

    @Provides
    @Singleton
    public YandexService provideYandexService(YandexApi yandexApi) {
        return new YandexService(yandexApi);
    }
}
