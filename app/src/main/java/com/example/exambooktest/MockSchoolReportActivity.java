package com.example.exambooktest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.exambooktest.mysql.MySQL;
import com.example.exambooktest.mysql.QuestionBank;
import com.example.exambooktest.mysql.Score;
import com.example.exambooktest.utils.Question;
import com.example.exambooktest.utils.Scores;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

//MPAndroidChart
//比如AnyChart-Android


public class MockSchoolReportActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MockSchoolReportActivit";

    private SharedPreferences mShare;
    private String studentId;
    private String name;
    private String school;

    private ArrayList<Score> scoresList = new ArrayList<>();
    private ArrayList<Score> maxScore = new ArrayList<>();
    private ArrayList<Score> passScores = new ArrayList<>();
    private ArrayList<Score> thisScores = new ArrayList<>();

    private TextView tv_id;
    private TextView tv_school_id;
    private TextView tv_score_max_history;
    private TextView tv_score_this;
    private TextView tv_he_ge_ci_shu;
    private TextView tv_lei_ji_ci_shu;

    private ImageView iv_touixang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loding_normal_layout);

        mShare = getSharedPreferences("user", MODE_PRIVATE);
        studentId = mShare.getString("studentId","");
        name = mShare.getString("name","");
        school = mShare.getString("school","");

        Log.d(TAG, "onCreate: 0000000000000000000000000000000000000000000000");
        initData();


    }

    private void init() {
        ImageView iv_fanhui = findViewById(R.id.iv_fanhui);
        iv_fanhui.setOnClickListener(this);

        tv_id = findViewById(R.id.tv_id);
        tv_school_id = findViewById(R.id.tv_school_id);
        tv_score_max_history = findViewById(R.id.tv_score_max_history);
        tv_score_this = findViewById(R.id.tv_score_this);
        tv_he_ge_ci_shu = findViewById(R.id.tv_he_ge_ci_shu);
        tv_lei_ji_ci_shu = findViewById(R.id.tv_lei_ji_ci_shu);



        iv_touixang = findViewById(R.id.iv_touixang);
        //设置头像
        SharedPreferences imageShare = getSharedPreferences("imagePath",MODE_PRIVATE);
        SharedPreferences user_share = getSharedPreferences("user",MODE_PRIVATE);
        String imagePath = imageShare.getString("imagePath" + user_share.getString("studentId",""),"");
        if (!imagePath.equals("")){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            iv_touixang.setImageBitmap(bitmap);
            iv_touixang.setBackground(null);
        }
    }

    private void initData() {

        Scores scores = new Scores(studentId, new Scores.CallBack() {
            @Override
            public void onResult(List<Score> scores, List<Score> scoreMax, List<Score> scorePass, List<Score> scoreThis) {

                //分数总列表 包含分数 日期
                scoresList.clear();
                scoresList.addAll(scores);

                //最大分数
                maxScore.clear();
                maxScore.addAll(scoreMax);

                //满足合格的列表
                passScores.clear();
                passScores.addAll(scorePass);

                //最新一次的分数
                thisScores.clear();
                thisScores.addAll(scoreThis);

                mHandler.sendEmptyMessage(98);
            }

        });
    }

    private final Handler.Callback callback = new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            if (msg.what == 98 ) {

                setContentView(R.layout.activity_school_report);

                init();

                tv_id.setText(name);
                tv_school_id.setText(school);

                //当新账号第一次登录时，由于数据库中没有数据，所获得的数据为空
                //会导空指针或数组越界异常  加上非空的判定 若空 就都显示0
                if (scoresList != null){
                    //累计次数
                    int all = scoresList.size();
                    tv_lei_ji_ci_shu.setText(MessageFormat.format("{0}", all));
                }else{
                    tv_lei_ji_ci_shu.setText("0");
                }

                if (passScores != null){
                    //及格次数
                    int pass = passScores.size();
                    tv_he_ge_ci_shu.setText(MessageFormat.format("{0}", pass));
                }else {
                    tv_he_ge_ci_shu.setText("0");
                }

                if (maxScore != null)
                {
                    //历史最高分
                    double height = maxScore.get(0).getScore();
                    tv_score_max_history.setText(MessageFormat.format("{0}", height));
                }else{
                    tv_score_max_history.setText("0");
                }
                //这里得用这个方法 不可以用 != null
                if (!thisScores.isEmpty())
                {
                    double thisScore = thisScores.get(0).getScore();
                    tv_score_this.setText(MessageFormat.format("{0} 分", thisScore));
                }else {
                    tv_score_this.setText("0");
                }
            }
            return false;
        }
    };

    private final Handler mHandler = new Handler(Looper.getMainLooper(), callback);

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId())
        {
            case R.id.iv_fanhui:
//                intent = new Intent(MockSchoolReportActivity.this, MockTestActivity.class);
//                startActivity(intent);
                MockSchoolReportActivity.this.finish();
                break;
            default:
                break;
        }
    }
}