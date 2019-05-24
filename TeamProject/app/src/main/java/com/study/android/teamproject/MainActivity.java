package com.study.android.teamproject;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "teamproject";

    TabLayout tabLayout;

    Category category;
    Chat chat;
    Home home;
    Mypage mypage;
    Search search;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabMenu);

        category = new Category();
        chat = new Chat();
        home = new Home();
        mypage = new Mypage();
        search = new Search();

        getSupportFragmentManager()
                .beginTransaction().replace(R.id.container, home).commit();

        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                Log.d(TAG, "POS:"+tab.getPosition());

                switch (tab.getPosition())
                {
                    case 0:
                        getSupportFragmentManager()
                                .beginTransaction().replace(R.id.container, home).commit();
                        break;
                    case 1:
                        getSupportFragmentManager()
                                .beginTransaction().replace(R.id.container, category).commit();
                        break;
                    case 2:
                        getSupportFragmentManager()
                                .beginTransaction().replace(R.id.container, search).commit();
                        break;
                    case 3:
                        getSupportFragmentManager()
                                .beginTransaction().replace(R.id.container, chat).commit();
                        break;
                    case 4:
                        getSupportFragmentManager()
                                .beginTransaction().replace(R.id.container, mypage).commit();
                        break;
                    default:
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab){}

            @Override
            public void onTabReselected(TabLayout.Tab tab){}
        });

    }

}
