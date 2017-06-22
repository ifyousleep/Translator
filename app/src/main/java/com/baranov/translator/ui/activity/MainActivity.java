package com.baranov.translator.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.baranov.translator.app.TranslatorApp;
import com.baranov.translator.model.Language;
import com.baranov.translator.presentation.view.MainView;
import com.baranov.translator.presentation.presenter.MainPresenter;

import com.arellomobile.mvp.MvpAppCompatActivity;

import com.baranov.translator.R;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.baranov.translator.ui.InterfaceCommunicator;
import com.baranov.translator.ui.fragment.EditorFragment;
import com.baranov.translator.ui.fragment.FavoriteFragment;
import com.baranov.translator.ui.fragment.PickLangFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MvpAppCompatActivity implements MainView, InterfaceCommunicator {

    @InjectPresenter
    MainPresenter mMainPresenter;

    @BindView(R.id.activity_main_toolbar)
    Toolbar mToolbar;

    @Inject
    SharedPreferences mSharedPrefs;

    private String[] mCodes;
    private Menu menu;
    private static final String DIALOG_PICK = "DialogPick";
    private static final String LANG_ITEMS = "LangItems";
    private static final String LANG_CODE = "LangCode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TranslatorApp.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.activity_main_container, EditorFragment.newInstance());
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.spinner_lang);
        menuItem.setTitle(mSharedPrefs.getString(LANG_CODE, "RU"));
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.spinner_lang:
                mMainPresenter.showDialog();
                break;
            case R.id.fav_lang:
                showFavorite();
                break;
            default:
                break;
        }
        return true;
    }

    private void showFavorite() {
        hideKeyboard(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_container, FavoriteFragment.newInstance());
        transaction.addToBackStack(FavoriteFragment.TAG);
        transaction.commit();
    }

    @Override
    public void populateLangs(@NonNull List<Language> langs) {
        String[] languages = new String[langs.size()];
        mCodes = new String[langs.size()];
        for (int i = 0; i < langs.size(); i++) {
            languages[i] = langs.get(i).getTitle();
            mCodes[i] = langs.get(i).getCode();
        }
        Bundle bundle = new Bundle();
        bundle.putStringArray(LANG_ITEMS, languages);
        FragmentManager manager = getSupportFragmentManager();
        PickLangFragment dialog = new PickLangFragment();
        dialog.setArguments(bundle);
        dialog.show(manager, DIALOG_PICK);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray(LANG_ITEMS, mCodes);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCodes = savedInstanceState.getStringArray(LANG_ITEMS);
    }

    @Override
    public void sendRequestCode(int which) {
        mSharedPrefs
                .edit()
                .putString(LANG_CODE, mCodes[which])
                .apply();
        MenuItem menuItem = menu.findItem(R.id.spinner_lang);
        menuItem.setTitle(mCodes[which]);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
