package com.example.exambooktest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.exambooktest.adapter.BottomSheetAdapter;
import com.example.exambooktest.adapter.ViewPagerAdapter;
import com.example.exambooktest.mysql.MySQL;
import com.example.exambooktest.mysql.QuestionBank;
import com.example.exambooktest.mysql.SQLite;
import com.example.exambooktest.utils.ErrorQuestion;
import com.example.exambooktest.utils.FavoriteQuestion;
import com.example.exambooktest.utils.Sort;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 收藏题答题
 */

public class QuestionFavoritesActivity extends AppCompatActivity implements View.OnClickListener{

    private List<Sort> data =  new ArrayList<>();       //下拉列表中题的序号
    private List<QuestionBank> qb = new ArrayList<>();  // 题库
    private BottomSheetBehavior mBottomSheetBehavior;   //下拉列表
    private BottomSheetAdapter bottomSheetAdapter;
    private View view;                                  //下拉列表view
    private ImageView iv_delete;
    private ImageView fanhui;
    private Button btn_analysis;
    private Button btn_answer;
    private LinearLayout ll_btn;                        //下拉列表head
    private SharedPreferences mShare;                   //获取登录的用户信息
    private String studentId;                           //用户id
    private ViewPager2 viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private final List<QuestionBank> questionBanks = new ArrayList<>(); //题库
    private static final String TAG = "QuestionFavoritesActivity";

    private TextView tv_count;
    private TextView tv_point;
    private TextView tv_right;
    private TextView tv_error;

    private int rightCount = 0;
    private int errorCount = 0;


//    public int getErrorState(int errorState) {
//        return errorState;
//    }
//
//    public int getRightState(int rightState) {
//        return rightState;
//    }

    private int errorState;
    private int rightState;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);

        mShare = getSharedPreferences("user", Context.MODE_PRIVATE);

        studentId = mShare.getString("studentId","");

        //错题数据初始化
        initdata();

    }

    private void initdata() {
        //获得收藏题的数据
        FavoriteQuestion favoriteQuestion = new FavoriteQuestion(studentId, new FavoriteQuestion.CallBack() {
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
        viewPagerAdapter = new ViewPagerAdapter(QuestionFavoritesActivity.this, new ArrayList<>(), new ViewPagerAdapter.CallBack() {

            //将错题信息更新到 s q 表中
            @Override
            public void onResult(int questionId, int right) {
                updateQuestion(studentId,questionId,right);
            }

            //删除收藏题
            @Override
            public void onResult(int questionId) {
                iv_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteF(studentId,questionId);
                    }
                });
            }

            //显示 对错题数
            @Override
            public void onResults(int rightSize, int errorSize, int isType) {

               rightState = rightSize;
               errorState = errorSize;
               
                Log.d(TAG, "onResult: ----------rightState" + rightState);
                Log.d(TAG, "onResult: ----------errorState" + errorState);



                //显示 正确 错误数量
                rightCount = rightCount + rightSize;
                errorCount = errorCount + errorSize;

                tv_right.setText(rightCount + "");
                tv_error.setText(errorCount + "");
            }
        });


        //初始化页面个数为题数   注意这里会导致内存占用量飙升   只是暂时满足要求
        viewPager.setOffscreenPageLimit(questionBanks.size());

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

            }

            //
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

                setContentView(R.layout.activity_question_favorites);
                //初始化控件
                init();
                //初始化viewPager
                initPager();
                //初始化下拉菜单
                initBottomSheetBehavior();
                //初始按钮的recycler
                initRecycler();

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

    //修改题目对错信息
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
        bottomSheetAdapter = new BottomSheetAdapter(data, this);


        recyclerView.setAdapter(bottomSheetAdapter);
//        bottomSheetAdapter.setRecyclerItemBackground(new BottomSheetAdapter.OnRecyclerItemBackground() {
//            @Override
//            public void onRecyclerItemBackground(int drawable) {
//
//            }
//        });
//        //数据不会更新
//        int right = rightState;
//        int error = errorState;
//        Log.d(TAG, "onResult: ----------rightState" + right);
//        Log.d(TAG, "onResult: ----------errorState" + error);

        //自定义的点击事件 点击题号滚到对应的view
        bottomSheetAdapter.setRecyclerItemClick(new BottomSheetAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onRecyclerItemClick(int position) {

                Log.e(TAG, "onRecyclerItemClick: " + (position + 1));
                //点击题号 滚动到对应的viewpager
                viewPager.setCurrentItem(position);

            }
        });
    }

    //初始化
    private void init() {
        btn_answer = findViewById(R.id.btn_answer);
        btn_answer.setOnClickListener(this);

        btn_analysis = findViewById(R.id.btn_analysis);
        btn_analysis.setOnClickListener(this);

        fanhui = findViewById(R.id.fanhui);
        fanhui.setOnClickListener(this);


        ll_btn = findViewById(R.id.ll_btn);
        ll_btn.setOnClickListener(this);

        iv_delete = findViewById(R.id.iv_delete);
        tv_count = findViewById(R.id.tv_count);
        tv_point = findViewById(R.id.tv_point);
        tv_right = findViewById(R.id.tv_right);
        tv_error = findViewById(R.id.tv_error);

    }


    //初始化下拉抽屉
    public void initBottomSheetBehavior()
    {
        final int peekLowHightPx = getResources().getDimensionPixelOffset(R.dimen.peek_lheight);

        view = findViewById(R.id.design_bottom_sheet_shoucang);
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
            case R.id.btn_answer:

                btn_answer.setTextColor(getResources().getColor(R.color.colorWhite));
                btn_answer.setBackground(getResources().getDrawable(R.drawable.shape_btn_fouce));
                btn_analysis.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_analysis.setBackground(getResources().getDrawable(R.drawable.shape_btn_unfocused));
                if(viewPagerAdapter != null){
                    viewPagerAdapter.showAnswer(false);
                }
                break;

            case R.id.btn_analysis:

                btn_analysis.setTextColor(getResources().getColor(R.color.colorWhite));
                btn_analysis.setBackground(getResources().getDrawable(R.drawable.shape_btn_fouce));
                btn_answer.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_answer.setBackground(getResources().getDrawable(R.drawable.shape_btn_unfocused));
                if(viewPagerAdapter != null){
                viewPagerAdapter.showAnswer(true);
                }
                break;

            case R.id.fanhui:

                this.finish();

                break;


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

//            case R.id.ll_a:
//                Toast.makeText(QuestionActivity.this,"点击了按钮A",Toast.LENGTH_SHORT).show();
//                Log.d(TAG, "onClick: 点击了A选项===================");
//                break;

            default:
                break;
        }
    }

    //把题从收藏表中删除
    public void deleteF(String studentId, int questionId)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MySQL mySQL = new MySQL();
                try {
                    mySQL.deleteFavorite(studentId,questionId);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }).start();
    }
}