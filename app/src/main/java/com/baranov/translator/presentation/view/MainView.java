package com.baranov.translator.presentation.view;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.baranov.translator.model.Language;

import java.util.List;

public interface MainView extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void showError(String error);

    @StateStrategyType(SkipStrategy.class)
    void populateLangs(@NonNull List<Language> langs);

}
