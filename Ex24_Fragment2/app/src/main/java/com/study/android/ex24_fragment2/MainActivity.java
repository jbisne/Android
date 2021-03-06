package com.study.android.ex24_fragment2;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
    implements ImageSelectionCallback
{
    private static final String TAG = "lecture";

    ListFragment listFragment;
    ViewerFragment viewerFragment;

    int[] images = {R.drawable.desert, R.drawable.hydrangeas, R.drawable.jellyfish};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        listFragment = (ListFragment) manager.findFragmentById(R.id.listFragment);
        viewerFragment = (ViewerFragment) manager.findFragmentById(R.id.viewerFragment);
    }

    @Override
    public void onImageSelected(int position)
    {
        viewerFragment.setImage(images[position]);
    }
}
