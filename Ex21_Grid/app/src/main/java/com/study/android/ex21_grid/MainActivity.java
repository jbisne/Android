package com.study.android.ex21_grid;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "lecture";

    String[] names = { "슬기", "아이린", "웬디", "조이", "예리"};
    String[] ages = {"26", "29", "26", "24", "21"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MyAdapter adapter = new MyAdapter();

        GridView gridView1 = findViewById(R.id.gridView1);
        gridView1.setAdapter(adapter);
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3)
            {
                int row = position / 2;
                int column = position % 2;

                Log.d(TAG, "Row index : " + row + " Column index :" + column);
                Log.d(TAG, names[row * 2 + column]);
            }
        });
    }

    class MyAdapter extends BaseAdapter
    {
        @Override
        public int getCount() { return names.length; }

        @Override
        public Object getItem(int position)
        {
            return names[position];
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
                TextView view1 = new TextView( getApplicationContext() );
                view1.setText(names[position]);
                view1.setTextSize(40.0f);
                view1.setTextColor(Color.BLUE);

                LinearLayout layout = new LinearLayout((getApplicationContext()));
                layout.setOrientation(LinearLayout.VERTICAL);

                layout.addView(view1);

                TextView view2 = new TextView( getApplicationContext() );
                view2.setText(ages[position]);
                view2.setTextSize(40.0f);
                view2.setTextColor(Color.RED);

                layout.addView(view2);

                return layout;

        }

    }
}
