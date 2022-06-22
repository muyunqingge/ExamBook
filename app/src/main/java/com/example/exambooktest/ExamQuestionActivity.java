package com.example.exambooktest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exambooktest.adapter.BottomSheetAdapter;
import com.example.exambooktest.adapter.ExamViewPagerAdapter;
import com.example.exambooktest.adapter.ViewPagerAdapter;
import com.example.exambooktest.mysql.MySQL;
import com.example.exambooktest.mysql.QuestionBank;
import com.example.exambooktest.mysql.SQLite;
import com.example.exambooktest.utils.MockQuestion;
import com.example.exambooktest.utils.Sort;
import com.example.exambooktest.utils.TestPaper;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamQuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Sort> data =  new ArrayList<>();       //下拉列表中题的序号
    private List<QuestionBank> qb = new ArrayList<>();  // 题库
    private BottomSheetBehavior mBottomSheetBehavior;   //下拉列表
    private View view;
    private ImageView iv_shoucang;
    private Button btn_analysis;
    private Button btn_answer;
    private Button btn_submit;
    private LinearLayout ll_btn;//下拉列表
    private SharedPreferences mShare;
    private String studentId;
    private ViewPager2 viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private final List<QuestionBank> questionBanks = new ArrayList<>();
    private static final String TAG = "QuestionActivity";
    private TextView tv_count;
    private TextView tv_point;
    private TextView tv_right;
    private TextView tv_error;
    private TextView tv_time;
    private int right = 0;
    private int error = 0;
    public double score = 0;

    private final int time = 30*60*1000;

    private TimeCount timeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loding_normal_layout);

        mShare = getSharedPreferences("user", Context.MODE_PRIVATE);

        studentId = mShare.getString("studentId","");

        initdata();

    }

    //20道选择 10道判断
    private void initdata() {
        MockQuestion question = new MockQuestion(studentId,new MockQuestion.CallBack() {
            @Override
            public void onResult(List<QuestionBank> list) {
                Log.d(TAG, "ViewPagerAdapter: "  + questionBanks.size() );
//                Log.d(TAG, "ViewPagerAdapter: "  + qb.get(0).getTitle() );
                questionBanks.clear();
                questionBanks.addAll(list);
                Log.d(TAG, "ViewPagerAdapter: "  + questionBanks.size() );
                handler.sendEmptyMessage(99);
            }
        });
    }

    //初始化viewpager
    private void initPager() {

        //可以获得
        Log.d(TAG, "initPager: ==============================studentId================" + studentId);


        viewPager = findViewById(R.id.viewPager);
        //回调
        viewPagerAdapter = new ViewPagerAdapter(ExamQuestionActivity.this, new ArrayList<>(), new ViewPagerAdapter.CallBack() {
            @Override
            public void onResult(int questionId, int right) {
                updateQuestion(studentId,questionId,right );
            }

            @Override
            public void onResult(int questionId) {

                iv_shoucang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        insertF(studentId,questionId);
                    }
                });
            }

            @Override
            public void onResults(int rightSize, int errorSize, int isType) {

                right = right + rightSize;
                error = error + errorSize;


                //如果正确，并且题型是选择  分数  + 3.5分
                if (rightSize == 1 && isType == 1)
                {
                    score = score + 3.5;
                }
                //如果正确，并且题型是判断  分数  + 3分
                if (rightSize == 1 && isType == 2)
                {
                    score = score + 3.0;
                }


                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ExamQuestionActivity.this);
                        builder.setTitle("是否提交")
                                .setMessage("确定提交后将不能更改")
                                .setPositiveButton("再看看", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setNegativeButton("确认提交", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                MySQL mySQL = new MySQL();
                                                try {
//                                                    mySQL.insertMockScore(studentId,score);

                                                    //修改考试信息
                                                    mySQL.updateExam(studentId,score,1);
                                                } catch (SQLException throwables) {
                                                    throwables.printStackTrace();
                                                }

                                                Intent intent = new Intent(ExamQuestionActivity.this, ExamSchoolReportActivity.class);
                                                startActivity(intent);
                                                finish();

                                            }
                                        }).start();
                                        //由于线程没有执行完 直接执行会导致空指针异常 所以放到线程中执行
//                                        Intent intent = new Intent(MockQuestionActivity.this, MockSchoolReportActivity.class);
//                                        startActivity(intent);
//                                        finish();
                                    }
                                })
                                .create()
                                .show();
                    }
                });


                tv_right.setText(right + "");
                tv_error.setText(error + "");
            }
        });

        //初始化页面个数为题数   注意这里会导致内存占用量飙升   只是暂时满足要求
        viewPager.setOffscreenPageLimit(30);
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                tv_point.setText(position + 1 + "");
                //当滑到最后一题时 显示提交按钮 否则隐藏
                if (position == 29)
                {
                    btn_submit.setVisibility(View.VISIBLE);
                }
                else btn_submit.setVisibility(View.INVISIBLE);


            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    private final Handler.Callback callback = new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == 99) {

                setContentView(R.layout.activity_mock_question);

                //初始化
                init();

                initPager();
                //初始化下拉菜单
                initBottomSheetBehavior();

                //初始化及时
                timeCount = new TimeCount(time,1000);
                timeCount.start();

                //初始按钮的recycler
                initRecycler();
                //将题库数据回调道adapter
                viewPagerAdapter.setData(questionBanks);

                int count = questionBanks.size();
                tv_count.setText(count + "");


            }
            return false;
        }
    };

    private final Handler handler = new Handler(Looper.getMainLooper(), callback);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(callback);
    }

    private void updateQuestion(String studentId, int questionId, int right){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    MySQL mySQL = new MySQL();
                    mySQL.updateStudentQuestion(studentId,questionId, right);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }




    //初始化下拉抽屉中的题号与点击事件
    private void initRecycler() {
//
//        sqQuestion = new SQLite();
//        qb = sqQuestion.getQuestion();


        //初始化题目数量 ,根据所拿题库的数量去初始化
        Log.d(TAG, "initRecycler: questionBanks: "+questionBanks.size());
        for (int i = 1; i <= questionBanks.size(); i++)
        {
            Sort sort = new Sort();
            sort.setBtn("" + i);
            data.add(sort);
        }

        RecyclerView recyclerView = findViewById(R.id.rv_sorts);
        //网格布局
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6);
        recyclerView.setLayoutManager(gridLayoutManager);

        //尝试！！成功！
