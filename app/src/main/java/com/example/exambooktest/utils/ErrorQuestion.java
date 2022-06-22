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
 * 获得错题题库
 */
public class ErrorQuestion {

//    private List<QuestionBank> qb = new ArrayList<QuestionBank>();

    private static final String TAG = "ErrorQuestion";
    private final List<QuestionBank> qbAll = new ArrayList<>();
    private final List<QuestionBank> qbToday = new ArrayList<>();

    private final List<QuestionBank> qb1 = new ArrayList<>();
    private final List<QuestionBank> qb2 = new ArrayList<>();
    private final List<QuestionBank> qb3 = new ArrayList<>();
    private final List<QuestionBank> qb4 = new ArrayList<>();
    private final List<QuestionBank> qb5 = new ArrayList<>();
    private final List<QuestionBank> qb6 = new ArrayList<>();
    private final List<QuestionBank> qb7 = new ArrayList<>();

    private int size1;
    private int size2;
    private int size3;
    private int size4;
    private int size5;
    private int size6;
    private int size7;

    public  ErrorQuestion(String studentId, CallBack callBack) {

        new Thread(() -> {
            MySQL mySQL = new MySQL();
            //查询所有错题
            String sql = "select qb.* from (select * from student_question where question_right = 2 and student_id = " + studentId + ") sq left join question_bank qb on sq.question_id = qb.id";
            //查询今天错题
            String sqlToday = "select qb.* from (select * from student_question where question_right = 2 and year(date) = year(now()) and month(date) = month(now()) and day(date) = day(now()) and student_id = " + studentId + " ) sq left join question_bank qb on sq.question_id = qb.id";
            //获取1章节错题
            String sql1 = "select * from question_bank where id in (select question_id from student_question where  question_right = 2 and student_id = " + studentId + ") and question_chapter = 1";
            String sql2 = "select * from question_bank where id in (select question_id from student_question where  question_right = 2 and student_id = " + studentId + ") and question_chapter = 2";
            String sql3 = "select * from question_bank where id in (select question_id from student_question where  question_right = 2 and student_id = " + studentId + ") and question_chapter = 3";
            String sql4 = "select * from question_bank where id in (select question_id from student_question where  question_right = 2 and student_id = " + studentId + ") and question_chapter = 4";
            String sql5 = "select * from question_bank where id in (select question_id from student_question where  question_right = 2 and student_id = " + studentId + ") and question_chapter = 5";
            String sql6 = "select * from question_bank where id in (select question_id from student_question where  question_right = 2 and student_id = " + studentId + ") and question_chapter = 6";
            String sql7 = "select * from question_bank where id in (select question_id from student_question where  question_right = 2 and student_id = " + studentId + ") and question_chapter = 7";
            try {

                List<QuestionBank> questionBanks = mySQL.getQuestion(sql);
                qbAll.addAll(questionBanks);

                List<QuestionBank> questionBanksToday = mySQL.getQuestion(sqlToday);
                qbToday.addAll(questionBanksToday);

                List<QuestionBank> questionBanks1 = mySQL.getQuestion(sql1);
                qb1.addAll(questionBanks1);

                List<QuestionBank> questionBanks2 = mySQL.getQuestion(sql2);
                qb2.addAll(questionBanks2);

                List<QuestionBank> questionBanks3 = mySQL.getQuestion(sql3);
                qb3.addAll(questionBanks3);

                List<QuestionBank> questionBanks4 = mySQL.getQuestion(sql4);
                qb4.addAll(questionBanks4);

                List<QuestionBank> questionBanks5 = mySQL.getQuestion(sql5);
                qb5.addAll(questionBanks5);

                List<QuestionBank> questionBanks6 = mySQL.getQuestion(sql6);
                qb6.addAll(questionBanks6);

                List<QuestionBank> questionBanks7 = mySQL.getQuestion(sql7);
                qb7.addAll(questionBanks7);

                callBack.onResult(qbAll,qbToday);
                callBack.onResult(qb1,qb2,qb3,qb4,qb5,qb6,qb7);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
//            Log.d(TAG, "run: ======================" + qb.get(0).getTitle());
//                System.out.println(qBanks.get(1 - 1).getTitle());
        }).start();
    }


    public interface CallBack{

        void onResult(List<QuestionBank> questionBanksAll,List<QuestionBank> questionBanksToday);
        void onResult(List<QuestionBank> chapter1,List<QuestionBank> chapter2,List<QuestionBank> chapter3,List<QuestionBank> chapter4,List<QuestionBank> chapter5,List<QuestionBank> chapter6,List<QuestionBank> chapter7);

    }
}
