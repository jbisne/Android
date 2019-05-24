package com.study.android.teamproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class SaleAdapter extends BaseAdapter
{
    Context context;
    ArrayList<SaleItem> items = new ArrayList<>();

    public SaleAdapter(Context context)
    {
        this.context = context;
    }

    public void addItem(SaleItem item)
    {
        items.add(item);
    }

    @Override
    public int getCount()
    {
        return items.size();
    }

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
        // SingerItemView view = new SingerItemView(context);

        SaleItemView saleItemView = null;
        if(convertView == null)
        {
            saleItemView = new SaleItemView(context);
        }
        else
        {
            saleItemView = (SaleItemView) convertView;
        }

        SaleItem item = items.get(position);
        saleItemView.setName(item.getName());
        saleItemView.setAge(item.getAge());
        saleItemView.setImage(item.getResId());

        return saleItemView;
    }
}
