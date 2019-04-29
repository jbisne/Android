package com.study.android.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class TourSelectedActivity extends AppCompatActivity  {
    private static String TAG = "project";

    ViewPager viewPager;
    TabLayout tabLayout;

    static String title;
    static String addr1;
    static String addr2;
    static String mapx;
    static String mapy;
    static String overview;
    static String image;
    static ArrayList<String> infoname = null;
    static ArrayList<String> infotext = null;

    static String chkbabycarriage;
    static String chkcreditcard;
    static String chkpet;
    static String infocenter;
    static String restdate;
    static String opendate;
    static String parking;

    String contentTypeId;
    String contentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_selected);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        addr1 = intent.getStringExtra("addr1");
        addr2 = intent.getStringExtra("addr2");
        mapx = intent.getStringExtra("mapx");
        mapy = intent.getStringExtra("mapy");
        overview = intent.getStringExtra("overview");
        image = intent.getStringExtra("image");

        contentTypeId = intent.getStringExtra("contentTypeId");
        contentId = intent.getStringExtra("contentId");

        infoname = intent.getStringArrayListExtra("infoname");
        infotext = intent.getStringArrayListExtra("infotext");

        viewPager = findViewById(R.id.container);
        tabLayout = findViewById(R.id.tabMenu);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getXmlData(contentTypeId, contentId);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PageAdapter adapter = new PageAdapter(
                                getSupportFragmentManager(), tabLayout.getTabCount());
                        viewPager.setAdapter(adapter);
                        viewPager.addOnPageChangeListener(
                                new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

                        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                            @Override
                            public void onTabSelected(TabLayout.Tab tab) {
                                viewPager.setCurrentItem(tab.getPosition());
                            }

                            @Override
                            public void onTabUnselected(TabLayout.Tab tab) {

                            }

                            @Override
                            public void onTabReselected(TabLayout.Tab tab) {

                            }
                        });
                    }
                });
            }
        }).start();

    }

    public void getXmlData(String contentTypeId, String contentId) {

        try{

            String queryUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro?ServiceKey="+MainActivity.key+"&contentTypeId="+ contentTypeId +"&contentId="+contentId+"&MobileOS=AND&MobileApp=TourAPI3.0_Guide&introYN=Y";

            URL url = new URL(queryUrl);
            InputStream is = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));

            int parserEvent = xpp.getEventType();


            outer:
            while (parserEvent != XmlPullParser.END_DOCUMENT) {

                if (parserEvent == XmlPullParser.START_DOCUMENT) {
                    //XML 파싱 시작
                } else if (parserEvent == XmlPullParser.START_TAG) {
                    String startTag = xpp.getName();

                    if (startTag.equals("item")) ;

                    else if (startTag.equals("chkbabycarriage")) {
                        xpp.next();
                        String temp = xpp.getText();
                        chkbabycarriage=temp;
                    }
                    else if (startTag.equals("chkcreditcard")) {
                        xpp.next();
                        String temp = xpp.getText();
                        chkcreditcard=temp;
                    }
                    else if (startTag.equals("chkpet")) {
                        xpp.next();
                        String temp = xpp.getText();
                        chkpet=temp;
                    }
                    else if (startTag.equals("infocenter")) {
                        xpp.next();
                        String temp = xpp.getText();
                        infocenter=temp;
                    }
                    else if (startTag.equals("opendate")) {
                        xpp.next();
                        String temp = xpp.getText();
                        opendate=temp;
                    }
                    else if (startTag.equals("parking")) {
                        xpp.next();
                        String temp = xpp.getText();
                        parking=temp;
                    }
                    else if (startTag.equals("restdate")) {
                        xpp.next();
                        String temp = xpp.getText();
                        restdate=temp;
                    }
                    else {

                    }

                } else if (parserEvent == XmlPullParser.END_TAG) {
                    String endTag = xpp.getName();
                    if (endTag.equals("item")) {
                        if (chkbabycarriage == null){ chkbabycarriage ="정보 없음"; }
                        if (chkcreditcard == null){ chkcreditcard ="정보 없음"; }
                        if (chkpet == null){ chkpet ="정보 없음"; }
                        if (opendate == null){ opendate ="정보 없음"; }
                        if (parking == null){ parking ="정보 없음"; }
                        if (restdate == null){ restdate ="정보 없음"; }
                    }
                }
                parserEvent = xpp.next();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
