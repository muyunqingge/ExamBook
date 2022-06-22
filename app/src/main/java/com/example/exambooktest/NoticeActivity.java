package com.example.exambooktest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.exambooktest.adapter.TheListAdapter;
import com.example.exambooktest.utils.Msg;

import java.util.ArrayList;
import java.util.List;

public class NoticeActivity extends AppCompatActivity {


    private ListView list_notice;
    private TheListAdapter mListAdapter;
    private List<Msg> mList =  new ArrayList<>();

    private static final String TAG = "NoticeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        bean();
        Log.i(TAG, "onCreate: " + mList);


        list_notice = findViewById(R.id.list_notice);

        mListAdapter = new TheListAdapter(NoticeActivity.this, mList);
        list_notice.setAdapter(mListAdapter);


    }
    private void bean(){
        mList.add(new Msg("通知1：","荒", "一剑断万古", "10:44"));
        mList.add(new Msg("通知2：","辰东", "我继续托更了", "10:44"));
        mList.add(new Msg("通知3：","萧炎", "三十年河东，三十年河西", "10:44"));
        mList.add(new Msg("通知4：","林动", "修炼一途,乃窃阴阳,夺造化,转涅盘,握生死,掌轮回。", "10:44"));
        mList.add(new Msg("通知5：","法外狂徒", "这犯法吗？这不犯法，这叫紧急避险。", "10:44"));
        mList.add(new Msg("通知6：","张三", "乌拉乌拉", "10:44"));
    }


}