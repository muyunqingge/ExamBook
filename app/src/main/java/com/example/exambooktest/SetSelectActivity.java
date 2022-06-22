package com.example.exambooktest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SetSelectActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_fanhui;
    private LinearLayout ll_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_select);

        iv_fanhui = findViewById(R.id.iv_fanhui);
        iv_fanhui.setOnClickListener(this);

        ll_me = findViewById(R.id.ll_me);
        ll_me.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_fanhui:

                SetSelectActivity.this.finish();

                break;
            case R.id.ll_me:
                Intent intent;
                intent = new Intent(SetSelectActivity.this,MyStuffActivity.class);
                startActivity(intent);

                break;
        }
    }
}