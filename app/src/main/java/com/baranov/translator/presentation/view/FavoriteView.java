package com.baranov.translator.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.baranov.translator.model.Favorite;

import java.util.List;

public interface FavoriteView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showFavoriteList(List<Favorite> favorite);
}
