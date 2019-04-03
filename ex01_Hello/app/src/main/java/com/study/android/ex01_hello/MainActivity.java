package com.study.android.ex01_hello;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "lecture";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Log.d(TAG,"로그 출력");
                Toast.makeText(getApplicationContext(), "긴 토스트", Toast.LENGTH_LONG).show();
                // getApplicationContext() -> 여기에 부모를 적어줘야한다.this 적어도된다.
            }
        });
    }
    // 버튼2 :
    // Intent 만들어 웹브라우저 띄우기
    public void onButton2Clicked(View v)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.naver.com"));
        startActivity(intent);
    }

    // 버튼3 :
    // Intent 만들어 전화 걸기
    public void onButton3Clicked(View v)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:01074693800"));
        startActivity(intent);
    }

    // 버튼4 :
    // EditText에 입력한 값을 TextView에 보여 주기
    public void onButton4Clicked(View v)
    {
        EditText editText = findViewById(R.id.editText1);
        TextView textView = findViewById(R.id.textView1);

        textView.setText(editText.getText());
    }

    // 버튼5 :
    // 내가 생성한 액티비티 실행하고 결과 받아오기
    public void onButton5Clicked(View v)
    {
        Intent intent = new Intent(getApplicationContext(), NewActivity.class);
        //명시적. '쟤'불러줘.
        intent.putExtra("CustomerName", "홍길동");
        //(키, 벨류) 키는 중복되면안됨! 덮어씀
        //startActivity(intent);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "콜백 함수 호출됨");

        if (requestCode ==1 && resultCode == 10)
        {
            String sData = "";
            String str = "OnActivityResult() called : " +
                    requestCode + " : " +
                    resultCode;

            sData = data.getStringExtra("BackData");
            str = str + " : " + sData;

            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
        }
    }
}
