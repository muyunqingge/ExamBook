package com.example.exambooktest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.exambooktest.mysql.MySQL;
import com.example.exambooktest.mysql.QuestionBank;
import com.example.exambooktest.mysql.SQLite;
import com.example.exambooktest.mysql.StudentQuestion;
import com.example.exambooktest.utils.ErrorQuestion;
import com.example.exambooktest.utils.Sort;
import com.example.exambooktest.utils.TestPaper;
import com.example.exambooktest.utils.WaitDialog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeachMaterialsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_down_ppt;
    private Button btn_down_outline;
    private Button btn_down_teachplan;
    private SharedPreferences mShare;
    private List<StudentQuestion> sq;
    private List<QuestionBank> qb;
    private List<QuestionBank> eQb;
    private String studentID;

    private WaitDialog waitDialog;

    private static final String TAG = "TeachMaterialsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach_materials);

        mShare = getSharedPreferences("user", Context.MODE_PRIVATE);

        studentID = mShare.getString("studentId","");

        btn_down_ppt = findViewById(R.id.btn_down_ppt);
        btn_down_ppt.setOnClickListener(this);

        btn_down_outline = findViewById(R.id.btn_down_outline);
        btn_down_outline.setOnClickListener(this);

        btn_down_teachplan = findViewById(R.id.btn_down_teachplan);
        btn_down_teachplan.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        waitDialog = new WaitDialog(this);
        switch (v.getId())
        {
            case R.id.btn_down_ppt:
                Uri uri = Uri.parse("https://pan.baidu.com/s/1j1HuaR9u9h3zIL5Nl6Ohvg?pwd=ws7i");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);

                break;
            case R.id.btn_down_outline:
                waitDialog.show();

                Log.d(TAG, "onClick:  +++++++++++++++++++++++++++show");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                waitDialog.dismiss();
                Log.d(TAG, "onClick:  +++++++++++++++++++++++++++++++++dis");
                break;

            case R.id.btn_down_teachplan:

                break;
            default:
                break;
        }
    }
}