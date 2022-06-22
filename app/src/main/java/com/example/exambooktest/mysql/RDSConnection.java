package com.example.exambooktest.mysql;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class RDSConnection {
    private static String CLS = "com.mysql.jdbc.Driver";
    private static String URL = "jdbc:mysql://rm-bp1hiidx93h31atf6no.mysql.rds.aliyuncs.com:3306/test?useSSL=false&serverTimezone=UTC";
    private static String USER = "muyun";
    private static String PWD = "123456*a";

    private static final String TAG = "RDSConnection";

    public static Connection connection;
    public static Statement statement;
    public static PreparedStatement preparedStatement;
    public static ResultSet resultset;

    //获取链接
    public static void getConnection() {
        try {
            //第一步：加载驱动程序
            Class.forName(CLS);

            //第二步：连接三个参数：1.数据库URL地址 2.用户账号 3.用户密码
            connection = DriverManager.getConnection(URL, USER, PWD);
//            Log.d(TAG, "getConnection: 连接成功");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
//            Log.d(TAG, "getConnection: 连接失败");
        }
    }

    //关闭连接
    public static void closeConnection() {
        //打开的每个对象都要占内存空间
        //为了释放内存空间，必须关闭他们
        //按打开对象的逆序关闭对象
        try {
            if (resultset != null) {
                resultset.close();
                resultset = null;
            }else if (preparedStatement != null) {
                preparedStatement.close();
                preparedStatement = null;
            }else if (statement != null) {
                statement.close();
                statement = null;
            }else if(connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
