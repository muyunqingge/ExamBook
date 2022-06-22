package com.example.exambooktest.ui.me;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.exambooktest.ErrorBookActivity;
import com.example.exambooktest.FavoritesActivity;
import com.example.exambooktest.LoginActivity;
import com.example.exambooktest.R;
import com.example.exambooktest.RegisterActivity;
import com.example.exambooktest.SetSelectActivity;
import com.example.exambooktest.mysql.QuestionBank;
import com.example.exambooktest.utils.Question;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MeFragment extends Fragment {

    private MeViewModel mViewModel;
    private SharedPreferences mShare;
    private String studentId;

    private TextView tv_name;
    private TextView tv_school;
    private TextView tv_tong_guo_lv;

    private ArrayList<QuestionBank> errorBanks = new ArrayList<>();
    private ArrayList<QuestionBank> notBanks = new ArrayList<>();
    private ArrayList<QuestionBank> allBanks = new ArrayList<>();

    private Button btn_favorite;
    private Button btn_error;
    private Button btn_shezhi;
    private static final String TAG = "MeFragment";

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_me, container, false);

        mShare = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        studentId = mShare.getString("studentId","");

//        initData();

        tv_name = root.findViewById(R.id.tv_name);
        tv_school = root.findViewById(R.id.tv_school);
        tv_tong_guo_lv = root.findViewById(R.id.tv_tong_guo_lv);

        btn_favorite = root.findViewById(R.id.btn_favorite);
        btn_error = root.findViewById(R.id.btn_error);
        btn_shezhi = root.findViewById(R.id.btn_shezhi);

        tv_name.setText(mShare.getString("name",""));
        tv_school.setText(mShare.getString("school",""));


        ImageView iv_touixang = root.findViewById(R.id.iv_touixang);
        //设置头像
        SharedPreferences imageShare = getActivity().getSharedPreferences("imagePath",Context.MODE_PRIVATE);
        SharedPreferences user_share = getActivity().getSharedPreferences("user",Context.MODE_PRIVATE);
        String imagePath = imageShare.getString("imagePath" + user_share.getString("studentId",""),"");
        if (!imagePath.equals("")){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            iv_touixang.setImageBitmap(bitmap);
            iv_touixang.setBackground(null);
        }


        btn_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FavoritesActivity.class);
                startActivity(intent);
            }
        });

        btn_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ErrorBookActivity.class);
                startActivity(intent);
            }
        });

        btn_shezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SetSelectActivity.class);
                startActivity(intent);
            }
        });


        return root;
    }


//    private void initData() {
//        Question notDoneQuestion = new Question(studentId, new Question.CallBack() {
//            @Override
//            public void onResult(List<QuestionBank> errorQuestionBanks, List<QuestionBank> ndBanks, List<QuestionBank> allqBanks) {
//                errorBanks.clear();
//                errorBanks.addAll(errorQuestionBanks);
//
//                notBanks.clear();
//                notBanks.addAll(ndBanks);
//
//                allBanks.clear();
//                allBanks.addAll(allqBanks);
//
//                Log.d(TAG, "handleMessage: ============2==========" + notBanks.size());
//                mHandler.sendEmptyMessage(98);
//            }
//        });
//
//
//    }

//    private final Handler.Callback callback = new Handler.Callback() {
//        @Override
//        public boolean handleMessage(@NonNull Message msg) {
//
//            if (msg.what == 98 ) {
//
//                //做错的
//                int size1 = errorBanks.size();
//                Log.d(TAG, "handleMessage: " + size1);
//                //没做的
//                int size2 = notBanks.size();
//                Log.d(TAG, "handleMessage: " + size2);
//                //总的
//                int size3 = allBanks.size();
//                Log.d(TAG, "handleMessage: " + size3);
//
//                double adoptPct = (int)(((float)((size3 - size2) - size1) / size3) * 100);
//
//                tv_tong_guo_lv.setText(MessageFormat.format("当前的考试通过率为  {0} %", adoptPct));    //通过率
//
//            }
//
//            return false;
//        }
//    };

//    private final Handler mHandler = new Handler(Looper.getMainLooper(), callback);

}