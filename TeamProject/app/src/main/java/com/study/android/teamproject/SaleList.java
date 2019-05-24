package com.study.android.teamproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SaleList extends Fragment
{
    private static final String TAG = "teamproject";

//    String[] names = { "슬기", "아이린", "웬디", "조이", "예리"};
//    String[] ages = {"26", "29", "26", "24", "21"};
//    int[] images = { R.drawable.face1, R.drawable.face1, R.drawable.face1,
//            R.drawable.face1, R.drawable.face1};

    //static final String[] LIST_MENU = {"LIST1", "LIST2", "LIST3"} ;

    // 기본 LIST 샘플
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
//    {
//        Log.d(TAG,"판매 리스트 출력");
//
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_salelist, null) ;
//
//        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, LIST_MENU) ;
//
//        ListView listview = (ListView) view.findViewById(R.id.salelist) ;
//        listview.setAdapter(adapter) ;
//
//        return view;
//    }

    SaleAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(TAG,"판매 리스트 출력");

        adapter = new SaleAdapter(getContext());
        // 여기! new SingerAdapter(this);인데 getContext()넣으니까 된다!

        SaleItem item1 = new SaleItem("모모", "24", R.drawable.momo);
        adapter.addItem(item1);

        SaleItem item2 = new SaleItem("슬기", "24", R.drawable.sul1);
        adapter.addItem(item2);

        View view = inflater.inflate(R.layout.fragment_salelist, null) ;

        //final MyAdapter adapter= new MyAdapter();

        ListView listview = (ListView) view.findViewById(R.id.salelist) ;
        listview.setAdapter(adapter) ;

        return view;
    }

//    class MyAdapter extends BaseAdapter
//    {
//        ArrayList<SaleItem> items = new ArrayList<>();
//
//        public void addItem(SaleItem item)
//        {
//            items.add(item);
//        }
//
//        @Override
//        public int getCount() { return items.size(); }
//
//        @Override
//        public Object getItem(int position)
//        {
//            return items.get(position);
//        }
//
//        @Override
//        public long getItemId(int position)
//        {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent)
//        {
//
//            SaleItemView saleItemView = new SaleItemView(getActivity());
//            // 여기! getApplicationContext() -> getActivity 로 변경하니 됨!
//
//            SaleItem item = items.get(position);
//            saleItemView.setName(item.getName());
//            saleItemView.setAge(item.getAge());
//            saleItemView.setImage(item.getResId());
//
//            return saleItemView;
//        }
//    }
}


