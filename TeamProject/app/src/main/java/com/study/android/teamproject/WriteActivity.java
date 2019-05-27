package com.study.android.teamproject;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class WriteActivity extends AppCompatActivity
{
    private static final String TAG = "teamproject";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG ,"WriteActivity 출력");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        Button button = findViewById(R.id.BackButton);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        Log.d(TAG ,"뒤로가기");
    }

}