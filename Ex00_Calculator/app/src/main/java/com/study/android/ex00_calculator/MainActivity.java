package com.study.android.ex00_calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private static String num1 = "";
    private static String num2 = "";
    private static float result = 0;

    EditText etNum1;
    EditText etNum2;

    Button btnSum;
    Button btnSub;
    Button btnMul;
    Button btnDiv;
    Button btnMod;

    TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNum1 = (EditText) findViewById(R.id.etNum1);
        etNum2 = (EditText) findViewById(R.id.etNum2);

        btnSum = (Button) findViewById(R.id.Sum);
        //btnSub = (Button) findViewById(R.id.Sub);
        //btnMul = (Button) findViewById(R.id.Mul);
        //btnDiv = (Button) findViewById(R.id.Div);
        //btnMod = (Button) findViewById(R.id.Mod);

        textResult = (TextView) findViewById(R.id.tvResult);
    }

    public void BtnsumClicked(View v)
    {
        num1 = etNum1.getText().toString();
        num2 = etNum2.getText().toString();

        result = Float.parseFloat(num1) + Float.parseFloat(num2);
        textResult.setText("계산 결과 : " + result);
    }
}
