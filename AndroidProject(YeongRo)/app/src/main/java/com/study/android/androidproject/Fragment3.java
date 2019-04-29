package com.study.android.androidproject;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Fragment3 extends Fragment {
    private static final String TAG = "lecture";

    TextView textView8;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView =
                (ViewGroup) inflater.inflate(R.layout.fragment3, container, false);

        textView8 = rootView.findViewById(R.id.textView8);

        if (TourSelectedActivity.infoname.size() > 0) {

            StringBuffer buffer = new StringBuffer();

            for(int i=0; i<TourSelectedActivity.infoname.size(); i++) {
                buffer.append("♣"+TourSelectedActivity.infoname.get(i) +": "+ TourSelectedActivity.infotext.get(i)+"\n\n");
            }

            String regex1 = "\\<.*?\\>";
            String temp = buffer.toString().replaceAll(regex1,"");

            textView8.setMovementMethod(new ScrollingMovementMethod());
            textView8.setText(temp);
        } else {
            textView8.setText("해당정보가 없습니다");
        }
        return rootView;
    }

}
