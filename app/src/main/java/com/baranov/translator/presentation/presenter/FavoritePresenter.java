package com.baranov.translator.presentation.presenter;


import com.baranov.translator.app.TranslatorApp;
import com.baranov.translator.model.DaoSession;
import com.baranov.translator.model.Favorite;
import com.baranov.translator.model.FavoriteDao;
import com.baranov.translator.presentation.view.FavoriteView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import javax.inject.Inject;

@InjectViewState
public class FavoritePresenter extends MvpPresenter<FavoriteView> {

    @Inject
    DaoSession mDaoSession;

    private FavoriteDao mFavoriteDao;

    public FavoritePresenter() {
        TranslatorApp.getAppComponent().inject(this);
        mFavoriteDao = mDaoSession.getFavoriteDao();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void loadFavFromDb() {
        Query<Favorite> mModelQuery;
        mModelQuery = mFavoriteDao.queryBuilder().build();
        List<Favorite> list = mModelQuery.list();
        getViewState().showFavoriteList(list);
    }

    public void delFromDb(Favorite favorite) {
        mFavoriteDao.delete(favorite);
    }
}
