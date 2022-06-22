package com.example.exambooktest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ChapterActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_fanhui;
    private Button btn_zhang_jie;
    private Button btn_kao_dian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        iv_fanhui = findViewById(R.id.iv_fanhui);
        iv_fanhui.setOnClickListener(this);

        btn_zhang_jie = findViewById(R.id.btn_zhang_jie);
        btn_zhang_jie.setOnClickListener(this);

        btn_kao_dian = findViewById(R.id.btn_kao_dian);
        btn_kao_dian.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId())
        {
            case R.id.iv_fanhui:
                intent = new Intent(ChapterActivity.this, SequentialAnswerActivity.class);
                startActivity(intent);
                ChapterActivity.this.finish();
                break;
            case R.id.btn_zhang_jie:
                intent = new Intent(ChapterActivity.this, ChapterPracticeActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_kao_dian:
                intent = new Intent(ChapterActivity.this, TestPracticeActivity.class);
                startActivity(intent);
                break;
        }
    }
}