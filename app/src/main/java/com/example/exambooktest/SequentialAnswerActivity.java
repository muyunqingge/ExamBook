package com.example.exambooktest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import com.example.exambooktest.mysql.QuestionBank;
import com.example.exambooktest.utils.ErrorQuestion;
import com.example.exambooktest.utils.NotDoneQuestion;
import com.example.exambooktest.utils.Question;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class SequentialAnswerActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_sync;
    private TextView tv_tong_guo_lv;
    private TextView tv_id;
    private TextView tv_no_action;
    private TextView tv_error_number;
    private TextView tv_error_pct;
    private ImageView iv_fanhui;
    private ImageView iv_touixang;
    private Button btn_chapter;
    private Button btn_continue;



    private ArrayList<QuestionBank> errorBanks = new ArrayList<>();
    private ArrayList<QuestionBank> notBanks = new ArrayList<>();
    private ArrayList<QuestionBank> allBanks = new ArrayList<>();
    private SharedPreferences mShare;
    private String studentId;
    private String user;
    private static final String TAG = "SequentialAnswerActivity";


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
        tv_sync = findViewById(R.id.tv_sync);
        tv_sync.setOnClickListener(this);
        btn_chapter = findViewById(R.id.btn_chapter);
        btn_chapter.setOnClickListener(this);

        iv_fanhui = findViewById(R.id.iv_fanhui);
        iv_fanhui.setOnClickListener(this);

        tv_tong_guo_lv = findViewById(R.id.tv_tong_guo_lv);

        btn_continue = findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(this);

        tv_id = findViewById(R.id.tv_id);
        tv_no_action = findViewById(R.id.tv_no_action);
        tv_error_number = findViewById(R.id.tv_error_number);
        tv_error_pct = findViewById(R.id.tv_error_pct);



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



    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId())
        {
            case R.id.tv_sync:
               intent = new Intent(SequentialAnswerActivity.this, SyncActivity.class);
               startActivity(intent);

               break;
            case R.id.btn_chapter:
                intent = new Intent(SequentialAnswerActivity.this, ChapterActivity.class);
                startActivity(intent);

                break;

            case R.id.iv_fanhui:
                intent = new Intent(SequentialAnswerActivity.this, MainActivity.class);
                startActivity(intent);
                SequentialAnswerActivity.this.finish();
                break;

            case R.id.btn_continue:
                intent = new Intent(SequentialAnswerActivity.this, SequentialQuestionActivity.class);
                startActivity(intent);
                SequentialAnswerActivity.this.finish();

                break;


            default:
                break;
        }
    }

    private void initData() {
//        ErrorQuestion errorQuestion = new ErrorQuestion(studentId, new ErrorQuestion.CallBack() {
//            @Override
//            public void onResult(List<QuestionBank> list) {
//                errorBanks.clear();
//                errorBanks.addAll(list);
//                Log.d(TAG, "handleMessage: ===========1===========" + errorBanks.size());
//                mHandler.sendEmptyMessage(99);
//            }
//        });
//
//        NotDoneQuestion notDoneQuestion = new NotDoneQuestion(studentId, new NotDoneQuestion.CallBack() {
//            @Override
//            public void onResult(List<QuestionBank> list) {
////                Log.d(TAG, "ViewPagerAdapter: "  + qb.get(0).getTitle() );
//                notBanks.clear();
//                notBanks.addAll(list);
//                Log.d(TAG, "handleMessage: ============2==========" + notBanks.size());
//                mHandler.sendEmptyMessage(98);
//            }
//        });

        Question notDoneQuestion = new Question(studentId, new Question.CallBack() {
            @Override
            public void onResult(List<QuestionBank> errorQuestionBanks,List<QuestionBank> ndBanks,List<QuestionBank> allqBanks) {
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
        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            if (msg.what == 98 ) {

                setContentView(R.layout.activity_sequential_answer);

                init();
                tv_id.setText(user);

                //做错的
                int size1 = errorBanks.size();
                tv_error_number.setText(MessageFormat.format("{0}", size1));
                //没做的
                int size2 = notBanks.size();
                tv_no_action.setText(MessageFormat.format("{0}", size2));
                //总的
                int size3 = allBanks.size();

                double errorPct = (int)(((float)size1 / (size3 - size2)) * 100);

                Log.d(TAG, "handleMessage: 做错的：" + size1 + "没做的：" + size2 + "总的" + size3);

                //通过率
                double adoptPct = (((float)((size3 - size2) - size1) / size3) * 100);

                tv_error_pct.setText(MessageFormat.format("{0} %", errorPct));      //错题率
                tv_tong_guo_lv.setText(String.format("%.2f",adoptPct) + "%");    //通过率

            }


            return false;
        }
    };

    private final Handler mHandler = new Handler(Looper.getMainLooper(), callback);
}