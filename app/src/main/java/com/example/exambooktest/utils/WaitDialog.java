package com.example.exambooktest.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.exambooktest.R;

public class WaitDialog extends Dialog {

    private TextView waitText;

    public WaitDialog(@NonNull Context context) {
        super(context);
        setCanceledOnTouchOutside(false);
        getWindow().setGravity(Gravity.CENTER);
        setContentView(R.layout.wait_dialog);
//        waitText = findViewById(R.id.tv_wait_dialog_text);
    }

//    public void setText(CharSequence waitMsg)
//    {
//        waitText.setText(waitMsg);
//    }
//
//    public void setText(int resId)
//    {
//        waitText.setText(resId);
//    }


    public WaitDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected WaitDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
