package com.study.android.ex03_layout1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 다음과 같이 원하는 xml 선택사용가능

        //setContentView(R.layout.activity_main);
        setContentView(R.layout.activity2_main);
    }
}
