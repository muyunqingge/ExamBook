package com.example.exambooktest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Button btn_play_first = findViewById(R.id.btn_play_first);
        btn_play_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.bilibili.com/video/BV1WW411Q7PF?spm_id_from=333.337.search-card.all.click");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        Button btn_play_second = findViewById(R.id.btn_play_second);
        btn_play_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.bilibili.com/video/BV1BE411D7ii?spm_id_from=333.337.search-card.all.click");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        Button btn_play_third = findViewById(R.id.btn_play_third);
        btn_play_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.bilibili.com/video/BV1kU4y177x9?spm_id_from=333.337.search-card.all.click");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
    }
}