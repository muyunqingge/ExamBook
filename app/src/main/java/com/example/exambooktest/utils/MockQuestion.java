package com.example.exambooktest.utils;

import android.util.Log;

import com.example.exambooktest.mysql.MySQL;
import com.example.exambooktest.mysql.QuestionBank;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 获得模拟考试题
 */
public class MockQuestion {

//    private List<QuestionBank> qb = new ArrayList<QuestionBank>();

    private static final String TAG = "ErrorQuestion";
    private final List<QuestionBank> qb = new ArrayList<>();
    public MockQuestion(String studentId, CallBack callBack) {

        new Thread(() -> {
            MySQL mySQL = new MySQL();

            //获得单选题
            String sql1 = "select * from question_bank where question_type = 1";
            //获得判断题
            String sql2 = "select * from question_bank where question_type = 2";

//            String sql1 = "select qb.* from (select * from student_question where student_id = " + studentId + ") sq left join question_bank qb on sq.question_id = qb.id and qb.question_type = 1";
//            String sql2 = "select qb.* from (select * from student_question where student_id = " + studentId + ") sq left join question_bank qb on sq.question_id = qb.id and qb.question_type = 2";
            try {

                List<QuestionBank> questionBanks = mySQL.getQuestion(sql1);
                List<QuestionBank> questionBanks1 = mySQL.getQuestion(sql2);
                Log.d(TAG, "ErrorQuestion: "+ questionBanks.toString());

                //洗牌算法
                Collections.shuffle(questionBanks);
                Collections.shuffle(questionBanks1);

                for (int i = 0; i < 10; i++)
                {

                        qb.add(i, questionBanks1.get(i));

                }

                for (int i = 0; i < 20; i++)
                {

                    qb.add(i, questionBanks.get(i));

                }


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
