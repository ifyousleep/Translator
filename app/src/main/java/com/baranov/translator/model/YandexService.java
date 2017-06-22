package com.baranov.translator.model;

import com.baranov.translator.app.YandexApi;

import retrofit2.Call;

/**
 * Created by Baranov on 21.06.2017 to Translator.
 */

public class YandexService {

    private YandexApi mYandexApi;

    public YandexService(YandexApi yandexApi) {
        mYandexApi = yandexApi;
    }

    public Call<ResponseFromYandex> getTranslation(String apiKey, String textToTranslate, String lang) {
        return mYandexApi.getTranslation(apiKey, textToTranslate, lang, "1");
    }

    public Call<LangsFromYandex> getLangs(String apiKey, String ui) {
        return mYandexApi.getLangs(apiKey, ui);
    }
}
