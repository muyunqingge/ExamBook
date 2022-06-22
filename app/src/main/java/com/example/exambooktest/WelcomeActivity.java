package com.example.exambooktest;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exambooktest.mysql.MySQL;
import com.example.exambooktest.mysql.User;
import com.example.exambooktest.utils.TestPaper;

import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        System.out.println("欢迎界面==================================================================");





        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        };

        timer.schedule(timerTask, 3000 * 1);
    }

}