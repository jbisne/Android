package com.study.android.androidproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment2 extends Fragment {
    private static final String TAG = "lecture";

    TextView textView7;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView =
                (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);

        textView7 = rootView.findViewById(R.id.textView7);

        StringBuffer buffer = new StringBuffer();

        buffer.append("♣ 문의 및 안내 : " + TourSelectedActivity.infocenter+"\n\n");
        buffer.append("♣ 개장 일 : " +TourSelectedActivity.opendate+"\n\n");
        buffer.append("♣ 쉬는 날 : " +TourSelectedActivity.restdate+"\n\n");
        buffer.append("♣ 유모차 대여 여부 : " +TourSelectedActivity.chkbabycarriage+"\n\n");
        buffer.append("♣ 신용카드 가능 여부 : " +TourSelectedActivity.chkcreditcard+"\n\n");
        buffer.append("♣ 애완동물 동반 가능 여부 : " +TourSelectedActivity.chkpet+"\n\n");
        buffer.append("♣ 주차 시설 : " +TourSelectedActivity.parking+"\n\n");

        String regex1 = "\\<.*?\\>";
        String temp = buffer.toString().replaceAll(regex1,"");

        textView7.setText(temp);


        return rootView;
    }
}
