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
 * 获得顺序练习题
 */
public class SequentialQuestion {

    //    private List<QuestionBank> qb = new ArrayList<QuestionBank>();
    private static final String TAG = "NotDoneQuestion";
    private final List<QuestionBank> qb = new ArrayList<>();
    public  SequentialQuestion(CallBack callBack) {

        new Thread(() -> {
            MySQL mySQL = new MySQL();
            String sql = "select * from question_bank  ";
            try {

                List<QuestionBank> questionBanks = mySQL.getQuestion(sql);
                Log.d(TAG, "NotDoneQuestion: "+ questionBanks.toString());
                qb.addAll(questionBanks);
                callBack.onResult(qb);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
//            Log.d(TAG, "run: ======================" + qb.get(0).getTitle());
//                System.out.println(qBanks.get(1 - 1).getTitle());
        }).start();
    }


    public interface CallBack{

        void onResult(List<QuestionBank> questionBanks);

    }

    public List<QuestionBank> getQb() {
        return qb;
    }

    public void clear() {
        qb.clear();
    }

}