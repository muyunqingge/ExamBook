package com.example.exambooktest.utils;

import android.util.Log;

import com.example.exambooktest.mysql.MySQL;
import com.example.exambooktest.mysql.QuestionBank;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *  获得收藏题题数
 */

public class FavoriteQuestionCount {

    private final List<Sort> qbCount = new ArrayList<>();
    private final List<Sort> qbCountToday = new ArrayList<>();
    private final List<Sort> qbCount1 = new ArrayList<>();
    private final List<Sort> qbCount2 = new ArrayList<>();
    private final List<Sort> qbCount3 = new ArrayList<>();
    private final List<Sort> qbCount4 = new ArrayList<>();
    private final List<Sort> qbCount5 = new ArrayList<>();
    private final List<Sort> qbCount6 = new ArrayList<>();
    private final List<Sort> qbCount7 = new ArrayList<>();
    private static final String TAG = "FavoriteQuestion";

    public FavoriteQuestionCount(String studentId, FavoriteQuestionCount.CallBack callBack){

        new Thread(() -> {
            MySQL mySQL = new MySQL();
            //获得全部收藏题数量
            String sql = "select count(id) from question_favorite where student_id = " + studentId;
            //获得今日收藏题数量
            String sqlToday = "select count(id) from question_favorite where year(date) = year(now()) and month(date) = month(now()) and day(date) = day(now()) and student_id = " + studentId;
            //获得收藏题第1章节数量
            String sql1 = "select count(id) from question_favorite where student_id = " + studentId + " and question_id in (select id from question_bank where question_chapter = 1)";
            //获得收藏题第2章节数量
            String sql2 = "select count(id) from question_favorite where student_id = " + studentId + " and question_id in (select id from question_bank where question_chapter = 2)";
            //获得收藏题第3章节数量
            String sql3 = "select count(id) from question_favorite where student_id = " + studentId + " and question_id in (select id from question_bank where question_chapter = 3)";
            //获得收藏题第4章节数量
            String sql4 = "select count(id) from question_favorite where student_id = " + studentId + " and question_id in (select id from question_bank where question_chapter = 4)";
            //获得收藏题第5章节数量
            String sql5 = "select count(id) from question_favorite where student_id = " + studentId + " and question_id in (select id from question_bank where question_chapter = 5)";
            //获得收藏题第6章节数量
            String sql6 = "select count(id) from question_favorite where student_id = " + studentId + " and question_id in (select id from question_bank where question_chapter = 6)";
            //获得收藏题第7章节数量
            String sql7 = "select count(id) from question_favorite where student_id = " + studentId + " and question_id in (select id from question_bank where question_chapter = 7)";


            try {
                //获得总数
                List<Sort> countAll = mySQL.getQuestionCount(sql);
//                qbCount.addAll(countAll);
                //获得今日数量
                List<Sort> countToday = mySQL.getQuestionCount(sqlToday);
                //获得章节数量
                List<Sort> count1 = mySQL.getQuestionCount(sql1);
                List<Sort> count2 = mySQL.getQuestionCount(sql2);
                List<Sort> count3 = mySQL.getQuestionCount(sql3);
                List<Sort> count4 = mySQL.getQuestionCount(sql4);
                List<Sort> count5 = mySQL.getQuestionCount(sql5);
                List<Sort> count6 = mySQL.getQuestionCount(sql6);
                List<Sort> count7 = mySQL.getQuestionCount(sql7);


                callBack.onResult(countAll.get(0).getSort(),countToday.get(0).getSort(),count1.get(0).getSort(),
                        count2.get(0).getSort(),count3.get(0).getSort(),count4.get(0).getSort(),count5.get(0).getSort(),
                        count6.get(0).getSort(),count7.get(0).getSort());

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }).start();
    }

    public interface CallBack{

        void onResult(int sizeAll,int sizeToday, int chapter1,int chapter2,int chapter3,int chapter4,int chapter5,int chapter6,int chapter7);

    }
    public List<Sort> getQb() {
        return qbCount;
    }
    public void clear() {
        qbCount.clear();
    }
}
