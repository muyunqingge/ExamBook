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

import com.example.exambooktest.mysql.MySQL;
import com.example.exambooktest.mysql.QuestionBank;
import com.example.exambooktest.utils.ErrorQuestion;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class ErrorBookActivity extends AppCompatActivity {

    private static final String TAG = "ErrorBookActivity";

    private SharedPreferences mShare;
    private String studentId;

    private TextView tv_error_number;       //错题数
    private TextView tv_points_first;
    private TextView tv_points_second;
    private TextView tv_points_third;
    private TextView tv_points_fourth;
    private TextView tv_points_fifth;
    private TextView tv_points_six;
    private TextView tv_points_seven;
    private Button btn_error_today;
    private Button btn_error_all;

    private ArrayList<QuestionBank> questionBanks = new ArrayList<>();
    private ArrayList<QuestionBank> questionBanksToday = new ArrayList<>();


    private int chapterSize1;
    private int chapterSize2;
    private int chapterSize3;
    private int chapterSize4;
    private int chapterSize5;
    private int chapterSize6;
    private int chapterSize7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loding_normal_layout);

        mShare = getSharedPreferences("user", Context.MODE_PRIVATE);

        studentId = mShare.getString("studentId","");


        initdata();

    }

    private void init() {
        btn_error_today = findViewById(R.id.btn_error_today);
        btn_error_all = findViewById(R.id.btn_error_all);
        tv_error_number = findViewById(R.id.tv_error_number1);
        tv_points_first = findViewById(R.id.tv_points_first);
        tv_points_second = findViewById(R.id.tv_points_second);
        tv_points_third = findViewById(R.id.tv_points_third);
        tv_points_fourth = findViewById(R.id.tv_points_fourth);
        tv_points_fifth = findViewById(R.id.tv_points_fifth);
        tv_points_six = findViewById(R.id.tv_points_six);
        tv_points_seven = findViewById(R.id.tv_points_seven);

    }



    private void initdata() {
        ErrorQuestion errorQuestion = new ErrorQuestion(studentId, new ErrorQuestion.CallBack() {
            @Override
            public void onResult(List<QuestionBank> questionBanksAll,List<QuestionBank> questionToday) {
//                Log.d(TAG, "ViewPagerAdapter: "  + qb.get(0).getTitle() );
                questionBanks.clear();
                questionBanks.addAll(questionBanksAll);

                questionBanksToday.clear();
                questionBanksToday.addAll(questionToday);

                Log.d(TAG, "handleMessage: ======================1" + questionBanks.size());

                mHandler.sendEmptyMessage(99);
            }

            @Override
            public void onResult(List<QuestionBank> chapter1, List<QuestionBank> chapter2, List<QuestionBank> chapter3, List<QuestionBank> chapter4, List<QuestionBank> chapter5, List<QuestionBank> chapter6, List<QuestionBank> chapter7) {
                chapterSize1 = chapter1.size();
                chapterSize2 = chapter2.size();
                chapterSize3 = chapter3.size();
                chapterSize4 = chapter4.size();
                chapterSize5 = chapter5.size();
                chapterSize6 = chapter6.size();
                chapterSize7 = chapter7.size();
                mHandler.sendEmptyMessage(99);
            }




        });

//        ErrorQuestion errorChapterQuestion = new ErrorQuestion(studentId, 1, new ErrorQuestion.CallBack() {
//            @Override
//            public void onResult(List<QuestionBank> questionBanksAll,List<QuestionBank> questionToday) {
//
//            }
//
//            @Override
//            public void onResult(List<QuestionBank> chapter,int size) {
//                chapterSize1 = size;
//
//                mHandler.sendEmptyMessage(99);
//            }
//        });


    }

    private final Handler.Callback callback = new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == 99) {

                setContentView(R.layout.activity_error_book);

                init();

                //错题数
                int size = questionBanks.size();
                Log.d(TAG, "handleMessage: ======================" + questionBanks.size());
                tv_error_number.setText(MessageFormat.format("{0}", size));

                //今日错题数
                int size2 = questionBanksToday.size();
                btn_error_today.setText(MessageFormat.format("今日错题（{0})", size2));

                tv_points_first.setText(MessageFormat.format("{0}题", chapterSize1));
                tv_points_second.setText(MessageFormat.format("{0}题", chapterSize2));
                tv_points_third.setText(MessageFormat.format("{0}题", chapterSize3));
                tv_points_fourth.setText(MessageFormat.format("{0}题", chapterSize4));
                tv_points_fifth.setText(MessageFormat.format("{0}题", chapterSize5));
                tv_points_six.setText(MessageFormat.format("{0}题", chapterSize6));
                tv_points_seven.setText(MessageFormat.format("{0}题", chapterSize7));

                btn_error_today.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (questionBanksToday.size() != 0)
                        {
                            Intent intent = new Intent(ErrorBookActivity.this, QuestionErrorToday.class);
                            startActivity(intent);
                        }
                        else Toast.makeText(ErrorBookActivity.this,"您今日没有错题",Toast.LENGTH_SHORT).show();
                    }
                });

                btn_error_all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (questionBanks.size() != 0)
                        {
                            Intent intent = new Intent(ErrorBookActivity.this, QuestionActivity.class);
                            startActivity(intent);
                        }
                        else Toast.makeText(ErrorBookActivity.this,"您没有错题",Toast.LENGTH_SHORT).show();
                    }
                });

            }
            return false;
        }
    };

    private final Handler mHandler = new Handler(Looper.getMainLooper(), callback);

//    private Handler mHandler = new Handler(Looper.myLooper()){
//
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == 99)
//            {
//
//            }
//        }
//    };
}