package com.example.exambooktest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 *  工具类  单例模式(1.构造函数私有化 2.对外提供函数)
 */

public class SubjectDBHelper extends SQLiteOpenHelper {

    //数据库名字
    private static final String DB_NAME = "subject.db";

    private SQLiteDatabase mDB = null;
    private static final String TABLE_NAME = "subject";

    //数据库版本号
    private static final int DB_VERSION = 1;
    //2.对外提供函数 单例模式
    private static SubjectDBHelper mHelper;
    public static synchronized SubjectDBHelper getInstance(Context context){
        if (mHelper == null)
        {
            mHelper = new SubjectDBHelper(context,"subject.db",null,1); //以后想要数据库升级 修改成2   修改成 3
        }

        return mHelper;
    }




    //1.构造函数私有化
    private SubjectDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    //数据库初始化用的
    //表数据初始化    数据库第一次创建的时候调用，第二次发现有了，就不会重复创建了 意味着只会执行一次
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表:questions

        String sql = "create table subject (_id integer primary key autoincrement, " +
                "subject_name varchar(20) " +
                ")";

        db.execSQL(sql);


//        //删除这个表
//        String drop_sql = "DROP TABLE IF EXISTS " + TABLE_NAME + " ;";
//        db.execSQL(drop_sql);


//        String create_sql = "CREATE TABLE IF NO EXISTS " + TABLE_NAME +
//                " (" +
//                " id int primary key autoincrement not null," +
//                " question_chapter int," +
//                " question_type int," +
//                " title varchar(50)," +
//                " option_a varchar(100)," +
//                " option_b varchar(100)," +
//                " option_c varchar(100)," +
//                " option_d varchar(100)," +
//                " question_answer int," +
//                " question_analysis varchar(200)," +
//                " subject_id int" +
//                ");";
//        db.execSQL(create_sql);
    }
    //数据库升级用的
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

//    public long insert(QuestionBank questionBank)
//    {
//        ArrayList<QuestionBank> questionBankArrayList = new ArrayList<>();
//        questionBankArrayList.add(questionBank);
//    }

//    public long insert(ArrayList<QuestionBank>  questionBankArrayList)
//    {
//        for (int i = 0; i < questionBankArrayList.size(); i++)
//        {
//            QuestionBank qBank = questionBankArrayList.get(i);
//
//            ArrayList<QuestionBank> tempArray = new ArrayList<>();
//
//            if (qBank.id > 0)
//            {
//                    String condition = String.format("id = '%s'", qBank.getId());
//                    tempArray = query(condition);
//            }
//
//        }
//    }

//    private ArrayList<QuestionBank> query(String condition) {
//
//        String sql = String.format("select * from %s where %s;",TABLE_NAME, condition);
//
//        ArrayList<QuestionBank> infoArray = new ArrayList<>();
//        Cursor cursor = mDB.rawQuery(sql,null);
//
//        while (cursor.moveToNext())
//        {
//            QuestionBank qB = new QuestionBank();
//            qB.id = cursor.getInt(0);
//
//        }
//
//    }
}
