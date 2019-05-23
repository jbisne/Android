package com.study.android.teamproject;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Home extends Fragment
{
    private static final String TAG = "teamproject";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        ViewGroup rootView =
                (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        return rootView;
    }

    SaleList saleList;
    FreeList freeList;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        Log.d(TAG, "버튼 나누기 전 완료 로그");

        super.onActivityCreated(savedInstanceState);
        saleList = new SaleList();
        freeList = new FreeList();

        Button salelistbutton = getView().findViewById(R.id.salelistbutton);
        salelistbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                insertNestedFragment1();
            }
        });

        Button freelistbutton = getView().findViewById(R.id.freelistbutton);
        freelistbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                insertNestedFragment2();
            }
        });
    }

    private void insertNestedFragment1()
    {
        getChildFragmentManager().beginTransaction().replace(R.id.listContainer, saleList).commit();
    }

    private void insertNestedFragment2()
    {
        getChildFragmentManager().beginTransaction().replace(R.id.listContainer, freeList).commit();
    }

}
