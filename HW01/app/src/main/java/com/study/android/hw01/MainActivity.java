package com.study.android.hw01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "lecture";

    SingerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new SingerAdapter(this);

        SingerItem item1 = new SingerItem("권다연", "010-1111-1111", R.drawable.kwondayeon, "female");
        adapter.addItem(item1);
        SingerItem item2 = new SingerItem("백지선", "010-2222-2222", R.drawable.baekjisun, "male");
        adapter.addItem(item2);
        SingerItem item3 = new SingerItem("장희준", "010-3333-3333", R.drawable.janghi, "male");
        adapter.addItem(item3);
        SingerItem item4 = new SingerItem("정의만", "010-4444-4444", R.drawable.justice, "male");
        adapter.addItem(item4);
        SingerItem item5 = new SingerItem("정수인", "010-5555-5555", R.drawable.sensei, "female");
        adapter.addItem(item5);
        SingerItem item6 = new SingerItem("지동원", "010-6666-6666", R.drawable.soccer, "male");
        adapter.addItem(item6);
        SingerItem item7 = new SingerItem("선생님", "010-7777-7777", R.drawable.teacher, "male");
        adapter.addItem(item7);


        ListView listView1 = findViewById(R.id.listView1);
        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                SingerItem item = (SingerItem) adapter.getItem(position);
                Toast.makeText(getApplicationContext(),
                        "selected : " + item.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
