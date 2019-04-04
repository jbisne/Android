package com.study.android.ex20_list6;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new SingerAdapter(this);

        SingerItem item1 = new SingerItem("슬기", "010-1111-1111", R.drawable.sul1);
        adapter.addItem(item1);

        SingerItem item2 = new SingerItem("아이린", "010-2222-2222", R.drawable.irin1);
        adapter.addItem(item2);

        SingerItem item3 = new SingerItem("웬디", "010-3333-3333", R.drawable.wen1);
        adapter.addItem(item3);

        SingerItem item4 = new SingerItem("조이", "010-5555-5555", R.drawable.joy1);
        adapter.addItem(item4);

        SingerItem item5 = new SingerItem("예리", "010-6666-6666", R.drawable.ye1);
        adapter.addItem(item5);

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

//        public void onBtn1Clicked(View v)
//        {
//            String inputName = editText1.getText().toString();
//            String inputtelnum = editText2.getText().toString();
//
//            SingerItem item = new SingerItem(inputName, inputtelnum, R.drawable.face1);
//            adapter.addItem(item);
//            adapter.notifyDataSetChanged();
//        }
}
