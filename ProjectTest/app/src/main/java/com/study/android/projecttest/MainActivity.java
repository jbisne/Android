package com.study.android.projecttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "lecture";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnClicked(View v)
    {
        getXmlData();
        Log.d(TAG,"버튼클릭 되는중");
    }

    private void getXmlData() {

        try {
            ArrayList<String> aOBJECTID = new ArrayList<String>();
            //고유번호
            ArrayList<String> aSHUNT_NAM = new ArrayList<String>();
            //대피소명칭
            ArrayList<String> aLNG = new ArrayList<String>();
            //경도
            ArrayList<String> aLAT = new ArrayList<String>();
            //위도

            // 스택 사용 여부! 값을 바꿔서 실행해 보시고
            // <word>aaa<any>any text</any>bbb</word>에서 bbb의 결과를 확인해 보세요.
            boolean useStack = true;

            // XML 파서
            //XmlPullParser parser = getResources().getXml(R.xml.test);

            URL url = new URL("http://openapi.seoul.go.kr:8088/707a45486e6a6973373541554f4365/" +
                    "xml/GeoInfoShelterWGS/1/694");
            // 검색 URL 부분

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            // XML 처리에 사용할 변수 선언
            int event = 0;
            String currentTag = null;
            Stack<String> tagStack = new Stack<String>();

            // 파싱 시작
            while((event = parser.next()) != XmlPullParser.END_DOCUMENT) {

                switch(event) {

                    case XmlPullParser.START_TAG:
                        // 시작 태그를 만나면 currentTag에 기록
                        if(useStack && currentTag != null) {
                            tagStack.push(currentTag);
                        }
                        currentTag = parser.getName();
                        break;

                    case XmlPullParser.TEXT:
                        // currentTag가 처리하고자 하는 태그이면...
                        if(currentTag != null)
                        {
                            if (currentTag.equals("OBJECTID"))
                            {
                                String value = parser.getText();
                                aOBJECTID.add(value);
                            }
                            else if (currentTag.equals("SHUNT_NAM"))
                            {
                                String value = parser.getText();
                                aSHUNT_NAM.add(value);
                            }
                            else if (currentTag.equals("LNG"))
                            {
                                String value = parser.getText();
                                aLNG.add(value);
                            }
                            else if (currentTag.equals("LAT"))
                            {
                                String value = parser.getText();
                                aLAT.add(value);
                            }
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        // 종료 태그를 만나면 currentTag 초기화
                        if(useStack && tagStack.size() > 0) {
                            currentTag = tagStack.pop();
                        }
                        else {
                            currentTag = null;
                        }
                        break;


                    default:
                        break;
                }
            }

            for (int i = 0; i < aOBJECTID.size(); i++)
            {
                Log.d(TAG, "OBJECTID : " + aOBJECTID.get(i));
                Log.d(TAG, "SHUNT_NAM   : " + aSHUNT_NAM.get(i));
                Log.d(TAG, "LNG   : " + aLNG.get(i));
                Log.d(TAG, "LAT   : " + aLAT.get(i));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
