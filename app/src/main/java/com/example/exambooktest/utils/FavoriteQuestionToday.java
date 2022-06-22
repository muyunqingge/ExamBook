package com.example.exambooktest.utils;

import android.util.Log;

import com.example.exambooktest.mysql.MySQL;
import com.example.exambooktest.mysql.QuestionBank;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *  获得既今日收藏题
 */

public class FavoriteQuestionToday {

    private final List<QuestionBank> qb = new ArrayList<>();
    private static final String TAG = "FavoriteQuestion";

    public FavoriteQuestionToday(String studentId, FavoriteQuestionToday.CallBack callBack){

        new Thread(() -> {
            MySQL mySQL = new MySQL();
            String sql = "select qb.* from (select * from question_favorite where year(date) = year(now()) and month(date) = month(now()) and day(date) = day(now()) and student_id =  " + studentId + ") qf left join question_bank qb on qf.question_id = qb.id";
            try {

                List<QuestionBank> questionBanks = mySQL.getQuestion(sql);
                Log.d(TAG, "ErrorQuestion: "+ questionBanks.toString());
                qb.addAll(questionBanks);
                callBack.onResult(qb);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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
