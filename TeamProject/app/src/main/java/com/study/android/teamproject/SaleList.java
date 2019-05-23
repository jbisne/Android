package com.study.android.teamproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SaleList extends Fragment
{
    private static final String TAG = "teamproject";

    static final String[] LIST_MENU = {"LIST1", "LIST2", "LIST3"} ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_salelist, null) ;

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, LIST_MENU) ;

        ListView listview = (ListView) view.findViewById(R.id.salelist) ;
        listview.setAdapter(adapter) ;

        return view ;
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater,
//                             ViewGroup container,
//                             Bundle savedInstanceState)
//    {
//        ViewGroup rootView =
//                (ViewGroup) inflater.inflate(R.layout.fragment_salelist, container, false);
//
//        return rootView;
//    }

}