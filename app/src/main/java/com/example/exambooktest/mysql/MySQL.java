package com.example.exambooktest.mysql;

import android.util.Log;

import com.example.exambooktest.utils.ExamHave;
import com.example.exambooktest.utils.Sort;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

    /**
     *   负责数据库的 curd
     */

public class MySQL extends RDSConnection{
    private String TAG = "MySQL";
    private String sql = null;

    //传入sql语句，获取用户信息
    //mysql线程池 德鲁伊
    public List<User> getUser(String sql) throws SQLException {
        //建立线性表 利用User实现类。
        List list = new ArrayList<User>();
        // 获取链接
        getConnection();
        //第三步：创建语句对象
        preparedStatement = connection.prepareStatement(sql);
//        System.out.println("zzzz");

        //第四步：执行sql语句，返回结果集
        resultset = preparedStatement.executeQuery();
        //第五步：处理结果
        //resultset.next()方法
        //可以将指针移动到下一条记录上
        //从结果集中取数据，就是取工作指针所指的那条数据
        //getString(1):取当前记录的第1个字段的数据值
        //getString("字段名"):取当前记录的指定字段的值。
        while(resultset.next()) {
            Log.d(TAG, "getUser: ========================================================================================");
            User user = new User();
            user.setStudentId(resultset.getString("student_id"));
            user.setName(resultset.getString("name"));
            user.setPassword(resultset.getString("password"));
            user.setPhone(resultset.getString("phone"));
            user.setGender(resultset.getString("gender"));
            user.setSchool(resultset.getString("school"));
            user.setCollege(resultset.getString("college"));
            user.setMajor(resultset.getString("major"));
            user.setGrade(resultset.getString("grade"));
            user.setNetName(resultset.getString("net_name"));
            user.setMockScore(resultset.getFloat("mock_score"));
            user.setFormalScore(resultset.getFloat("formal_score"));
            list.add(user);

        }
//        Log.d(TAG, "list<User>: " + list.toString());
        //关闭连接
        closeConnection();
        //返回集合
        return list;
    }


    //账号注册
    //增
    public int insertUser(String school, String college, String major, String grade, String student_id,String name, String password, String phone) throws SQLException
    {
        int i;
        //获取数据库连接
        getConnection();

        Log.d(TAG, "insertUser: ===============================================================================");
        //第三步：创建语句对象
        sql = "insert into student (school, college, major, grade, student_id, name, password, phone) values (?,?,?,?,?,?,?,?);";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,school);
        preparedStatement.setString(2,college);
        preparedStatement.setString(3,major);
        preparedStatement.setString(4,grade);
        preparedStatement.setString(5,student_id);
        preparedStatement.setString(6,name);
        preparedStatement.setString(7,password);
        preparedStatement.setString(8,phone);

        //第四步：执行sql语句，返回结果集
        i = preparedStatement.executeUpdate();

        if (i > 0)
        {
            System.out.println("插入成功！" + i);
        }
        closeConnection();
        return i;
    }

    //账号注册 同步增加student_question 表
    public int insertQuestionStudent(String studentId) throws SQLException
    {

        int i;
        //获取数据库连接
        getConnection();

        Log.d(TAG, "insertUser: ===============================================================================");
        //第三步：创建语句对象
        sql = "insert into student_question(student_id,question_id)  select ?, id from question_bank";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,studentId);

       //第四步：执行sql语句，返回结果集
        i = preparedStatement.executeUpdate();
        if (i > 0)
        {
            System.out.println("插入成功！" + i);
        }
        closeConnection();
        return i;
    }


    //修改账号密码
    public int updatePassword(String phone, String password) throws SQLException {
        int i = 0;
        getConnection();
//        Log.d(TAG, "updatePassword: ===============================================================================");
        sql = "select * from student where phone = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,phone);
        resultset = preparedStatement.executeQuery();

        if (resultset != null)
        {
            sql = "update student set password = ? where phone = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,password);
            preparedStatement.setString(2,phone);
            i = preparedStatement.executeUpdate();
        }
        if (i > 0)
        {
            System.out.println("密码更新成功");
        }
        closeConnection();
        return i;
    }

    //传入sql语句，获取题库信息  和用户表用法相同
    public List<QuestionBank> getQuestion(String sql) throws SQLException {
        List list = new ArrayList<QuestionBank>();

        getConnection();
        Log.w("TAG","get question ===========");
        preparedStatement  = connection.prepareStatement(sql);
        resultset = preparedStatement.executeQuery();
        while (resultset.next()){

            QuestionBank question = new QuestionBank();
            if(resultset !=null){
                question.setId(resultset.getInt("id"));                     //获取题号
                question.setQuestionChapter(resultset.getInt("question_chapter"));       //获取题目章节
                question.setQuestionType(resultset.getInt("question_type"));//获取题型 单选 多选 判断
                question.setTitle(resultset.getString("title"));            //获取题头
                question.setOption_a(resultset.getString("option_a"));      //选项a
                question.setOption_b(resultset.getString("option_b"));      //选项b
                question.setOption_c(resultset.getString("option_c"));      //选项c
                question.setOption_d(resultset.getString("option_d"));      //选项d
                //题的答案：1.A，2.B，3.C，4.D，5.对，6.错
                question.setQuestionAnswer(resultset.getInt("question_answer"));
                question.setQuestionAnalysis(resultset.getString("question_analysis")); //解析
                question.setSubjectId(resultset.getInt("subject_id"));                  //题目所属科目外键
                list.add(question);
            }
        }
        closeConnection();
        return list;
    }
    //获得题目个数
    public List<Sort> getQuestionCount(String sql) throws SQLException {
        List list = new ArrayList<Sort>();
        getConnection();
        preparedStatement  = connection.prepareStatement(sql);
        resultset = preparedStatement.executeQuery();
        while (resultset.next()){

            Sort questionCount = new Sort();

            questionCount.setSort(resultset.getInt("count(id)"));                     //获取题号
            list.add(questionCount);
        }
        closeConnection();
        return list;
    }



        //这么写不行
