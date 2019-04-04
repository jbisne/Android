package com.study.android.hw01;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SingerItemView extends LinearLayout
{
    private static final String TAG = "lecture";

    TextView textView1;
    TextView textView2;
    ImageView imageView1;

    LayoutInflater inflater;

    public SingerItemView(Context context)
    {
        super(context);

        inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setName(String name)
    {
        textView1.setText(name);
    }

    public void setTel(String telnum)
    {
        textView2.setText(telnum);
    }

    public void setImage(int imgNum)
    {
       Log.d(TAG, "imgNum : " + imgNum);
       if(imgNum == 0)
       {
           imgNum = R.drawable.sul1;
       }
       imageView1.setImageResource(imgNum);
    }

    public void layout1()
    {
        inflater.inflate(R.layout.singer_item_view_man, this, true);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        imageView1 = findViewById(R.id.imageView1);
    }

    public void layout2()
    {
        inflater.inflate(R.layout.singer_item_view_woman, this, true);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        imageView1 = findViewById(R.id.imageView1);
    }
}
