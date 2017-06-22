package com.baranov.translator.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.baranov.translator.R;
import com.baranov.translator.ui.InterfaceCommunicator;

/**
 * Created by Baranov on 22.06.2017 to Translator.
 */

public class PickLangFragment extends DialogFragment {

    public InterfaceCommunicator interfaceCommunicator;

    private static final String LANG_ITEMS = "LangItems";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            interfaceCommunicator = (InterfaceCommunicator) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement InterfaceCommunicator");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle mArgs = getArguments();
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.langs_title)
                .setItems(mArgs.getStringArray(LANG_ITEMS), (dialog, which) -> {
                    interfaceCommunicator.sendRequestCode(which);
                    dialog.dismiss();
                })
                .create();
    }

}
