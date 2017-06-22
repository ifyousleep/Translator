package com.baranov.translator.presentation.presenter;


import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.baranov.translator.BuildConfig;
import com.baranov.translator.app.TranslatorApp;
import com.baranov.translator.model.DaoSession;
import com.baranov.translator.model.Favorite;
import com.baranov.translator.model.FavoriteDao;
import com.baranov.translator.model.ResponseFromYandex;
import com.baranov.translator.model.YandexService;
import com.baranov.translator.presentation.view.EditorView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class EditorPresenter extends MvpPresenter<EditorView> {

    @Inject
    YandexService mYandexService;
    @Inject
    DaoSession mDaoSession;
    @Inject
    SharedPreferences mSharedPrefs;

    private FavoriteDao mFavoriteDao;
    private static final String LANG_CODE = "LangCode";

    public EditorPresenter() {
        TranslatorApp.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mFavoriteDao = mDaoSession.getFavoriteDao();
    }

    public void translateText(String text) {
        String langCode = mSharedPrefs.getString(LANG_CODE, "ru");
        Call<ResponseFromYandex> call = mYandexService.getTranslation(BuildConfig.SERVER_KEY, text, langCode);
        call.enqueue(new Callback<ResponseFromYandex>() {
            @Override
            public void onResponse(@NonNull Call<ResponseFromYandex> call, @NonNull Response<ResponseFromYandex> response) {
                if (response.isSuccessful()) {
                    getViewState().showTranslate(response.body());
                    getViewState().setVisibleStar(true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseFromYandex> call, @NonNull Throwable t) {
                getViewState().showError(t.getLocalizedMessage());
            }
        });
    }

    public void addToFavorite(Favorite favorite) {
        mFavoriteDao.insertOrReplace(favorite);
        getViewState().setColorFilter();
    }

    public void hideStuff(String text) {
        getViewState().hideStuff(text);
    }

    @Override
    public void onDestroy() {
        mFavoriteDao.getDatabase().close();
    }
}
