package com.baranov.translator.di.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.baranov.translator.app.TranslatorApp;
import com.baranov.translator.model.DaoMaster;
import com.baranov.translator.model.DaoSession;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Baranov on 21.06.2017 to Translator.
 */

@Module
public class ContextModule {

    @Provides
    public Context provideContext(TranslatorApp app) {
        return app.getApplicationContext();
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPrefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    DaoSession daoSession(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "translator-db");
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDb());
        return daoMaster.newSession();
    }
}