//    public int getIntQuestionCount(String sql) throws SQLException {
//        int count = 0;
//        getConnection();
//        preparedStatement  = connection.prepareStatement(sql);
//        resultset = preparedStatement.executeQuery();
////        while (resultset.next()){
////            count = count + 1;
////        }
//        if (resultset.next())
//        {
//            count = count + 1;
//        }
//        closeConnection();
//        return count;
//    }





    //获取中间表 整体思路
    //目的 select * from student_question where studentid = 用户学号 and questionid = 题库题号
    //如果 为空没找到，那就insert 一条数据
    //如果 不为空说明找到了，那么update 里面的question_right , question_done, question_favorite
    public int updateStudentQuestion(String student_id, int question_id, int question_right) throws SQLException {
        int i = 0;
        getConnection();

        String sql1 = "update student_question set question_right = ?,date = now() where student_id = ? and question_id = ? ";
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setInt(1,question_right);
            preparedStatement.setString(2,student_id);
            preparedStatement.setInt(3,question_id);
            i = preparedStatement.executeUpdate();

        Log.d(TAG, "updateStudentQuestion: 执行了更新操作学号是:" + student_id + "题号是:" + question_id + "正确错误为:" + question_right);
        closeConnection();
        return i;


////        Log.d(TAG, "updateStudentQuestion: ===============================================================================");
//        sql = "select * from student_question where student_id = ? and question_id = ?";
//        // 1
//        // 2
//        // 3
//        // 4
//        preparedStatement = connection.prepareStatement(sql);
//        preparedStatement.setString(1,student_id);
//        preparedStatement.setInt(2,question_id);
//        resultset = preparedStatement.executeQuery();
//        //为什么能查到信息  getRow 还是0
////        System.out.println("这个是resultset" + resultset.getRow() +resultset.wasNull());
//        //查询结果如果不为空，那么更新
//        if (resultset.next())
//        {
//
//            Log.d(TAG, "updateStudentQuestion: +++++++++++执行了更新操作");
//            String sql1 = "update student_question set question_right = ?,date = now() where student_id = ? and question_id = ? ";
//            preparedStatement = connection.prepareStatement(sql1);
//            preparedStatement.setInt(1,question_right);
//            preparedStatement.setString(2,student_id);
//            preparedStatement.setInt(3,question_id);
//            i = preparedStatement.executeUpdate();
//        }
//        //查询结果如果为空，那么插入
//        else
//        {
//            Log.d(TAG, "updateStudentQuestion: +++++++执行了插入操作");
//            System.out.println("+++++++++++执行了插入操作");
//            String sql2 = "insert into student_question (student_id, question_id, question_right, date) values ( ?, ?, ?, now())";
//            preparedStatement = connection.prepareStatement(sql2);
//            preparedStatement.setString(1,student_id);
//            preparedStatement.setInt(2,question_id);
//            preparedStatement.setInt(3,question_right);
//            i = preparedStatement.executeUpdate();
//        }
//        if (i > 0)
//        {
//            System.out.println("学生题库中间表信息更新成功");
////        }
//        closeConnection();
//        return i;
    }


    public int firstStudentQuestion(String student_id, int question_id, int question_right) throws SQLException {
        int i = 0;
        getConnection();

        sql = "select * from student_question where student_id = ? and question_id = ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,student_id);
        preparedStatement.setInt(2,question_id);
        resultset = preparedStatement.executeQuery();

        //查询结果为空，那么插入
        if (!resultset.next())
        {
            String sql2 = "insert into student_question (student_id, question_id, question_right) values ( ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.setString(1,student_id);
            preparedStatement.setInt(2,question_id);
            preparedStatement.setInt(3,question_right);
            i = preparedStatement.executeUpdate();
        }

        if (i > 0)
        {
            System.out.println("学生题库中间表信息首次创建成功");
        }
        closeConnection();
        return i;
    }

    //获取学生题库中间表  用来记录错题 是否做过 是否收藏
    public List<StudentQuestion> getStudentQuestion(String studentID) throws SQLException {
        List list = new ArrayList<StudentQuestion>();
        getConnection();
        String sql = "select question_id, question_right from student_question where student_id = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,studentID);
        resultset = preparedStatement.executeQuery();
        while (resultset.next())
        {
            StudentQuestion sq = new StudentQuestion();
            sq.setQuestion_id(resultset.getInt("question_id"));
            sq.setQuestion_right(resultset.getInt("question_right"));
            list.add(sq);
        }
        closeConnection();
        return list;
    }

    //点击收藏时将题号id加入收藏表中
    public int insertFavorite(String student_id, int question_id) throws SQLException {
        int i = 0;

        getConnection();

        sql = "select * from question_favorite where student_id = ? and question_id = ?";
//        sql = "insert into question_favorite (student_id, question_id) values (?, ?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,student_id);
        preparedStatement.setInt(2,question_id);

        resultset = preparedStatement.executeQuery();
        //如果查询结果为空 那么说明这道题没有在表中， 执行插入操作
        if (!resultset.next())
        {
            String sql1 = "insert into question_favorite (student_id, question_id, date) values (?, ?, now())";
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setString(1,student_id);
            preparedStatement.setInt(2,question_id);

            i = preparedStatement.executeUpdate();
        }

        if ( i > 0)
        {
            Log.d(TAG, "insertFavorite: --------------插入成功");
        }

        closeConnection();
        return i;
    }


    //点击删除时时将题从收藏表中删除
    public int deleteFavorite(String student_id, int question_id) throws SQLException {
        int i = 0;

        getConnection();

        sql = "select * from question_favorite where student_id = ? and question_id = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,student_id);
        preparedStatement.setInt(2,question_id);

        resultset = preparedStatement.executeQuery();
        //如果查询结果不为空 那么说明这道题在表中， 执行删除操作
        if (resultset.next())
        {
            String sql1 = "delete from question_favorite where student_id = ? and question_id = ?";
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setString(1,student_id);
            preparedStatement.setInt(2,question_id);

            i = preparedStatement.executeUpdate();
        }

        if ( i > 0)
        {
            Log.d(TAG, "deleteFavorite: =============  删除成功");
        }

        closeConnection();
        return i;
    }

    //获取科目
    public List<Subject> getSubject() throws SQLException {
        List list = new ArrayList<Subject>();

        getConnection();

        String sql = "select * from subject";
        preparedStatement  = connection.prepareStatement(sql);

        resultset = preparedStatement.executeQuery();

        while (resultset.next()){
            Subject subject = new Subject();
            subject.setId(resultset.getInt("id"));                     //获取主键
            subject.setSubject_name(resultset.getString("subject_name"));            //获取题头
            list.add(subject);
        }
            Log.d(TAG, "getSubject: 科目获得成功 ");

        closeConnection();
        return list;
    }

    //修改网名
    public int updateNetName(String studentId, String netName) throws SQLException
    {
        int i = 0;
        getConnection();
        sql = "select * from student where student_id = ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,studentId);
        resultset = preparedStatement.executeQuery();
        //查询结果如果不为空，那么更新
        if (resultset.next())
        {
            Log.d(TAG, "updateNetName ============================ 执行了更新操作");
            String sql1 = "update student set net_name = ? where student_id = ?";
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setString(1,netName);
            preparedStatement.setString(2,studentId);
            i = preparedStatement.executeUpdate();
        }
        //查询结果如果为空，那么插入
        else
        {
            Log.d(TAG, "updateNetName:  查询结果为空");
        }
        if (i > 0)
        {
            Log.d(TAG, "updateNetName: ====================  修改昵称成功");
        }
        closeConnection();

        return i;
    }

    //修改性别
    public int updateGender(String studentId, String gender) throws SQLException
    {
        int i = 0;
        getConnection();
        sql = "select * from student where student_id = ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,studentId);
        resultset = preparedStatement.executeQuery();
        //查询结果如果不为空，那么更新
        if (resultset.next())
        {
            Log.d(TAG, "updateGender ============================ 执行了更新操作");
            String sql1 = "update student set gender = ? where student_id = ?";
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setString(1,gender);
            preparedStatement.setString(2,studentId);
            i = preparedStatement.executeUpdate();
        }
        //查询结果如果为空，那么插入
        else
        {
            Log.d(TAG, "updateGender:  查询结果为空");
        }
        if (i > 0)
        {
            Log.d(TAG, "updateGender: ====================  修改性别成功");
        }
        closeConnection();
        return i;
    }

    //修改手机号
    public int updatePhone(String studentId, String phone) throws SQLException
    {
        int i = 0;
        getConnection();
        sql = "select * from student where student_id = ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,studentId);
        resultset = preparedStatement.executeQuery();
        //查询结果如果不为空，那么更新
        if (resultset.next())
        {
            Log.d(TAG, "updatePhone ============================ 执行了更新操作");
            String sql1 = "update student set phone = ? where student_id = ?";
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setString(1,phone);
            preparedStatement.setString(2,studentId);
            i = preparedStatement.executeUpdate();
        }
        //查询结果如果为空，那么插入
        else
        {
            Log.d(TAG, "updatePhone:  查询结果为空");
        }
        if (i > 0)
        {
            Log.d(TAG, "updatePhone: ====================  修改手机号成功");
        }
        closeConnection();
        return i;
    }

    //当模拟考试提交试卷时，将信息添加到数据库
    public int insertMockScore(String studentId, Double mockScore) throws SQLException
    {
        int i;
        //获取数据库连接
        getConnection();
        //第三步：创建语句对象
        sql = "insert into mock_score (student_id, score, date) values (?,?,now());";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,studentId);
        preparedStatement.setDouble(2,mockScore);

        //第四步：执行sql语句，返回结果集
        i = preparedStatement.executeUpdate();

        if (i > 0)
        {
            Log.d(TAG, "insertMockScore: 执行成功");
        }
        closeConnection();
        return i;
    }

