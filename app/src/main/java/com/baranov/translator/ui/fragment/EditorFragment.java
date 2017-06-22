package com.baranov.translator.ui.fragment;

import android.content.ClipboardManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.baranov.translator.model.Favorite;
import com.baranov.translator.model.ResponseFromYandex;
import com.baranov.translator.presentation.presenter.EditorPresenter;
import com.baranov.translator.presentation.view.EditorView;
import com.arellomobile.mvp.MvpAppCompatFragment;

import com.baranov.translator.R;

import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.CLIPBOARD_SERVICE;

public class EditorFragment extends MvpAppCompatFragment implements EditorView {

    @InjectPresenter
    EditorPresenter mEditorPresenter;

    @BindView(R.id.textToTranslate)
    EditText mTextToTranslate;
    @BindView(R.id.buttonGo)
    AppCompatButton mButtonGo;
    @BindView(R.id.textAfterTranslate)
    TextView mTextAfterTranslate;
    @BindView(R.id.starImage)
    ImageView mStarImage;
    @BindView(R.id.copyImage)
    ImageView mCopyImage;

    private Unbinder mUnbinder;
    private Favorite mFavorite;

    @ProvidePresenter
    EditorPresenter provideEditorPresenter() {
        return new EditorPresenter();
    }

    public static EditorFragment newInstance() {
        EditorFragment fragment = new EditorFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_editor, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        getActivity().setTitle(getString(R.string.letsgo));
        return v;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mButtonGo.setEnabled(false);
        mButtonGo.setOnClickListener(
                v -> translateText(mTextToTranslate.getText().toString())
        );
        mStarImage.getDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        mCopyImage.getDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        mStarImage.setOnClickListener(
                v -> addFavorite(mFavorite)
        );
        mCopyImage.setOnClickListener(
                v -> copyTranslate(mTextAfterTranslate.getText().toString(), v)
        );

        TextWatcherP inputTextWatcher = new TextWatcherP(mTextToTranslate);
        mTextToTranslate.addTextChangedListener(inputTextWatcher);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        mButtonGo.setEnabled(true);
    }

    @Override
    public void showTranslate(ResponseFromYandex responseFromYandex) {
        mTextAfterTranslate.setText(responseFromYandex.getText().get(0));
        mFavorite = new Favorite(mTextToTranslate.getText().toString(), responseFromYandex.getText().get(0));
        mButtonGo.setEnabled(true);
    }

    @Override
    public void setVisibleStar(boolean visibleStar) {
        mStarImage.setVisibility(visibleStar ? View.VISIBLE : View.GONE);
        mCopyImage.setVisibility(visibleStar ? View.VISIBLE : View.GONE);
    }

    private void translateText(String text) {
        mEditorPresenter.translateText(text);
        mButtonGo.setEnabled(false);
    }

    private void addFavorite(Favorite favorite) {
        mEditorPresenter.addToFavorite(favorite);
    }

    @Override
    public void setColorFilter() {
        mStarImage.getDrawable().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void hideStuff(String text) {
        if (text.length() < 1) {
            mButtonGo.setEnabled(false);
            mTextAfterTranslate.setText("");
            mStarImage.getDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
            mStarImage.setVisibility(View.GONE);
            mCopyImage.setVisibility(View.GONE);
        } else {
            mButtonGo.setEnabled(true);
        }
    }

    private void copyTranslate(String text, View view) {
        final ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
        final android.content.ClipData clipData = android.content.ClipData
                .newPlainText("", text);
        clipboard.setPrimaryClip(clipData);
        Snackbar snackbar = Snackbar
                .make(view, R.string.succes, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void validateName(String text) {
        mEditorPresenter.hideStuff(text);
    }

    private class TextWatcherP implements TextWatcher {

        private EditText editText;

        private TextWatcherP(EditText et) {
            super();
            editText = et;
        }

        public void afterTextChanged(Editable s) {
            validateName(editText.getText().toString());
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
    }
}
