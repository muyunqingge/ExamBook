package com.example.exambooktest.utils;

import android.util.Log;

import com.example.exambooktest.mysql.MySQL;
import com.example.exambooktest.mysql.QuestionBank;
import com.example.exambooktest.mysql.SQLite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 获得30道题的考试卷子，可以传入科目 章节
 */

public class TestPaper {

    private final int N = 30;       //一份试卷的总题数
    public int chapter = 1;            //题目的章节
    public int subject = 4;            //题目的科目

    private List<QuestionBank> qb = new ArrayList<>(); //构建一张题库线性表
    private List<QuestionBank> paper = new ArrayList<>(); //试卷
    private static final String TAG = "TestPaper";

    public TestPaper(int chapter, int subject){
//        chapter = 1;
//        subject = 4;
        //放到全局变量会导致空指针异常 原因未知
//        SQLite sqLite = new SQLite();
//
//        qb = sqLite.getQuestion(chapter,subject);
        //洗牌算法 获得的qb题库 洗牌 打乱
        Collections.shuffle(qb);
        Log.d(TAG, "TestPaper: +++++++++++++++++++++++" + qb.size());

        //考试卷子出30道题
        for (int i = 0; i < N; i++)
        {
            if (qb != null)
            {
                paper.add(qb.get(i));
            }
        }

        getPaper();
    }

    public List<QuestionBank> getPaper()
    {

        Log.d(TAG, "getPaper:++++++++++ " + paper.size() + paper.get(29).getTitle());

        return paper;
    }
}
