package com.example.exambooktest.utils;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.exambooktest.mysql.MySQL;
import com.example.exambooktest.mysql.QuestionBank;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 获得题库  包含获得错题， 获得未做题
 */
public class Question {


    private static final String TAG = "ErrorQuestion";
    private final List<QuestionBank> errorBanks = new ArrayList<>();
    private final List<QuestionBank> ndBanks = new ArrayList<>();
    private final List<QuestionBank> allBanks = new ArrayList<>();
    public  Question(String studentId, CallBack callBack) {

        new Thread(() -> {
            MySQL mySQL = new MySQL();
            String sql1 = "select qb.* from (select * from student_question where question_right = 2 and student_id = " + studentId + ") sq left join question_bank qb on sq.question_id = qb.id";
            String sql2 = "select qb.* from (select * from student_question where question_right = 0 and student_id = " + studentId + ") sq left join question_bank qb on sq.question_id = qb.id";
            String sql3 = "select * from question_bank";
            try {
                //错题
                List<QuestionBank> error = mySQL.getQuestion(sql1);
                errorBanks.addAll(error);
                //未做题
                List<QuestionBank> not = mySQL.getQuestion(sql2);
                ndBanks.addAll(not);
                //总题
                List<QuestionBank> all = mySQL.getQuestion(sql3);
                allBanks.addAll(all);

                callBack.onResult(errorBanks, ndBanks,allBanks);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }).start();
    }



    public interface CallBack{

        void onResult(List<QuestionBank> errorQuestionBanks,List<QuestionBank> ndBanks,List<QuestionBank> allBanks);

    }



}