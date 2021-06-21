package com.morlag.exam.common;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MessageDialog extends DialogFragment {
    String m;
    String t;
    Context mContext;
    public MessageDialog(String mes, Context c, String title){
        m = mes;
        mContext = c;
        t = "Ошибка";
        if (title!=null) t = title;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(mContext)
                .setTitle(t)
                .setMessage(m)
                .setPositiveButton("Ок",null)
                .create();
    }
}
