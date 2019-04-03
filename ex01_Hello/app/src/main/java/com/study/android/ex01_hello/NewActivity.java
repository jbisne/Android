package com.study.android.ex01_hello;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class NewActivity extends AppCompatActivity
{

    String sName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        // Intent에 전달된 데이터 구하기
        Intent intent = getIntent();
        sName = intent.getStringExtra("CustomerName");
    }

    public void onBtn1Clicked(View v)
    {
        // 토스트로 전달된 데이터 보여주기
        Toast.makeText(getApplicationContext(),
                    "CustomerName : " + sName, Toast.LENGTH_SHORT).show();
    }

    public void onBtn2Clicked(View v)
    {
        // 현재 Intent 종료시 Intent에 전달할 데이터 세팅
        Intent intent = new Intent();
        intent.putExtra("BackData", "트와이스");
        setResult(10, intent);
        //여기까지는 죽지만, Result는 남아있다.(intent를 넣어줬기때문에)

        finish();
    }
}
