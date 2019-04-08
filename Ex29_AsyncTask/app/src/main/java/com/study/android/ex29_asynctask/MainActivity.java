// Thread 예제랑 코드 비교해보면서 뭐가 다른지 볼것.

// AsyncTask = Thread + Handler

package com.study.android.ex29_asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "lecture";

    ProgressBar mProgress1;
    int mProgressStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgress1 = findViewById(R.id.progressBar1);
    }

    public void onBtnClicked(View v)
    {
        new CounterTask().execute(0);
    }

    class CounterTask extends AsyncTask<Integer, Integer, Integer>
    {
        // <>안의 Integer는 밑에 3개에서 써줄 변수값.
        protected  void onPreExecute()
        {

        }

        protected  Integer doInBackground(Integer... value)
        {
            //백그라운드에서 어떻게 해줄지
            //우리가 정의한 AsyncTask를 execute할 때 전해줄 값의 종류
            while (mProgressStatus < 100)
            {
                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {

                }
                mProgressStatus++;
                publishProgress(mProgressStatus);
            }
            return mProgressStatus;
        }

        protected void onProgressUpdate(Integer... value)
        {
            // 진행상황을 업데이트할 때 전달할 값의 종류
            mProgress1.setProgress(mProgressStatus);
        }

        protected void onPostExecute(Integer result)
        {
            // AsyncTask가 끝난 뒤 결과값의 종류
            mProgress1.setProgress(mProgressStatus);
        }
    }
}
