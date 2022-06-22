package com.example.exambooktest.utils;

import com.example.exambooktest.mysql.MySQL;
import com.example.exambooktest.mysql.QuestionBank;
import com.example.exambooktest.mysql.Score;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 获得模拟考试分数的所有数据
 */
public class Scores {

    private final List<Score> scores = new ArrayList<>();
    private final List<Score> scoreMax = new ArrayList<>();
    private final List<Score> scorePass = new ArrayList<>();
    private final List<Score> scoreThis = new ArrayList<>();
    public  Scores(String studentId, Scores.CallBack callBack) {
        new Thread(() -> {
            MySQL mySQL = new MySQL();
            //获得所有数据
            String sql = "select score, date from mock_score where student_id = " + studentId;
            //获得最高分
            String sql2 = "select max(score) from mock_score where student_id = " + studentId;
            //获得及格数据
            String sql3 = "select score, date from mock_score where score >= 60 and student_id = " + studentId ;
            //获得当前数据
            String sql4 = "select score from mock_score where student_id = " + studentId + " order by id desc";
            try {

                List<Score> scoresNormal = mySQL.getMockScore(sql);
                scores.addAll(scoresNormal);


                List<Score> maxScore = mySQL.getMaxMockScore(sql2);
                scoreMax.addAll(maxScore);

                List<Score> passScore = mySQL.getMockScore(sql3);
                scorePass.addAll(passScore);

                List<Score> thisScore = mySQL.getThisMockScore(sql4);
                scoreThis.addAll(thisScore);


                callBack.onResult(scores,scoreMax,scorePass,scoreThis);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }).start();
    }

    public interface CallBack{

        void onResult(List<Score> scores,List<Score> scoreMax,List<Score> scorePass,List<Score> scoreThis);

    }
}
