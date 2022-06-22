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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exambooktest.mysql.MySQL;
import com.example.exambooktest.mysql.QuestionBank;
import com.example.exambooktest.utils.ExamHave;
import com.example.exambooktest.utils.Question;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class ExamTestActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ExamTestActivity";
    private SharedPreferences mShare;
    private String studentId;
    private String user;

    private TextView tv_id;         //姓名
    private TextView tv_tong_guo_lv;//通过率
    private Button btn_start_exam;  //模拟考试

    private ImageView iv_fanhui;
    //    private TextView tv_cheng_ji_dan;
    //用于计算考试通过率
    private ArrayList<QuestionBank> errorBanks = new ArrayList<>();
    private ArrayList<QuestionBank> notBanks = new ArrayList<>();
    private ArrayList<QuestionBank> allBanks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loding_normal_layout);

        mShare = getSharedPreferences("user", MODE_PRIVATE);
        studentId = mShare.getString("studentId","");
        user = mShare.getString("name","");

        initData();



    }

    private void init() {
        iv_fanhui = findViewById(R.id.iv_fanhui);
        iv_fanhui.setOnClickListener(this);

//        tv_cheng_ji_dan = findViewById(R.id.tv_cheng_ji_dan);
//        tv_cheng_ji_dan.setOnClickListener(this);

        tv_id = findViewById(R.id.tv_id);
        tv_id.setOnClickListener(this);

        tv_tong_guo_lv = findViewById(R.id.tv_tong_guo_lv);
        tv_tong_guo_lv.setOnClickListener(this);

        btn_start_exam = findViewById(R.id.btn_start_exam);
        btn_start_exam.setOnClickListener(this);

        ImageView iv_touixang = findViewById(R.id.iv_touixang);
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

    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId())
        {
            case R.id.iv_fanhui:
                intent = new Intent(ExamTestActivity.this, MainActivity.class);
                startActivity(intent);
                ExamTestActivity.this.finish();
                break;

            case R.id.tv_cheng_ji_dan:
                intent = new Intent(ExamTestActivity.this, MockSchoolReportActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_start_exam:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<ExamHave> list = new ArrayList<>();
                        MySQL mySQL = new MySQL();
                        try {
                            list = mySQL.getExam(studentId);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                        if (!list.isEmpty())
                        {
                            Intent intent = new Intent(ExamTestActivity.this, ExamQuestionActivity.class);
                            startActivity(intent);
                            ExamTestActivity.this.finish();
                        }else runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ExamTestActivity.this,"您没有考试",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }).start();
                break;
            default:
                break;

        }
    }

    private void initData() {
        Question notDoneQuestion = new Question(studentId, new Question.CallBack() {
            @Override
            public void onResult(List<QuestionBank> errorQuestionBanks, List<QuestionBank> ndBanks, List<QuestionBank> allqBanks) {
//                Log.d(TAG, "ViewPagerAdapter: "  + qb.get(0).getTitle() );

                errorBanks.clear();
                errorBanks.addAll(errorQuestionBanks);

                notBanks.clear();
                notBanks.addAll(ndBanks);

                allBanks.clear();
                allBanks.addAll(allqBanks);

                Log.d(TAG, "handleMessage: ============2==========" + notBanks.size());
                mHandler.sendEmptyMessage(98);
            }
        });
    }

    private final Handler.Callback callback = new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == 98 ) {
                setContentView(R.layout.activity_exam_test);
                init();
                tv_id.setText(user);
                //做错的
                int size1 = errorBanks.size();
                //没做的
                int size2 = notBanks.size();
                //总的
                int size3 = allBanks.size();
                Log.d(TAG, "handleMessage: 做过：" + size1 + "没做的：" + size2 + "总的" + size3);
                double adoptPct = (int)(((float)((size3 - size2) - size1) / size3) * 100);
                tv_tong_guo_lv.setText(MessageFormat.format("{0} %", adoptPct));    //通过率

            }
            return false;
        }
    };
    private final Handler mHandler = new Handler(Looper.getMainLooper(), callback);

}