//        BottomSheetAdapter bottomSheetAdapter = new BottomSheetAdapter(data,this);
        BottomSheetAdapter bottomSheetAdapter = new BottomSheetAdapter(data, this);
        recyclerView.setAdapter(bottomSheetAdapter);

        //自定义的点击事件 点击题号滚到对应的view
        bottomSheetAdapter.setRecyclerItemClick(new BottomSheetAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onRecyclerItemClick(int position) {

                Log.e(TAG, "onRecyclerItemClick: " + (position + 1));
                //点击题号 滚动到对应的viewpager
                viewPager.setCurrentItem(position);

                if (position == 29)
                {
                    btn_submit.setVisibility(View.VISIBLE);
                }
                else btn_submit.setVisibility(View.INVISIBLE);

            }
        });
    }



    //初始化
    private void init() {
        btn_answer = findViewById(R.id.btn_answer);
        btn_submit = findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(this);

        iv_shoucang = findViewById(R.id.iv_shoucang);
        iv_shoucang.setOnClickListener(this);

        ll_btn = findViewById(R.id.ll_btn);
        ll_btn.setOnClickListener(this);

        tv_count = findViewById(R.id.tv_count);
        tv_point = findViewById(R.id.tv_point);
        tv_right = findViewById(R.id.tv_right);
        tv_error = findViewById(R.id.tv_error);

        tv_time = findViewById(R.id.tv_time);


    }


    //初始化下拉抽屉
    public void initBottomSheetBehavior()
    {
        final int peekLowHightPx = getResources().getDimensionPixelOffset(R.dimen.peek_lheight);

        view = findViewById(R.id.design_bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(view);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mBottomSheetBehavior.setPeekHeight(peekLowHightPx);

        mBottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {


                int peekHighHightPx = getResources().getDimensionPixelOffset(R.dimen.peek_hheight);
                if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED)
                {

                    mBottomSheetBehavior.setPeekHeight(peekLowHightPx);
                }
                if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
                {
                    mBottomSheetBehavior.setPeekHeight(peekHighHightPx);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }


    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {


//            case R.id.iv_shoucang:
//
//
//
//                break;

            case R.id.ll_btn:
                if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED)
                {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
                {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                break;


//            case R.id.btn_submit:
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(MockQuestionActivity.this);
//                builder.setTitle("是否提交")
//                        .setMessage("确定提交后将不能更改")
//                        .setPositiveButton("再看看", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        })
//                        .setNegativeButton("确认提交", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        })
//                        .create()
//                        .show();
//                break;

            default:
                break;
        }
    }

    private void replaceFragment (Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.layout, fragment);
        transaction.commit();
    }

    //把题加入到数据库 收藏表中
    public void insertF(String studentId, int questionId)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MySQL mySQL = new MySQL();
                try {
                    mySQL.insertFavorite(studentId,questionId);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }).start();
    }

    class TimeCount extends CountDownTimer{
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            int time = (int)millisUntilFinished;

            tv_time.setText(secondsToTime(time / 1000));
        }

        @Override
        public void onFinish() {
            AlertDialog.Builder builder = new AlertDialog.Builder(ExamQuestionActivity.this);
            builder.setTitle("答题时间结束")
                    .setMessage("确定提交后将不能更改")
                    .setNegativeButton("确认提交", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    MySQL mySQL = new MySQL();
                                    try {
                                        mySQL.insertMockScore(studentId,score);
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    }

                                    Intent intent = new Intent(ExamQuestionActivity.this, MockSchoolReportActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                            }).start();
                            //由于线程没有执行完 直接执行会导致空指针异常 所以放到线程中执行
//                                        Intent intent = new Intent(MockQuestionActivity.this, MockSchoolReportActivity.class);
//                                        startActivity(intent);
//                                        finish();
                        }
                    })
                    .create()
                    .show();
        }
    }

    private static String secondsToTime(int seconds){
        int h=seconds/3600;			//小时
        int m=(seconds%3600)/60;		//分钟
        int s=(seconds%3600)%60;		//秒
        if(h>0){
            return h+"小时 "+m+"分钟 "+s+"秒";
        }
        if(m>0){
            return m+"分钟  "+s+"秒";
        }
        return s+"秒";
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){

            Toast.makeText(this,"考试过程中不允许返回",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}

