package com.study.android.androidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    Context context;
    ArrayList<TourDTO> dtos;


    public ListViewAdapter(Context context,ArrayList<TourDTO> dtos) {

        this.dtos = dtos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dtos.size();
    }

    @Override
    public Object getItem(int position) {
        return dtos.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int pos = position;
        Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_view_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView ImageView1=  convertView.findViewById(R.id.imageView1) ;
        TextView titleTextView =  convertView.findViewById(R.id.textView1) ;
        TextView addrTextView = convertView.findViewById(R.id.textView2) ;

        // 아이템 내 각 위젯에 데이터 반영
        String temp = dtos.get(position).getImage();

        if (temp.equals("no Image")){
            ImageView1.setImageResource(R.drawable.noimage);
        } else{
            Picasso.with(context)
                    .load(temp)
                    .placeholder(R.drawable.popup_reflesh)
                    .into(ImageView1);
        }
        titleTextView.setText(dtos.get(position).getTitle());
        addrTextView.setText(dtos.get(position).getAddr1());


        return convertView;
    }
}