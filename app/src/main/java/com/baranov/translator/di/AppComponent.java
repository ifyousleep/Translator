package com.baranov.translator.di;

import com.baranov.translator.app.TranslatorApp;
import com.baranov.translator.di.modules.ContextModule;
import com.baranov.translator.di.modules.YandexModule;
import com.baranov.translator.presentation.presenter.EditorPresenter;
import com.baranov.translator.presentation.presenter.FavoritePresenter;
import com.baranov.translator.presentation.presenter.MainPresenter;
import com.baranov.translator.ui.activity.MainActivity;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by Baranov on 21.06.2017 to Translator.
 */


@Singleton
@Component(modules = {
        ContextModule.class,
        YandexModule.class
})

public interface AppComponent {

    void inject (MainActivity mainActivity);

    void inject (EditorPresenter editorPresenter);

    void inject (MainPresenter mainPresenter);

    void inject (FavoritePresenter favoritePresenter);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(TranslatorApp application);
        AppComponent build();
    }
}
