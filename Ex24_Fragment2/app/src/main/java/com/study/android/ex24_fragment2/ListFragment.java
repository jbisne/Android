package com.study.android.ex24_fragment2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ListFragment extends Fragment
{
    private static final String TAG = "lecture";

    String[] values = {"첫번째 이미지", "두번째 이미지", "세번째 이미지"};

    public ImageSelectionCallback callback;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if (context instanceof ImageSelectionCallback)
        {
            callback = (ImageSelectionCallback) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        ViewGroup rootView =
                (ViewGroup) inflater.inflate(R.layout.fragment_list, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (getContext(), android.R.layout.simple_list_item_1,values);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (callback != null) {
                    callback.onImageSelected(position);
                }
            }
        });

        return rootView;
    }
}
//  onAttach
//  내 프래그먼트가 부모 뷰에 부착이 될 때(onAttach) 부모뷰를 나타내는
// 객체변수(context)를 가져와서 내 변수(callback)에 할당한다.
//  callback을 이용해서 부모의 기능을 호출할 수 있게 된다.
