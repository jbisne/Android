package com.study.android.teamproject;

import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Home extends Fragment
{
    private static final String TAG = "teamproject";

    SaleList saleList;
    FreeList freeList;

    ViewPager pager1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        ViewGroup rootView =
                (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        pager1 = rootView.findViewById(R.id.pager1);
        // 기본 3개 : 다음으로 숫자 조정
        pager1.setOffscreenPageLimit(2);
        MyPagerAdapter adapter = new MyPagerAdapter();
        pager1.setAdapter(adapter);

        return rootView;

    }

    public void onBtn1Clicked(View v)
    {
        pager1.setCurrentItem(0);
    }

    public void onBtn2Clicked(View v) {
        pager1.setCurrentItem(1);
    }

    class MyPagerAdapter extends PagerAdapter
    {
        String[] names = { "트와이스♡", "레드벨벳♡", "여자친구♡" };

        @Override
        public int getCount()
        {
            return names.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj)
        {
            return view.equals(obj);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            super.destroyItem(container, position, object);

            pager1.removeView((View)object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            LinearLayout layout = new LinearLayout(getContext());
            layout.setOrientation(LinearLayout.VERTICAL);

            TextView view1 = new TextView( getContext() );
            view1.setText(names[position]);
            view1.setTextSize(40.0f);
            view1.setTextColor(Color.BLUE);

            layout.addView(view1);

            pager1.addView(layout, position);

            return  layout;
        }
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
