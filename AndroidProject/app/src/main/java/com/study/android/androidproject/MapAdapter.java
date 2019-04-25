package com.study.android.androidproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class MapAdapter extends BaseAdapter
{
    private static final String TAG = "lecture";

    Context context;
    ArrayList<MapItem> items = new ArrayList<>();

    public MapAdapter(Context context)
    {
        this.context = context;
    }

    public void addItem(MapItem item)
    {
        items.add(item);
    }

    @Override
    public int getCount(){ return items.size(); }

    @Override
    public Object getItem(int position)
    {
        return items.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //    MapItemView view = new MapItemView(context);

        MapItemView view = null;
        if(convertView == null)
        {
            view = new MapItemView(context);
        }
        else
        {
            view = (MapItemView) convertView;
        }

        final MapItem item = items.get(position);
        view.setName(item.getSHUNT_NAM());
        view.setAddress(item.getADR_NAM());
        view.setTel(item.getTEL_NO_CN());

        // 리스트뷰 안의 버튼 클릭 이벤트 처리
        Button button1 = view.findViewById(R.id.button1);
        button1.setFocusable(false);
        button1.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String str = "tel:" + item.getTEL_NO_CN();

                Log.d(TAG, str);

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(str));
                context.startActivity(intent);
            }
        });
        // Toast.makeText(context, "getView() called : " +position, Toast.LENGTH_SHORT).show();

        return view;
    }
}
