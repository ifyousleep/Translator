package com.baranov.translator.presentation.presenter;

import android.support.annotation.NonNull;

import com.baranov.translator.BuildConfig;
import com.baranov.translator.app.TranslatorApp;
import com.baranov.translator.model.DaoSession;
import com.baranov.translator.model.LangsFromYandex;
import com.baranov.translator.model.Language;
import com.baranov.translator.model.LanguageDao;
import com.baranov.translator.model.YandexService;
import com.baranov.translator.presentation.view.MainView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @Inject
    YandexService mYandexService;
    @Inject
    DaoSession mDaoSession;

    private LanguageDao mLanguageDao;

    public MainPresenter() {
        TranslatorApp.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        mLanguageDao = mDaoSession.getLanguageDao();
        if (mLanguageDao.count() > 0) {
            return;
        } else
            loadLangs(false);
    }

    public void showDialog() {
        if (mLanguageDao.count() > 0) {
            loadLangsFromDb();
        } else
            loadLangs(false);
    }

    private void loadLangs(boolean isShow) {
        Call<LangsFromYandex> call = mYandexService.getLangs(BuildConfig.SERVER_KEY, "ru");
        call.enqueue(new Callback<LangsFromYandex>() {
            @Override
            public void onResponse(@NonNull Call<LangsFromYandex> call, @NonNull Response<LangsFromYandex> response) {
                if (response.isSuccessful()) {
                    List<Language> result = new ArrayList<>();
                    for (Map.Entry<String, String> entry : response.body().getLangs().entrySet()) {
                        Language language = new Language(entry.getKey(), entry.getValue());
                        result.add(language);
                        mLanguageDao.insertOrReplace(language);
                    }
                    if (isShow) {
                        Collections.sort(result, (Language left, Language right) -> left.getTitle().compareToIgnoreCase(right.getTitle()));
                        getViewState().populateLangs(result);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LangsFromYandex> call, @NonNull Throwable t) {
                getViewState().showError(t.getLocalizedMessage());
            }
        });
    }

    private void loadLangsFromDb() {
        Query<Language> mModelQuery;
        mModelQuery = mLanguageDao.queryBuilder().orderAsc(LanguageDao.Properties.Title).build();
        List<Language> list = mModelQuery.list();
        getViewState().populateLangs(list);
    }

    @Override
    public void onDestroy() {
        mLanguageDao.getDatabase().close();
    }
}
