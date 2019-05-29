package com.study.android.teamproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Mypage extends Fragment
{
    private static final String TAG = "teamproject";
//    여기다가 전역변수로 지저해놓으니까 APP이 아예 튕긴다!!
//    Fragment는 이러는듯? 잘 기억해둘것!
//    final String[] items = {"YES", "NO"};
//
//    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        ViewGroup rootView =
                (ViewGroup) inflater.inflate(R.layout.fragment_mypage, container, false);

        Button button1 = rootView.findViewById(R.id.modifyButton);
        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(),ModifyActivity.class);
                startActivity(intent);
            }
        });

        Button button2 = rootView.findViewById(R.id.logOutButton);
        button2.setOnClickListener(new View.OnClickListener()
        {
            final String[] items = {"예", "아니오"};

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            @Override
            public void onClick(View v)
            {

                builder.setTitle("로그아웃 하시겠습니까?");
                builder.setItems(items, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which ==0){
                            /*..YES..*/
                        }
                        else{
                            /*...NO...*/
                        }
                    }
                });
                builder.show();
            }
        });
//        final String[] items = {"YES", "NO"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

//        builder.setTitle("title");
//        builder.setItems(items, new DialogInterface.OnClickListener()
//        {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if(which ==0){
//                    /*..YES..*/
//                }
//                else{
//                    /*...NO...*/
//                }
//            }
//        });
//        builder.show();


        return rootView;
    }


}
