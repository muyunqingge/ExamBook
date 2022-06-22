package com.example.exambooktest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MyStuffActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_stuff);

        ImageView iv_fanhui = findViewById(R.id.iv_fanhui);
        iv_fanhui.setOnClickListener(this);

        LinearLayout ll_net_name = findViewById(R.id.ll_net_name);
        ll_net_name.setOnClickListener(this);

        LinearLayout ll_gender = findViewById(R.id.ll_gender);
        ll_gender.setOnClickListener(this);

        LinearLayout ll_phone = findViewById(R.id.ll_phone);
        ll_phone.setOnClickListener(this);

        LinearLayout ll_head = findViewById(R.id.ll_head);
        ll_head.setOnClickListener(this);
        ll_head.setVisibility(View.GONE);



    }

    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId())
        {
            case R.id.iv_fanhui:
                this.finish();
                break;

            case R.id.ll_net_name:
                intent = new Intent(MyStuffActivity.this,UpdateNetName.class);
                startActivity(intent);
                break;

            case R.id.ll_gender:
                intent = new Intent(MyStuffActivity.this,UpdateGender.class);
                startActivity(intent);
                break;

            case R.id.ll_phone:
                intent = new Intent(MyStuffActivity.this,UpdatePhone.class);
                startActivity(intent);
                break;

            case R.id.ll_head:
                intent = new Intent(MyStuffActivity.this,UpdateHead.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


}