package com.example.exambooktest.ui.practice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.exambooktest.ErrorBookActivity;
import com.example.exambooktest.FavoritesActivity;
import com.example.exambooktest.MainActivity;
import com.example.exambooktest.MockTestActivity;
import com.example.exambooktest.NoticeActivity;
import com.example.exambooktest.R;
import com.example.exambooktest.SequentialAnswerActivity;
import com.example.exambooktest.TeachMaterialsActivity;
import com.example.exambooktest.VideoActivity;
import com.example.exambooktest.mysql.MySQL;
import com.example.exambooktest.mysql.QuestionBank;
import com.example.exambooktest.mysql.SQLite;
import com.example.exambooktest.mysql.Subject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PracticeFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "PracticeFragment";
    private PracticeViewModel practiceViewModel;

    private String[] data = {"早上好", "早上好啊", "中午好", "中午好啊", "下午好", "下午好啊", "晚上好", "晚上好啊"};
    private ListView listView;

    private TextView tv_sync;

    private ImageView iv_cailiao;
    private ImageView iv_shipin;
    private ImageView iv_cuotiben;
    private ImageView iv_shoucangjia;
    private ImageView iv_notice;
    private Button btn_practice;
    private Button btn_exam;
    private Spinner sp_subject;
    private List<QuestionBank> qb;      //为账号初次更新 sq表

    private SQLite sqSubject;           //科目
    private SharedPreferences mShare;
    private String studentId;
    private List<Subject> subjects = new ArrayList<>();
    private List<String> subject_name = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        practiceViewModel =
                ViewModelProviders.of(this).get(PracticeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_practice, container, false);
        final TextView textView = root.findViewById(R.id.tv_practice);

        listView = root.findViewById(R.id.lv_community);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
        Log.i(TAG, "onChanged: " + data);
        mShare = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        studentId = mShare.getString("studentId","");

        iv_cailiao = root.findViewById(R.id.iv_cailiao);
        iv_cailiao.setOnClickListener(this);

        iv_shipin = root.findViewById(R.id.iv_shipin);
        iv_shipin.setOnClickListener(this);

        iv_cuotiben = root.findViewById(R.id.iv_cuotiben);
        iv_cuotiben.setOnClickListener(this);

        iv_shoucangjia = root.findViewById(R.id.iv_shoucangjia);
        iv_shoucangjia.setOnClickListener(this);

        iv_notice = root.findViewById(R.id.iv_notice);
        iv_notice.setOnClickListener(this);

        btn_practice = root.findViewById(R.id.btn_practice);
        btn_practice.setOnClickListener(this);

        btn_exam = root.findViewById(R.id.btn_exam);
        btn_exam.setOnClickListener(this);

        sp_subject = root.findViewById(R.id.sp_subject);

//        initData();
//
//        initSpinner();

//        unRight_share = root.getContext().getSharedPreferences("unRight", Context.MODE_PRIVATE);
//
//        user_share = root.getContext().getSharedPreferences("user",Context.MODE_PRIVATE);

        practiceViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);

            }
        });

        //当在练习界面时候  更新 student_question表
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                MySQL mySQL = new MySQL();
//                try {
//                    qb = mySQL.getQuestion("select * from question_bank");
//                } catch (SQLException throwables) {
//                    throwables.printStackTrace();
//                }
//                for (int i = 0; i < qb.size(); i++){
//                    try {
//                        mySQL.firstStudentQuestion(studentId, qb.get(i).getId(), 0);
//                    } catch (SQLException throwables) {
//                        throwables.printStackTrace();
//                    }
//                }
//            }
//        }).start();
        return root;
    }

//    private void initData() {
//        sqSubject = new SQLite();
//        subjects = sqSubject.getSubject();
//    }
//
//    private void initSpinner() {
//        for (int i = 0; i < subjects.size(); i++)
//        {
//            subject_name.add(subjects.get(i).getSubject_name());
//        }
//
//        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(getActivity(),
//                R.layout.item_select,subject_name);
//
//        subjectAdapter.setDropDownViewResource(R.layout.item_dropdown);
//
//        sp_subject.setPrompt("请选择科目");
//        sp_subject.setAdapter(subjectAdapter);
//        sp_subject.setSelection(3);
//        sp_subject.setOnItemSelectedListener(this);
//    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId())
        {

                //点击进入教学材料
            case R.id.iv_cailiao:
                intent = new Intent(getActivity(), TeachMaterialsActivity.class);
                startActivity(intent);
                break;

                //点击进入教学视频
            case R.id.iv_shipin:
                intent = new Intent(getActivity(), VideoActivity.class);
                startActivity(intent);
                break;

                //点击进入错题本
            case R.id.iv_cuotiben:

                intent = new Intent(getActivity(), ErrorBookActivity.class);
                startActivity(intent);
                break;

                //点击进入收藏
            case R.id.iv_shoucangjia:
                intent = new Intent(getActivity(), FavoritesActivity.class);
                startActivity(intent);
                break;

                //点击进入消息
            case R.id.iv_notice:
                intent = new Intent(getActivity(), NoticeActivity.class);
                startActivity(intent);
                break;

                //顺序练习按钮
            case R.id.btn_practice:
                intent = new Intent(getActivity(), SequentialAnswerActivity.class);
                startActivity(intent);
                break;

                //模拟考试按钮
            case R.id.btn_exam:
                intent = new Intent(getActivity(), MockTestActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(getActivity(),"你点击了" + subject_name.get(position),Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
}