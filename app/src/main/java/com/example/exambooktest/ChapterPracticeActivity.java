package com.example.exambooktest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ChapterPracticeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_fanhui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_practice);


        iv_fanhui = findViewById(R.id.iv_fanhui);
        iv_fanhui.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId())
        {
            case R.id.iv_fanhui:
                intent = new Intent(ChapterPracticeActivity.this, ChapterActivity.class);
                startActivity(intent);
                ChapterPracticeActivity.this.finish();
                break;
        }
    }
}