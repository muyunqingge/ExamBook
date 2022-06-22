package com.example.exambooktest;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.exambooktest.mysql.MySQL;

import java.sql.SQLException;
import java.util.Random;

public class RetrieveActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_phone, et_verification, et_password, et_password_again;
    private String phone, verification, password, passwordAgain;
    private Button btn_verification;
    boolean isOK = false;
    //随机验证码
    private int randNumber;

    private static final String TAG = "RetrieveActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);

        init();

        //生成四位随机数
        randNumber = new Random().nextInt(9000) + 1000;

        btn_verification = findViewById(R.id.btn_verification);
        btn_verification.setOnClickListener(this);
        findViewById(R.id.btn_confirm).setOnClickListener(this);
    }

    public void init()
    {
        et_phone    = findViewById(R.id.et_phone);
        et_verification   = findViewById(R.id.et_verification);
        et_password   = findViewById(R.id.et_password);
        et_password_again   = findViewById(R.id.et_password_again);
    }

    @Override
    public void onClick(View v) {
        MySQL mySQL = new MySQL();
        getEditString();
        switch (v.getId())
        {
            case R.id.btn_confirm:
                if (verification.equals(randNumber + "")){
                    new Thread(() -> {

                        //获取输入框中的文字
                        getEditString();
                        if (isOK())
                        {
                            try {
                                //执行MySQL中的修改密码指令，返回i 如果 i > 0 密码修改成功
                            int i = mySQL.updatePassword(phone, password);

                                //如果密码修改成功 展示dialog 允许返回登录界面,销毁进程

                                if (i > 0)
                                {
                                    showDialog();
                                }
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    }).start();
                }
                break;

            case R.id.btn_verification:
                if (!phone.equals("")){
                    if (ContextCompat.checkSelfPermission(RetrieveActivity.this, Manifest.permission.SEND_SMS)
                            != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(RetrieveActivity.this,new String[]{Manifest.permission.SEND_SMS},1);
                    } else {
                        sendSmsMessage();
                        btn_verification.setText("已发送");
                    }
                } else {
                    Toast.makeText(this,"请输入手机号，手机号为空",Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }

    public boolean isOK()
    {
        getEditString();
        if (TextUtils.isEmpty(phone)) {
            showResponse("请输入手机号");
        } else if (TextUtils.isEmpty(verification)) {
            showResponse("请输入与验证码");
        } else if (TextUtils.isEmpty(password)) {
            showResponse("请输入密码");
        } else if (TextUtils.isEmpty(passwordAgain)) {
            showResponse("请再次输入密码");
        } else if (!password.equals(passwordAgain)) {
            showResponse("两次输入的密码不一样，请重新输入");
        } else {
            isOK = true;
        }

        return isOK;
    }

    public void showResponse(String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RetrieveActivity.this,s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //获取控件中的字符串
    public void getEditString() {

        phone   = et_phone.getText().toString().trim();
        verification = et_verification.getText().toString().trim();
        password    = et_password.getText().toString().trim();
        passwordAgain = et_password_again.getText().toString().trim();

    }

    //在修改密码成功后展示展示dialog 并返回登录界面
    private void showDialog()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                AlertDialog.Builder builder = new AlertDialog.Builder(RetrieveActivity.this);
                builder.setTitle("密码修改成功")
                        .setMessage("点击离开返回登录页面")
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

    private void loginSucess() {
        Intent intent = new Intent(RetrieveActivity.this, LoginActivity.class);
        startActivity(intent);
        RetrieveActivity.this.finish();
    }

    public void sendSmsMessage(){

        SmsManager smsManager = SmsManager.getDefault();

        Log.d(TAG, "sendSmsMessage: 手机号为" + phone);

        smsManager.sendTextMessage(phone,null, String.valueOf(randNumber),null,null);

    }
}