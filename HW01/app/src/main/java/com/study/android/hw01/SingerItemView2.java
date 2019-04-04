package com.study.android.hw01;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SingerItemView2 extends LinearLayout
{
    private static final String TAG = "lecture";

    TextView textView1;
    TextView textView2;
    ImageView imageView1;

    LayoutInflater inflater;

    public SingerItemView2(Context context)
    {
        super(context);

        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.singer_item_view_man, this, true);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        imageView1 = findViewById(R.id.imageView1);
    }

    public void setName(String name)
    {
        textView1.setText(name);
    }

    public void setTel(String telnum)
    {
        textView2.setText(telnum);
    }

    public void setImage(int imgNum) { imageView1.setImageResource(imgNum); }

}
