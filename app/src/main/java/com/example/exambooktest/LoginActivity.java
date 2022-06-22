package com.example.exambooktest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.telephony.SmsManager;
import android.text.SpanWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.exambooktest.database.QuestionDBHelper;
import com.example.exambooktest.database.SubjectDBHelper;
import com.example.exambooktest.mysql.MySQL;
import com.example.exambooktest.mysql.QuestionBank;
import com.example.exambooktest.mysql.Subject;
import com.example.exambooktest.mysql.User;
import com.mysql.fabric.Response;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
    登录界面
 */
public class LoginActivity extends AppCompatActivity implements View.OnLongClickListener, View.OnClickListener {

    private static final String TAG = "loginActivity";
    private ImageView iv_head;
    private EditText et_phone;
    private EditText et_password;
    private Button btn_verification;
    private Button btn_login;
    private TextView tv_type;
    private TextView tv_forget;
    private TextView register;

    private String phone;
    private String password;
    private Context context;

    private SharedPreferences user_share;
    private SharedPreferences imageShare;
    private SharedPreferences mShared;

    //随机验证码
    private int randNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        System.out.println("登录界面===============================================================================");
        context = this;
        init();

        //生成四位随机数
        randNumber = new Random().nextInt(9000) + 1000;

        //记住登录账号密码
        mShared = getSharedPreferences("share_login", MODE_PRIVATE);
        String phone1 = mShared.getString("phone", "");
        String password1 = mShared.getString("password", "");
        et_phone.setText(phone1);
        et_password.setText(password1);

