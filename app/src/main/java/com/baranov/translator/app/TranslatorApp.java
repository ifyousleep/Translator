package com.baranov.translator.app;

import android.app.Application;

import com.baranov.translator.di.AppComponent;
import com.baranov.translator.di.DaggerAppComponent;

/**
 * Created by Baranov on 21.06.2017 to Translator.
 */

public class TranslatorApp extends Application {

    private static AppComponent sAppComponent;

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sAppComponent = DaggerAppComponent
                .builder()
                .application(this)
                .build();
    }
}
