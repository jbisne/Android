package com.study.android.ex23_fragment1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "lecture";

    MainFragment mainFragment;
    MenuFragment menuFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragment =
                (MainFragment) getSupportFragmentManager().findFragmentById(R.id.mainFragment);
        menuFragment = new MenuFragment();
    }

    public void onFragmentChange(int index)
    {
        if(index == 0)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,menuFragment).commit();
        }
        else if (index == 1)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, mainFragment).commit();
        }
    }
}
// 액티비티 안에서 프레그먼트 하나가 기능을 하나 할수도있고, 액티비티안에 여러 프레그먼트가
// 들어갈수있다.
// 부분적으로 들어가는건 inflate를 사용해주고 view를 만들어준다.
