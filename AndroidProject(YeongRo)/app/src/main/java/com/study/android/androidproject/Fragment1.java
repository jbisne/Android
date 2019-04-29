package com.study.android.androidproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Fragment1 extends Fragment{
    private static final String TAG = "project";
    Context context;

    ImageView imageView2;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    Button button5,button6;

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView =
                (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);

        context = container.getContext();

        imageView2 = rootView.findViewById(R.id.imageView2);
        textView4 = rootView.findViewById(R.id.textView4);
        textView5 = rootView.findViewById(R.id.textView5);
        textView6 = rootView.findViewById(R.id.textView6);

        button5 = rootView.findViewById(R.id.button5);
        button6 = rootView.findViewById(R.id.button6);

        if (TourSelectedActivity.image.equals("no Image")){
            imageView2.setImageResource(R.drawable.noimage);
        } else{
            Picasso.with(context)
                    .load(TourSelectedActivity.image)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(imageView2);
        }
        textView4.setText(TourSelectedActivity.title);
        textView5.setText(TourSelectedActivity.addr1 + ", " + TourSelectedActivity.addr2);
        textView6.setText(stripHtml(TourSelectedActivity.overview));
        textView6.setMovementMethod(new ScrollingMovementMethod());
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MapActivity.class);
                int type = 7;
                intent.putExtra("type",type);
                startActivity(intent);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MapActivity.class);
                int type = 8;
                intent.putExtra("type",type);
                startActivity(intent);
            }
        });

        return rootView;
    }

    public String stripHtml(String html) {
        return Html.fromHtml(html).toString();
    }
}
