package com.example.exambooktest.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.exambooktest.R;

import com.example.exambooktest.mysql.QuestionBank;
import com.example.exambooktest.mysql.SQLite;
import com.example.exambooktest.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 *
 * 答题碎片
 */
public class QuestionAnswer extends Fragment {

    private Button bt_a;
    private Button bt_b;
    private Button bt_c;
    private Button bt_d;

    private TextView tv_a;
    private TextView tv_b;
    private TextView tv_c;
    private TextView tv_d;

    private LinearLayout ll_a;
    private LinearLayout ll_b;
    private LinearLayout ll_c;
    private LinearLayout ll_d;

    private QuestionAnswerViewModel mViewModel;
    private TextView tv_question_type;
    private TextView tv_question_title;

    private SharedPreferences un_right_share;

    private SQLite sql_questions;


    public static QuestionAnswer newInstance() {
        return new QuestionAnswer();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

//        iv_question_subject.findViewById(R.id.iv_question_subject);
//        iv_question_subject.setMaxHeight(100);
//        iv_question_subject.setMaxWidth(100);


//        //初始化 SQLite
//        sql_questions = new SQLite();
//        //获得题库
//        List<QuestionBank> qb = sql_questions.getQuestion();

        //!!!!!!!!
//        View view = inflater.inflate(R.layout.question_answer_fragment, container, false);
        View view = inflater.inflate(R.layout.layout_null, container, false);

//        un_right_share = view.getContext().getSharedPreferences("unRight", Context.MODE_PRIVATE);
        //实例化题目种类 和题干
//        tv_question_type = view.findViewById(R.id.tv_question_type);
//        tv_question_title = view.findViewById(R.id.tv_question_title);

//        tv_question_type.setText(qb.get(1).getQuestionType() == 1 ? "单选题" : "判断题");
//        tv_question_title.setText(qb.get(1).getTitle());

//        tv_question_type.setText(un_right_share.getInt("type",0));
//        tv_question_title.setText(un_right_share.getString("title",""));
        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(QuestionAnswerViewModel.class);
//        ll_a = getActivity().findViewById(R.id.ll_a);

        // TODO: Use the ViewModel
    }

}