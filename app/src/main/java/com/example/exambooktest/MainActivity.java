package com.example.exambooktest;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

/**
 * WelcomeActivity                      欢迎页面             √
 * LoginActivity                        登录页面             √
 * RegisterActivity                     注册页面             √
 * RetrieveActivity                     找回密码页面          √
 * SequentialAnswerActivity             顺序练习选择登录页面
 * SyncActivity                         同步更新页面
 *
 *
 * ChapterActivity                      专项练习选择界面
 * ChapterPracticeActivity              章节练习页面
 * TestPracticeActivity                 考点练习页面
 * MockTestActivity                     模拟考试页面
 * MockSchoolReportActivity             模拟成绩单页面
 * QuestionActivity                     做题界面
 * TeachMaterialsActivity               教学材料界面
 * VideoActivity                        教学视频界面
 * ErrorBookActivity                    错题本选择界面  指向做题
 * FavoritesActivity                    收藏题选择界面  指向收藏题
 * QuestionFavoritesActivity            收藏题页面
 * NoticeActivity                       消息界面
 *
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_practice, R.id.navigation_exam, R.id.navigation_friend, R.id.navigation_me)
//                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        //        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        NavigationUI.setupWithNavController(navView, navController);
    }

}