        //设置头像
        imageShare = getSharedPreferences("imagePath",MODE_PRIVATE);
        String imagePath = imageShare.getString("imagePath" + user_share.getString("studentId",""),"");
        if (!imagePath.equals("")){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            iv_head.setImageBitmap(bitmap);
            iv_head.setBackground(null);
        }
//        new Thread(() -> {
//            MySQL mySQL = new MySQL();
//
//            try {
//                List<User> user = mySQL.getUser("select student_id, name, password, gender, school, major, grade, net_name from student where name = '张三';");
//
//                //试验对象
//                System.out.println(user.toString());
//                System.out.println(user.get(0).getPassword());
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }).start();
    }

    private void init() {

        tv_type = findViewById(R.id.tv_type);
        tv_type.setOnClickListener(this);

        btn_verification = findViewById(R.id.btn_verification);
        btn_verification.setOnClickListener(this);

        tv_forget = findViewById(R.id.tv_forget);
        tv_forget.setOnClickListener(this);

        et_phone = findViewById(R.id.et_phone);
        et_password = findViewById(R.id.et_password);

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        register = findViewById(R.id.tv_register);
        register.setOnClickListener(this);

        iv_head = findViewById(R.id.iv_head);

        user_share = getSharedPreferences("user",MODE_PRIVATE);

//        question_share = getSharedPreferences("question",MODE_PRIVATE);

    }

    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {
            case R.id.tv_type:
                if (tv_type.getText().toString().equals("验证码登录")) {
                    et_phone.setHint("请输入手机号");
                    et_password.setHint("请输入验证码");
                    et_phone.setText("");
                    et_password.setText("");
                    btn_verification.setVisibility(View.VISIBLE);
                    tv_type.setText("密码登录");

                } else if (tv_type.getText().toString().equals("密码登录")) {
                    et_phone.setHint("请输入学号");
                    et_password.setHint("请输入登陆密码");
                    et_phone.setText("");
                    et_password.setText("");
                    btn_verification.setVisibility(View.INVISIBLE);
                    tv_type.setText("验证码登录");
                }
                break;

            case R.id.tv_forget:
                intent = new Intent(context, RetrieveActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_login:
                if (tv_type.getText().toString().equals("验证码登录")){
                    new Thread(() -> {
                        MySQL mySQL = new MySQL();

                        //获取输入框中的信息
                        getEditString();

                        //根据输入的账号从数据库中查询账号对应的密码 注意后面的 sql语句不要少了 ''  傻了好久
                        //select * from 不严谨不好  这里先这么用着过后再改
                        //sql注入问题
                        String sql = "select * from student where student_id = '" +
                                phone + "' and password = '" + password + "';";
                        List<User> user = null;
                        try {
                            user = mySQL.getUser(sql);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
//                    System.out.println(user.get(0).getStudentId());
                        //如果user表查到了信息 执行登录操作,没查到信息吐司一个提示
                        if (!user.isEmpty()) {
                            SharedPreferences.Editor editor_user = user_share.edit();
                            editor_user.putString("studentId", user.get(0).getStudentId());
                            editor_user.putString("name", user.get(0).getName());
                            editor_user.putString("password", user.get(0).getPassword());
                            editor_user.putString("phone", user.get(0).getPhone());
                            editor_user.putString("gender", user.get(0).getGender());
                            editor_user.putString("school", user.get(0).getSchool());
                            editor_user.putString("college", user.get(0).getCollege());
                            editor_user.putString("major", user.get(0).getMajor());
                            editor_user.putString("grade", user.get(0).getGrade());
                            editor_user.putString("netName", user.get(0).getNetName());
                            editor_user.putFloat("mockScore", user.get(0).getMockScore());
                            editor_user.putFloat("formalScore", user.get(0).getFormalScore());
                            editor_user.commit();

                            //记住登录账号密码
                            SharedPreferences.Editor editor = mShared.edit();
                            editor.putString("phone",et_phone.getText().toString());
                            editor.putString("password", et_password.getText().toString());
                            editor.commit();


                            Intent intent1 = new Intent(context, MainActivity.class);
                            startActivity(intent1);
                            LoginActivity.this.finish();
                        }else
                        {
                            showResponse();
                        }

                    }).start();
                    //手机号登录
                } else if (tv_type.getText().toString().equals("密码登录")){
                    if (et_password.getText().toString().trim().equals("")){
                        Toast.makeText(this,"请输入验证码",Toast.LENGTH_SHORT).show();
                    } else {
                        if (!et_password.getText().toString().trim().equals(randNumber + "")){
                            Toast.makeText(this,"验证码输入错误,请重新输入",Toast.LENGTH_SHORT).show();
                        } else {
                            new Thread(() -> {
                                MySQL mySQL = new MySQL();

                                //获取输入框中的信息
                                getEditString();

                                //根据输入的账号从数据库中查询账号对应的密码 注意后面的 sql语句不要少了 ''  傻了好久
                                //select * from 不严谨不好  这里先这么用着过后再改
                                //sql注入问题
                                String sql = "select * from student where phone = '" +
                                        phone + "';";
                                List<User> user = null;
                                try {
                                    user = mySQL.getUser(sql);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }

                                //如果user表查到了信息 执行登录操作,没查到信息吐司一个提示
                                if (!user.isEmpty()) {
                                    SharedPreferences.Editor editor_user = user_share.edit();
                                    editor_user.putString("studentId", user.get(0).getStudentId());
                                    editor_user.putString("name", user.get(0).getName());
                                    editor_user.putString("password", user.get(0).getPassword());
                                    editor_user.putString("phone", user.get(0).getPhone());
                                    editor_user.putString("gender", user.get(0).getGender());
                                    editor_user.putString("school", user.get(0).getSchool());
                                    editor_user.putString("college", user.get(0).getCollege());
                                    editor_user.putString("major", user.get(0).getMajor());
                                    editor_user.putString("grade", user.get(0).getGrade());
                                    editor_user.putString("netName", user.get(0).getNetName());
                                    editor_user.putFloat("mockScore", user.get(0).getMockScore());
                                    editor_user.putFloat("formalScore", user.get(0).getFormalScore());
                                    editor_user.commit();

                                    //记住登录账号密码
                                    SharedPreferences.Editor editor = mShared.edit();
                                    editor.putString("phone",et_phone.getText().toString());
//                                    editor.putString("password", "");
                                    editor.commit();

                                    Intent intent1 = new Intent(context, MainActivity.class);
                                    startActivity(intent1);
                                    LoginActivity.this.finish();
                                }else
                                {
                                    showResponse();
                                }
                            }).start();
                        }
                    }
                }
                break;

            case R.id.btn_verification:
                phone = et_phone.getText().toString().trim();
                if (!phone.equals("")){
                    if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.SEND_SMS)
                            != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(LoginActivity.this,new String[]{Manifest.permission.SEND_SMS},1);
                    } else {
                        sendSmsMessage();
                        btn_verification.setText("已发送");
                    }
                } else {
                    Toast.makeText(this,"请输入手机号，手机号为空",Toast.LENGTH_SHORT).show();
                }
                break;


            case R.id.tv_register:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void showResponse() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this,"登陆失败，请重新输入账号密码", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getEditString() {
        phone = et_phone.getText().toString().trim();
        password = et_password.getText().toString().trim();
    }





    @Override
    public boolean onLongClick(View v) {
        return false;
    }
    public void sendSmsMessage(){

        SmsManager smsManager = SmsManager.getDefault();

        Log.d(TAG, "sendSmsMessage: 手机号为" + phone);

        smsManager.sendTextMessage(phone,null, String.valueOf(randNumber),null,null);

    }



}