//    public List<Score> getMockScore(String studentID) throws SQLException {
//        List list = new ArrayList<Score>();
//        getConnection();
//        String sql = "select score, date from mock_score where student_id = ?";
//        preparedStatement = connection.prepareStatement(sql);
//        preparedStatement.setString(1,studentID);
//        resultset = preparedStatement.executeQuery();
//        while (resultset.next())
//        {
//            Score score = new Score();
//            score.setScore(resultset.getDouble("score"));
//            score.setDate(resultset.getString("date"));
//            list.add(score);
//        }
//        closeConnection();
//        return list;
//    }
    //获得模拟分数 和对应时间
    public List<Score> getMockScore(String sql) throws SQLException {
        List list = new ArrayList<Score>();
        getConnection();
        preparedStatement = connection.prepareStatement(sql);
        resultset = preparedStatement.executeQuery();
        while (resultset.next())
        {
            Score score = new Score();
            score.setScore(resultset.getDouble("score"));
            score.setDate(resultset.getString("date"));
            list.add(score);
        }
        closeConnection();
        return list;
    }



    //获得模拟考试最高分
    public List<Score> getMaxMockScore(String sql) throws SQLException {
        List list = new ArrayList<Score>();
        getConnection();
        preparedStatement = connection.prepareStatement(sql);
        resultset = preparedStatement.executeQuery();
        while (resultset.next())
        {
            Score score = new Score();
            score.setScore(resultset.getDouble("max(score)"));
            list.add(score);
        }
        closeConnection();
        return list;
    }

    //获得当前考试分数
    public List<Score> getThisMockScore(String sql) throws SQLException {
        List list = new ArrayList<Score>();
        getConnection();
        preparedStatement = connection.prepareStatement(sql);
        resultset = preparedStatement.executeQuery();
        while (resultset.next())
        {
            Score score = new Score();
            score.setScore(resultset.getDouble("score"));
            list.add(score);
        }
        closeConnection();
        return list;
    }


    //查询是否有今日考试信息
    public List<ExamHave> getExam(String student_id) throws SQLException {
        List list = new ArrayList<ExamHave>();

        getConnection();

        String sql = "select * from exam where state = 0 and year(date) = year(now()) and month(date) = month(now()) and day(date) = day(now()) and student_id = " + student_id;
        preparedStatement  = connection.prepareStatement(sql);
        resultset = preparedStatement.executeQuery();

        while (resultset.next()){
            ExamHave examHave = new ExamHave();
            examHave.setId(resultset.getInt("id"));

            list.add(examHave);
        }
        Log.d(TAG, "getExam: 查询是否有考试信息");

        closeConnection();
        return list;
    }

    //修改正式考试时，提交信息 主要包括 分数 时间 和试卷状态
    public int updateExam(String studentId, double score, int state) throws SQLException
    {
        int i = 0;
        getConnection();
        sql = "update exam set score = ? ,date = now(), state = ? where student_id = ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDouble(1,score);
        preparedStatement.setInt(2,state);
        preparedStatement.setString(3,studentId);
        i = preparedStatement.executeUpdate();

        if (i > 0)
        {
            Log.d(TAG, "updateExam: 修改考试信息成功");
        }
        closeConnection();
        return i;
    }


}
