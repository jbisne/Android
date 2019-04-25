package com.study.android.androidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MapItemView extends LinearLayout
{
    TextView textView1;
    TextView textView2;
    Button button1;

    public MapItemView(Context context)
    {
        super(context);

        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.map_item_view, this, true);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        button1 = findViewById(R.id.button1);
    }

    public void setName(String SHUNT_NAM)
    {
        textView1.setText(SHUNT_NAM);
    }

    public void setAddress(String ADR_NAM)
    {
        textView2.setText(ADR_NAM);
    }

    public void setTel(String TEL_NO_CN)
    {
        button1.setText(TEL_NO_CN);
    }
}
