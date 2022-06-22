package com.example.exambooktest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.exambooktest.mysql.MySQL;

import java.sql.SQLException;

public class UpdateGender extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_fanhui;
    private TextView tv_finish;
    private EditText et_up;
    private String gender;

    private SharedPreferences mShare;
    private String studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_gender);

        iv_fanhui = findViewById(R.id.iv_fanhui);
        iv_fanhui.setOnClickListener(this);

        tv_finish = findViewById(R.id.tv_finish);
        tv_finish.setOnClickListener(this);

        et_up = findViewById(R.id.et_up);
        mShare = getSharedPreferences("user", Context.MODE_PRIVATE);

        studentId = mShare.getString("studentId","");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_fanhui:
                this.finish();
                break;

            case R.id.tv_finish:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getGender();
                        MySQL mySQL = new MySQL();
                        try {
                            mySQL.updateGender(studentId,gender);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                }).start();
                this.finish();
                break;
        }

    }

    public void getGender(){
        gender = et_up.getText().toString().trim();
    }
}