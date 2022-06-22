package com.example.exambooktest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exambooktest.mysql.QuestionBank;
import com.example.exambooktest.utils.ErrorQuestion;
import com.example.exambooktest.utils.FavoriteQuestion;
import com.example.exambooktest.utils.FavoriteQuestionCount;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {
    private static final String TAG = "FavoritesActivity";

    private Button btn_favorites_today;
    private Button btn_favorites_all;
    private TextView tv_favorite_number;
    private TextView tv_points_first;
    private TextView tv_points_second;
    private TextView tv_points_third;
    private TextView tv_points_fourth;
    private TextView tv_points_fifth;
    private TextView tv_points_six;
    private TextView tv_points_seven;

    private SharedPreferences mShare;
    private String studentId;
    private ArrayList<QuestionBank> questionBanks = new ArrayList<>();

    private int allCount;
    private int todayCount;
    private int count1;
    private int count2;
    private int count3;
    private int count4;
    private int count5;
    private int count6;
    private int count7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loding_normal_layout);

        mShare = getSharedPreferences("user", Context.MODE_PRIVATE);

        studentId = mShare.getString("studentId","");

        initdata();

    }

    private void init() {
        btn_favorites_today = findViewById(R.id.btn_favorites_today);
        btn_favorites_all = findViewById(R.id.btn_favorites_all);

        tv_points_first = findViewById(R.id.tv_points_first);
        tv_points_second = findViewById(R.id.tv_points_second);
        tv_points_third = findViewById(R.id.tv_points_third);
        tv_points_fourth = findViewById(R.id.tv_points_fourth);
        tv_points_fifth = findViewById(R.id.tv_points_fifth);
        tv_points_six = findViewById(R.id.tv_points_six);
        tv_points_seven = findViewById(R.id.tv_points_seven);
        tv_favorite_number = findViewById(R.id.tv_favorite_number);
    }



    //准备收藏题数据
    private void initdata() {
        FavoriteQuestionCount errorQuestion = new FavoriteQuestionCount(studentId, new FavoriteQuestionCount.CallBack() {
            @Override
            public void onResult(int sizeAll, int sizeToday, int chapter1, int chapter2, int chapter3, int chapter4, int chapter5, int chapter6, int chapter7) {

                allCount = sizeAll;
                todayCount = sizeToday;
                count1 = chapter1;
                count2 = chapter2;
                count3 = chapter3;
                count4 = chapter4;
                count5 = chapter5;
                count6 = chapter6;
                count7 = chapter7;

                mHandler.sendEmptyMessage(99);

            }

        });
    }

    private final Handler.Callback callback = new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == 99) {

                setContentView(R.layout.activity_favorites);

                init();

                tv_favorite_number.setText(MessageFormat.format("{0}", allCount));
                btn_favorites_today.setText(MessageFormat.format("今日收藏 ( {0})", todayCount));
                tv_points_first.setText(MessageFormat.format("{0} 题", count1));
                tv_points_second.setText(MessageFormat.format("{0} 题", count2));
                tv_points_third.setText(MessageFormat.format("{0} 题", count3));
                tv_points_fourth.setText(MessageFormat.format("{0} 题", count4));
                tv_points_fifth.setText(MessageFormat.format("{0} 题", count5));
                tv_points_six.setText(MessageFormat.format("{0} 题", count6));
                tv_points_seven.setText(MessageFormat.format("{0} 题", count7));

                btn_favorites_today.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (todayCount != 0) {
                            Intent intent = new Intent(FavoritesActivity.this, QuestionFavoritesTodayActivity.class);
                            startActivity(intent);
                        }else Toast.makeText(FavoritesActivity.this,"您今日没有收藏题目",Toast.LENGTH_SHORT).show();
                    }
                });

                btn_favorites_all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (allCount != 0){
                            Intent intent = new Intent(FavoritesActivity.this, QuestionFavoritesActivity.class);
                            startActivity(intent);
                        }else Toast.makeText(FavoritesActivity.this,"您还没有进行题目收藏",Toast.LENGTH_SHORT).show();
                    }
                });


            }
            return false;
        }
    };

    private final Handler mHandler = new Handler(Looper.getMainLooper(), callback);
}