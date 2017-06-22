package com.baranov.translator.app;

import com.baranov.translator.model.LangsFromYandex;
import com.baranov.translator.model.ResponseFromYandex;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Baranov on 21.06.2017 to Translator.
 */

public interface YandexApi {

    @POST("api/v1.5/tr.json/translate")
    Call<ResponseFromYandex> getTranslation(@Query("key") String apiKey,
                                            @Query("text") String textToTranslate,
                                            @Query("lang") String lang,
                                            @Query("options") String opt);

    @POST("api/v1.5/tr.json/getLangs")
    Call<LangsFromYandex> getLangs(@Query("key") String apiKey,
                                   @Query("ui") String ui);
}
