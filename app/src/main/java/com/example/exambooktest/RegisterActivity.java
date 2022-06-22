package com.example.exambooktest;

import android.Manifest;
import android.app.Instrumentation;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.exambooktest.mysql.MySQL;
import com.example.exambooktest.mysql.User;
import com.example.exambooktest.utils.Sort;
import com.example.exambooktest.utils.WaitDialog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingDeque;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_student_id, et_student_name, et_password, et_password_again, et_phone, et_verification, et_school, et_college, et_major, et_grade;
    private String studentId, studentName, pwd, pwdAgain, phone, school, college, major, grade, vft;
    private Button btn_verification;

    private BroadCastDone broadCastDone;
    private final String donefilter="donefilter";
    private WaitDialog waitDialog;

    private int randNumber;



    private static final String TAG = "RegisterActivity";
    boolean isOK = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        //生成四位随机数
        randNumber = new Random().nextInt(9000) + 1000;

        Log.d(TAG, "onCreate: 随机数为" + randNumber);

        findViewById(R.id.btn_confirm).setOnClickListener(this);
        findViewById(R.id.btn_verification).setOnClickListener(this);

    }

    private void registerBroadcast()
    {
        broadCastDone = new BroadCastDone();

        IntentFilter filter = new IntentFilter(donefilter);

        LocalBroadcastManager.getInstance(this).registerReceiver(broadCastDone,filter);
    }

    private void init() {
        et_student_id = findViewById(R.id.et_student_id);
        et_student_name = findViewById(R.id.et_student_name);
        et_password = findViewById(R.id.et_password);
        et_password_again = findViewById(R.id.et_password_again);
        et_phone = findViewById(R.id.et_phone);
        et_school = findViewById(R.id.et_school);
        et_college = findViewById(R.id.et_college);
        et_major = findViewById(R.id.et_major);
        et_grade = findViewById(R.id.et_grade);

        et_verification = findViewById(R.id.et_verification);
        btn_verification = findViewById(R.id.btn_verification);
    }




    @Override
    public void onClick(View v) {

        MySQL mySQL = new MySQL();

        switch (v.getId())
        {
            case R.id.btn_confirm:
                //判断短信验证码是否输入
                if (isOK()){
                    if (et_verification.getText().toString().trim().equals(randNumber + "")){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
//                            if (isOK())
//                            {
                                Log.d(TAG, "isOK结束++++++++++++++++++++++"
                                        + school + college + major + grade
                                        + studentId + studentName + pwd);


                                String sql = "select * from student where student_id = '" +
                                        studentId + "';";
                                List<User> user = null;

                                try {
                                    user = mySQL.getUser(sql);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                if (!user.isEmpty()) {
                                    showResponse2();
                                }
                                else {

                                    try {
                                        mySQL.insertUser(school, college, major, grade, studentId, studentName, pwd, phone);
                                        //创建账号时直接插入中间表 不在之后操作
                                        mySQL.insertQuestionStudent(studentId);
                                        Log.d(TAG, "run: 耗时操作完成");

                                        //显示Dialog 返回登录页
                                        showDialog();

                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    }
                                }
                            }
//                        }
                        }).start();
                    } else if(et_verification.getText().toString().trim().equals("")){
                        Toast.makeText(this,"验证码为空，请输入验证码",Toast.LENGTH_SHORT).show();
                    } else if(!et_verification.getText().toString().trim().equals(randNumber + "")){
                        Toast.makeText(this,"验证码输入错误，请重新输入验证码",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this,"出现未知错误",Toast.LENGTH_SHORT).show();
                    }
                }

               break;

                //短信验证功能
            case R.id.btn_verification:
                phone = et_phone.getText().toString().trim();
                if (!phone.equals("")){
                    if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(RegisterActivity.this,new String[]{Manifest.permission.SEND_SMS},1);
                    } else {
                        sendSmsMessage();
                        btn_verification.setText("已发送");
                    }
                } else {
                    Toast.makeText(this,"请输入手机号，手机号为空",Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private void loginSucess() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        RegisterActivity.this.finish();
    }

    public void sendSmsMessage(){

        SmsManager smsManager = SmsManager.getDefault();

        Log.d(TAG, "sendSmsMessage: 手机号为" + phone);

        smsManager.sendTextMessage(phone,null, String.valueOf(randNumber),null,null);

    }

    //获取控件中的字符串
    public void getEditString() {
        school = et_school.getText().toString().trim();
        college = et_college.getText().toString().trim();
        major = et_major.getText().toString().trim();
        grade = et_grade.getText().toString().trim();
        studentId = et_student_id.getText().toString().trim();
        studentName = et_student_name.getText().toString().trim();
        pwd = et_password.getText().toString().trim();
        pwdAgain = et_password_again.getText().toString().trim();
        phone = et_phone.getText().toString().trim();
        vft = et_verification.getText().toString().trim();
    }


    //判断输入框是否填完整
    //判断两次密码是否一样
    public boolean isOK()
    {
        getEditString();

        if (TextUtils.isEmpty(school)) {
            showResponse("请输入学校");
        }else  if (TextUtils.isEmpty(college)) {
            showResponse("请输入学院");
        }else  if (TextUtils.isEmpty(major)) {
            showResponse("请输入专业");
        }else  if (TextUtils.isEmpty(grade)) {
            showResponse("请输入班级");
        } else  if (TextUtils.isEmpty(studentId)) {
            showResponse("请输入学号");
        } else if (TextUtils.isEmpty(studentName)) {
            showResponse("请输入姓名");
        } else if (TextUtils.isEmpty(pwd)) {
            showResponse("请输入密码");
        } else if (TextUtils.isEmpty(pwdAgain)) {
            showResponse("请再次输入密码");
        } else if (!pwd.equals(pwdAgain)) {
            showResponse("两次输入的密码不一样，请重新输入");
        } else if (TextUtils.isEmpty(phone)) {
            showResponse("请输入手机号");
        }else {
            isOK = true;
        }

        return isOK;
    }

    //在线程中显示吐司
    public void showResponse(String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterActivity.this,s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //在线程中显示吐司
    public void showResponse2() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterActivity.this,"信息已注册，请勿重复注册", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //注册成功后显示dialog

    private void showDialog()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("注册成功")
                        .setMessage("点击离开返回登录页面")
                        //不允许点击外部取消
                        .setCancelable(false)
                        .setPositiveButton("离开", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                loginSucess();
                            }
                        })
                        .create()
                        .show();
            }
        });

    }


    private class BroadCastDone extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null)
            {
                waitDialog.dismiss();
                Log.d(TAG, "onReceive: ------------------------dismiss");
            }
        }
    }

    private class Thread1 extends Thread {
        private Context context;

        public Thread1(Context context){
            this.context = context;
        }

        @Override
        public void run() {
            MySQL mySQL = new MySQL();
            if (isOK())
            {

                Log.d(TAG, "isOK结束++++++++++++++++++++++"
                        + school + college + major + grade
                        + studentId + studentName + pwd);
                //-------------------------------------------------------
//                try {
//                    registerBroadcast();
//                    Log.d(TAG, "run: @!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                    Thread.sleep(10000);
//                    Log.d(TAG, "run: 耗时操作结束");
//
//                    Log.d(TAG, "run: intent");
//
//                    Intent intent = new Intent(donefilter);
//
//                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
//                    Log.d(TAG, "run: =================!!===============");
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                //------------------------------------------------------------------
//
//                registerBroadcast();
                Log.d(TAG, "run: @!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                try {
                    mySQL.insertUser(school,college,major,grade,studentId,studentName,pwd,phone);
                    mySQL.insertQuestionStudent(studentId);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                Log.d(TAG, "run: 耗时操作结束");
                Intent intent = new Intent(donefilter);

                Log.d(TAG, "run: intent");

                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                Log.d(TAG, "run: =================!!===============");

                //显示Dialog 返回登录页
//                    showDialog();

            }
        }
    }



}