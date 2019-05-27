package com.study.android.teamproject;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Home extends Fragment
{
    private static final String TAG = "teamproject";

    TabLayout tabLayout;
    SaleList saleList;
    FreeList freeList;

//    @Override
//    public void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//
//        Intent intent = new Intent(this, WriteActivity.class);
//        startActivity(intent);
//    }
//    ->액티비티에서 쓰는것, 프래그먼트에선 안됨

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        final ViewGroup rootView =
                (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        Button button = rootView.findViewById(R.id.WriteButton);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(),WriteActivity.class);
                startActivity(intent);
            }
            // 데이터 넘기기없이 화면만 넘긴것!
        });

        tabLayout = rootView.findViewById(R.id.listtab);
//      tabLayout = rootView(R.id.listtab);

        saleList = new SaleList();
        freeList = new FreeList();

        getFragmentManager() // getSupportFragmentManager() 였다.
                .beginTransaction().replace(R.id.ListContainer, saleList).commit();
        // 시작할때 화면 설정

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "POS:" + tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        getFragmentManager()
                                .beginTransaction().replace(R.id.ListContainer, saleList).commit();
                        break;
                    case 1:
                        getFragmentManager()
                                .beginTransaction().replace(R.id.ListContainer, freeList).commit();
                        break;
                    default:
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab){}

            @Override
            public void onTabReselected(TabLayout.Tab tab){}
        });

        return rootView;

    }



//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState)
//    {
//        Log.d(TAG, "버튼 나누기 전 완료 로그");
//
//        super.onActivityCreated(savedInstanceState);
//        saleList = new SaleList();
//        freeList = new FreeList();
//
//        Button salelistbutton = getView().findViewById(R.id.salelistbutton);
//        salelistbutton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//
//                insertNestedFragment1();
//            }
//        });
//
//        Button freelistbutton = getView().findViewById(R.id.freelistbutton);
//        freelistbutton.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                insertNestedFragment2();
//            }
//        });
//    }

//    private void insertNestedFragment1()
//    {
//        getChildFragmentManager().beginTransaction().replace(R.id.listContainer, saleList).commit();
//    }
//
//    private void insertNestedFragment2()
//    {
//        getChildFragmentManager().beginTransaction().replace(R.id.listContainer, freeList).commit();
//    }

}
