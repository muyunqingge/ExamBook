package com.example.exambooktest.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.exambooktest.R;
import com.example.exambooktest.mysql.QuestionBank;
import com.example.exambooktest.mysql.SQLite;
import com.example.exambooktest.utils.TestPaper;

import java.util.ArrayList;
import java.util.List;

/**
 * 尝试写滑动效果 第一次
 */

public class ExamViewPagerAdapter extends RecyclerView.Adapter<ExamViewPagerAdapter.ViewPagerViewHolder> {

    private List<QuestionBank> qb;

    private static final String TAG = "ViewPagerAdapter";

    private int isType;     //题目种类  1；单选 2：判断
    private int isAnswer;   //题目答案 1：A, 2:B, 3:C, 4:D, 5:√， 6：×
    private int count;      //记录总分数
//    private OnItemListenter mItemClickListener;


    public ExamViewPagerAdapter(){

       TestPaper paper = new TestPaper(1,4);
        //获取题库
        qb = paper.getPaper();

//        isType = qb.get(position).getQuestionType();

    }

    static class ViewPagerViewHolder extends RecyclerView.ViewHolder{
        View qView;
        //初始化布局
        LinearLayout mContainer;
        TextView tv_question_type;  //题型
        TextView tv_question_title; //题干
        //选项A
        LinearLayout ll_a;
        Button bt_a;
        TextView tv_a;
        //选项B
        LinearLayout ll_b;
        Button bt_b;
        TextView tv_b;
        //选项C
        LinearLayout ll_c;
        Button bt_c;
        TextView tv_c;
        //选项D
        LinearLayout ll_d;
        Button bt_d;
        TextView tv_d;

        public ViewPagerViewHolder(@NonNull View itemView) {
            super(itemView);
            //解析布局
            mContainer = itemView.findViewById(R.id.mContainer);
            tv_question_type = itemView.findViewById(R.id.tv_question_type);
            tv_question_title = itemView.findViewById(R.id.tv_question_title);
            ll_a = itemView.findViewById(R.id.ll_a);
            bt_a = itemView.findViewById(R.id.bt_a);
            tv_a = itemView.findViewById(R.id.tv_a);
            ll_b = itemView.findViewById(R.id.ll_b);
            bt_b = itemView.findViewById(R.id.bt_b);
            tv_b = itemView.findViewById(R.id.tv_b);
            ll_c = itemView.findViewById(R.id.ll_c);
            bt_c = itemView.findViewById(R.id.bt_c);
            tv_c = itemView.findViewById(R.id.tv_c);
            ll_d = itemView.findViewById(R.id.ll_d);
            bt_d = itemView.findViewById(R.id.bt_d);
            tv_d = itemView.findViewById(R.id.tv_d);
            qView = itemView;

        }
    }


    //内容适配 展示数据
    @Override
    public void onBindViewHolder(ViewPagerViewHolder holder, int position) {
        //记录是选择题还是判断题 1:单选题  2:判断题
        isType = qb.get(position).getQuestionType();
        //记录题目的答案
        isAnswer = qb.get(position).getQuestionAnswer();
        //记录题干
        String title = qb.get(position).getTitle();
        String a = qb.get(position).getOption_a();
        String b = qb.get(position).getOption_b();
        String c = qb.get(position).getOption_c();
        String d = qb.get(position).getOption_d();

        holder.tv_question_type.setText(isType == 1 ? "单选题" : "判断题");
        holder.tv_question_title.setText(title);

        //如果是单选题 那么展示ABCD 按钮
        if (isType == 1)
        {
//                holder.bt_c.setVisibility(View.VISIBLE);
//                holder.bt_d.setVisibility(View.VISIBLE);
            holder.tv_a.setVisibility(View.VISIBLE);
            holder.tv_b.setVisibility(View.VISIBLE);
//                holder.tv_c.setVisibility(View.VISIBLE);
//                holder.tv_d.setVisibility(View.VISIBLE);
            holder.ll_c.setVisibility(View.VISIBLE);
            holder.ll_d.setVisibility(View.VISIBLE);
            holder.bt_a.setText("A");
            holder.bt_b.setText("B");
            holder.bt_c.setText("C");
            holder.bt_d.setText("D");
            holder.tv_a.setText(a);
            holder.tv_b.setText(b);
            holder.tv_c.setText(c);
            holder.tv_d.setText(d);

            holder.bt_a.setBackgroundResource(R.drawable.shape_choice);
            holder.bt_b.setBackgroundResource(R.drawable.shape_choice);
            holder.bt_c.setBackgroundResource(R.drawable.shape_choice);
            holder.bt_d.setBackgroundResource(R.drawable.shape_choice);

            holder.ll_a.setEnabled(true);
            holder.ll_b.setEnabled(true);
            holder.ll_c.setEnabled(true);
            holder.ll_d.setEnabled(true);
            Log.d(TAG, "onBindViewHolder: 执行了单选题初始化" + title + holder.getAbsoluteAdapterPosition());
            //在这里会执行多次 放到onCreate中执行
//                holder.ll_a.setOnClickListener(new View.OnClickListener() {

        }
        //如果是判断题那么 展示对错 并且隐藏其他部件
        else if (isType == 2)
        {
            holder.bt_a.setText("√");
            holder.bt_b.setText("×");
            holder.tv_a.setVisibility(View.INVISIBLE);
            holder.tv_b.setVisibility(View.INVISIBLE);
            holder.ll_c.setVisibility(View.INVISIBLE);
            holder.ll_d.setVisibility(View.INVISIBLE);
            holder.bt_a.setBackgroundResource(R.drawable.shape_choice);
            holder.bt_b.setBackgroundResource(R.drawable.shape_choice);

            holder.ll_a.setEnabled(true);
            holder.ll_b.setEnabled(true);
            Log.d(TAG, "onBindViewHolder: 执行了判断题初始化" + title + holder.getAbsoluteAdapterPosition());
        }
    }


//    //接口的回调--------------------------------------------------------------------------------------
//    public interface OnItemListenter{
//        void onItemClick(View view, int position);
//    }
//
//    public void setOnItemClickListener(OnItemListenter mItemClickListener){
//        this.mItemClickListener = mItemClickListener;
//    }
//    //----------------------------------------------------------------------------------------------



    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public ViewPagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new ViewPagerViewHolder(LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.question_answer_fragment,parent,false));
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_answer_fragment,parent,false);
        final ViewPagerViewHolder holder  = new ViewPagerViewHolder(view);
