package com.baranov.translator.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.baranov.translator.model.ResponseFromYandex;

public interface EditorView extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void showError(String error);

    void showTranslate(ResponseFromYandex responseFromYandex);

    void setVisibleStar(boolean visibleStar);

    void setColorFilter();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void hideStuff(String text);
}
