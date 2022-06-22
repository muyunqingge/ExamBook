package com.example.exambooktest.ui.exam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.exambooktest.ExamQuestionActivity;
import com.example.exambooktest.ExamTestActivity;
import com.example.exambooktest.QuestionActivity;
import com.example.exambooktest.R;

public class ExamFragment extends Fragment {

    private ExamViewModel examViewModel;
    private TextView tv_student_id;
    private TextView tv_student_name;

    private Button btn_start_exam;

    private SharedPreferences mShare;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        examViewModel =
                ViewModelProviders.of(this).get(ExamViewModel.class);
        View root = inflater.inflate(R.layout.fragment_exam, container, false);

        mShare = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        tv_student_id = root.findViewById(R.id.tv_student_id);
        tv_student_name = root.findViewById(R.id.tv_student_name);
        btn_start_exam = root.findViewById(R.id.btn_start_exam);


        //初始化View
        tv_student_id.setText("学         号:     " + mShare.getString("studentId",""));
        tv_student_name.setText("考生姓名:     " + mShare.getString("name",""));

        btn_start_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ExamTestActivity.class);
                startActivity(intent);

            }
        });
        examViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

}