//        //思路是 获取题对应的 题型 然后根据不同的题型做出不同的判断
//        //未解决问题  1.如何在答对情况下 直接跳转下一个item 2.在打错情况下 将错题添加至数据库中
//        //选项A / 判断√
        holder.ll_a.setOnClickListener(v -> {
            int position = holder.getAbsoluteAdapterPosition();
            QuestionBank questionBank = qb.get(position);
            holder.ll_a.setEnabled(false);
            holder.ll_b.setEnabled(false);
            holder.ll_c.setEnabled(false);
            holder.ll_d.setEnabled(false);
            //获取题型 是单选还是判断
            isType = questionBank.getQuestionType();
            //获得这道题的答案
            isAnswer = questionBank.getQuestionAnswer();
            //如果是单选题
            if (isType == 1)
            {
                //比较答案 A对应1 如果是1 说明选对了  否则选错了
                if (isAnswer == 1)
                {
                    Toast.makeText(v.getContext(), "单选题您对了" , Toast.LENGTH_SHORT).show();
                    holder.bt_a.setBackgroundResource(R.drawable.btn_blue);
                }
                else if (isAnswer == 2){
                    holder.bt_a.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_b.setBackgroundResource(R.drawable.btn_blue);
                    holder.bt_c.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_d.setBackgroundResource(R.drawable.btn_red);
                }
                else if (isAnswer == 3){
                    holder.bt_a.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_b.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_c.setBackgroundResource(R.drawable.btn_blue);
                    holder.bt_d.setBackgroundResource(R.drawable.btn_red);
                }
                else if (isAnswer == 4){
                    holder.bt_a.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_b.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_c.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_d.setBackgroundResource(R.drawable.btn_blue);
                }

            }
            //如果是判断题
            else if (isType == 2)
            {
                //比较答案 5对应 正确
                if (isAnswer == 5)
                {
                    Toast.makeText(v.getContext(), "判断题您对了" , Toast.LENGTH_SHORT).show();
                    holder.bt_a.setBackgroundResource(R.drawable.btn_blue);

                }
                else  if (isAnswer == 6){
                    Toast.makeText(v.getContext(), "判断题您错了" , Toast.LENGTH_SHORT).show();
                    holder.bt_a.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_b.setBackgroundResource(R.drawable.btn_blue);
                }


//                    Toast.makeText(v.getContext(), "你点击了A" + questionBank.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

//-----------------------尝试利用接口回调的方式------------------------------------
//        if (mItemClickListener != null)
//        {
//            int position = holder.getAbsoluteAdapterPosition();
//
//            holder.ll_a.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mItemClickListener.onItemClick(v,position);
//                }
//            });
//        }
//--------------------------------------------------------------------------------


        //选项B / 判断×
        holder.ll_b.setOnClickListener(v -> {
            int position = holder.getAbsoluteAdapterPosition();
            QuestionBank questionBank = qb.get(position);
            holder.ll_a.setEnabled(false);
            holder.ll_b.setEnabled(false);
            holder.ll_c.setEnabled(false);
            holder.ll_d.setEnabled(false);
            //获取题型 是单选还是判断
            isType = questionBank.getQuestionType();
            //获得这道题的答案
            isAnswer = questionBank.getQuestionAnswer();
            //如果是单选题
            if (isType == 1)
            {
                //比较答案 B对应2 如果是2 说明选对了  否则选错了
                if (isAnswer == 1)
                {
                    holder.bt_a.setBackgroundResource(R.drawable.btn_blue);
                    holder.bt_b.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_c.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_d.setBackgroundResource(R.drawable.btn_red);
                }
                else if (isAnswer == 2){
                    holder.bt_b.setBackgroundResource(R.drawable.btn_blue);
                    Toast.makeText(v.getContext(), "单选题您对了" , Toast.LENGTH_SHORT).show();
                }
                else if (isAnswer == 3){
                    holder.bt_a.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_b.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_c.setBackgroundResource(R.drawable.btn_blue);
                    holder.bt_d.setBackgroundResource(R.drawable.btn_red);
                }
                else if (isAnswer == 4){
                    holder.bt_a.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_b.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_c.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_d.setBackgroundResource(R.drawable.btn_blue);
                }
            }
            //如果是判断题
            else if (isType == 2)
            {
                //比较答案 6对应 错误
                if (isAnswer == 6)
                {
                    holder.bt_b.setBackgroundResource(R.drawable.btn_blue);
                    Toast.makeText(v.getContext(), "判断题您对了" , Toast.LENGTH_SHORT).show();

                }
                else if (isAnswer == 5){
                    Toast.makeText(v.getContext(), "判断题您错了" , Toast.LENGTH_SHORT).show();
                    holder.bt_a.setBackgroundResource(R.drawable.btn_blue);
                    holder.bt_b.setBackgroundResource(R.drawable.btn_red);
                }

//                    Toast.makeText(v.getContext(), "你点击了A" + questionBank.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        //单选C
        holder.ll_c.setOnClickListener(v -> {
            int position = holder.getAbsoluteAdapterPosition();
            QuestionBank questionBank = qb.get(position);
            holder.ll_a.setEnabled(false);
            holder.ll_b.setEnabled(false);
            holder.ll_c.setEnabled(false);
            holder.ll_d.setEnabled(false);
            //获取题型 是单选还是判断
            isType = questionBank.getQuestionType();
            //获得这道题的答案
            isAnswer = questionBank.getQuestionAnswer();
            //如果是单选题
            if (isType == 1)
            {
                //比较答案 C对应3 如果是3 说明选对了  否则选错了
                if (isAnswer == 1)
                {
                    holder.bt_a.setBackgroundResource(R.drawable.btn_blue);
                    holder.bt_b.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_c.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_d.setBackgroundResource(R.drawable.btn_red);
                }
                else if (isAnswer == 2){
                    holder.bt_a.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_b.setBackgroundResource(R.drawable.btn_blue);
                    holder.bt_c.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_d.setBackgroundResource(R.drawable.btn_red);
                }
                else if (isAnswer == 3){

                    holder.bt_c.setBackgroundResource(R.drawable.btn_blue);

                    Toast.makeText(v.getContext(), "单选题您对了" , Toast.LENGTH_SHORT).show();
                }
                else if (isAnswer == 4){
                    holder.bt_a.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_b.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_c.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_d.setBackgroundResource(R.drawable.btn_blue);
                }
            }

        });

        //单选d
        holder.ll_d.setOnClickListener(v -> {
            int position = holder.getAbsoluteAdapterPosition();
            QuestionBank questionBank = qb.get(position);
            holder.ll_a.setEnabled(false);
            holder.ll_b.setEnabled(false);
            holder.ll_c.setEnabled(false);
            holder.ll_d.setEnabled(false);
            //获取题型 是单选还是判断
            isType = questionBank.getQuestionType();

            //获得这道题的答案
            isAnswer = questionBank.getQuestionAnswer();

            //如果是单选题
            if (isType == 1)
            {
                //比较答案 D对应4 如果是4 说明选对了  否则选错了
                if (isAnswer == 1)
                {
                    holder.bt_a.setBackgroundResource(R.drawable.btn_blue);
                    holder.bt_b.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_c.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_d.setBackgroundResource(R.drawable.btn_red);
                }
                else if (isAnswer == 2){
                    holder.bt_a.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_b.setBackgroundResource(R.drawable.btn_blue);
                    holder.bt_c.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_d.setBackgroundResource(R.drawable.btn_red);
                }
                else if (isAnswer == 3){

                    holder.bt_a.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_b.setBackgroundResource(R.drawable.btn_red);
                    holder.bt_c.setBackgroundResource(R.drawable.btn_blue);
                    holder.bt_d.setBackgroundResource(R.drawable.btn_red);

                }
                else if (isAnswer == 4){

                    holder.bt_d.setBackgroundResource(R.drawable.btn_blue);
                    Toast.makeText(v.getContext(), "单选题您对了" , Toast.LENGTH_SHORT).show();
                }
            }

        });
        return holder;
    }

    @Override
    public int getItemCount() {
        return qb.size();
    }

}
