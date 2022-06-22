package com.example.exambooktest.mysql;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

import com.example.exambooktest.database.QuestionDBHelper;
import com.example.exambooktest.database.SubjectDBHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SQLite  {

    private static final String TAG = "SQLite";
    private Context context;



    @SuppressLint("Range")
    public List<QuestionBank> getQuestion()
    {
        List list = new ArrayList<QuestionBank>();

        SQLiteOpenHelper helper = QuestionDBHelper.getInstance(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        if (db.isOpen())
        {
            Cursor cursor = db.rawQuery("select * from questions", null);

            //迭代游标 往下面移动来遍历数据
            while (cursor.moveToNext())
            {
                QuestionBank qb = new QuestionBank();
                qb.setId(cursor.getInt(cursor.getColumnIndex("_id")));

                Log.e(TAG, "getQuestion: +++++++++++++++++++++++++++" + qb.getId());

                qb.setQuestionChapter(cursor.getInt(cursor.getColumnIndex("chapter")));
                qb.setQuestionType(cursor.getInt(cursor.getColumnIndex("type")));
                qb.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                qb.setOption_a(cursor.getString(cursor.getColumnIndex("option_a")));
                qb.setOption_b(cursor.getString(cursor.getColumnIndex("option_b")));
                qb.setOption_c(cursor.getString(cursor.getColumnIndex("option_c")));
                qb.setOption_d(cursor.getString(cursor.getColumnIndex("option_d")));
                qb.setQuestionAnswer(cursor.getInt(cursor.getColumnIndex("question_answer")));
                qb.setQuestionAnalysis(cursor.getString(cursor.getColumnIndex("question_analysis")));
                qb.setSubjectId(cursor.getInt(cursor.getColumnIndex("subject_id")));

                list.add(qb);
//               int type = cursor.getInt(2);
//               String title = cursor.getString(3);
//               String option_a = cursor.getString(4);
//               String option_b = cursor.getString(5);
//               String option_c = cursor.getString(6);
//               String option_d = cursor.getString(7);
//               int question_answer = cursor.getInt(8);
//               String question_analysis = cursor.getString(9);
//               int subject_id = cursor.getInt(10);
            }

            cursor.close();
            db.close();
        }
        return list;
    }


    //传入章节，科目 获得符合的题
    @SuppressLint("Range")
    public List<QuestionBank> getQuestion(int chapter, int subject)
    {
        List list = new ArrayList<QuestionBank>();

        SQLiteOpenHelper helper = QuestionDBHelper.getInstance(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        if (db.isOpen())
        {
            Cursor cursor = db.rawQuery("select * from questions where chapter = " + chapter + " and subject_id = " + subject + ";", null);

            //迭代游标 往下面移动来遍历数据
            while (cursor.moveToNext())
            {
                QuestionBank qb = new QuestionBank();
                qb.setId(cursor.getInt(cursor.getColumnIndex("_id")));

                Log.e(TAG, "getQuestion2: +++++++++++++++++++++++++++" + qb.getId());

                qb.setQuestionChapter(cursor.getInt(cursor.getColumnIndex("chapter")));
                qb.setQuestionType(cursor.getInt(cursor.getColumnIndex("type")));
                qb.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                qb.setOption_a(cursor.getString(cursor.getColumnIndex("option_a")));
                qb.setOption_b(cursor.getString(cursor.getColumnIndex("option_b")));
                qb.setOption_c(cursor.getString(cursor.getColumnIndex("option_c")));
                qb.setOption_d(cursor.getString(cursor.getColumnIndex("option_d")));
                qb.setQuestionAnswer(cursor.getInt(cursor.getColumnIndex("question_answer")));
                qb.setQuestionAnalysis(cursor.getString(cursor.getColumnIndex("question_analysis")));
                qb.setSubjectId(cursor.getInt(cursor.getColumnIndex("subject_id")));

                list.add(qb);
//               int type = cursor.getInt(2);
//               String title = cursor.getString(3);
//               String option_a = cursor.getString(4);
//               String option_b = cursor.getString(5);
//               String option_c = cursor.getString(6);
//               String option_d = cursor.getString(7);
//               int question_answer = cursor.getInt(8);
//               String question_analysis = cursor.getString(9);
//               int subject_id = cursor.getInt(10);
            }
            cursor.close();
            db.close();
        }
        return list;
    }



    @SuppressLint("Range")
    public List<Subject> getSubject()
    {
        List list = new ArrayList<Subject>();

        SQLiteOpenHelper helper = SubjectDBHelper.getInstance(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        if (db.isOpen())
        {
            Cursor cursor = db.rawQuery("select * from subject", null);
            //迭代游标 往下面移动来遍历数据
            while (cursor.moveToNext())
            {
                Subject subjects = new Subject();
                subjects.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                subjects.setSubject_name(cursor.getString(cursor.getColumnIndex("subject_name")));
                list.add(subjects);
            }
            cursor.close();
            db.close();
        }
        return list;
    }








}
