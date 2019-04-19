package com.study.android.project03;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;



public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "lecture";

    SupportMapFragment mapFragment;
    GoogleMap map;

    MarkerOptions myLocationMarker;

    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION))
            {

            }
            else
            {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback()
        {
            @Override
            public void onMapReady(GoogleMap googleMap)
            {
                Log.d(TAG, "GoogleMap is ready.");

                map = googleMap;

                requestMyLocation();
            }
        });

        try
        {
            MapsInitializer.initialize(this);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

////////////////////////// 구글맵 내위치 표시 ///////////////////////

        StrictMode.enableDefaults();

        //TextView check = (TextView)findViewById(R.id.result);
        // 파싱된 결과확인용. check 들어간 애들로 텍스트 출력해서 파싱이
        // 된건지 안된건지 확인할수있다.

        boolean inrow=false, inOBJECTID=false, inSHUNT_NAM=false, inADR_NAM=false;
        boolean inHOU_CNT_M=false, inHOU_CNT_C=false, inOPR_YN=false, inTEL_NO_CN=false;
        boolean inHJD_CDE=false, inHJD_NAM=false, inSHUNT_LVL=false, inREMARK=false;
        boolean inLNG=false, inLAT=false;

        String OBJECTID=null, SHUNT_NAM=null, ADR_NAM=null, HOU_CNT_M=null, HOU_CNT_C=null;
        String OPR_YN=null, TEL_NO_CN=null, HJD_CDE=null, HJD_NAM=null, SHUNT_LVL=null;
        String REMARK=null, LNG=null, LAT=null;

        try
        {
            URL url = new URL("http://openapi.seoul.go.kr:8088/707a45486e6a6973373541554f4365/" +
                    "xml/GeoInfoShelterWGS/1/694");
            // 검색 URL 부분

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            System.out.println("파싱을 시작합니다.");

            while (parserEvent != XmlPullParser.END_DOCUMENT)
            {
                switch (parserEvent)
                {
                    case XmlPullParser.START_TAG: // parser가 시작 태그를 만나면 실행
                        if(parser.getName().equals("OBJECTID"))
                        {
                            inOBJECTID = true;
                        }
                        if(parser.getName().equals("SHUNT_NAM"))
                        {
                            inSHUNT_NAM = true;
                        }
                        if(parser.getName().equals("ADR_NAM"))
                        {
                            inADR_NAM = true;
                        }
                        if(parser.getName().equals("HOU_CNT_M"))
                        {
                            inHOU_CNT_M = true;
                        }
                        if(parser.getName().equals("HOU_CNT_C"))
                        {
                            inHOU_CNT_C = true;
                        }
                        if(parser.getName().equals("OPR_YN"))
                        {
                            inOPR_YN = true;
                        }
                        if(parser.getName().equals("TEL_NO_CN"))
                        {
                            inTEL_NO_CN = true;
                        }
                        if(parser.getName().equals("HJD_CDE"))
                        {
                            inHJD_CDE = true;
                        }
                        if(parser.getName().equals("HJD_NAM"))
                        {
                            inHJD_NAM = true;
                        }
                        if(parser.getName().equals("SHUNT_LVL"))
                        {
                            inSHUNT_LVL = true;
                        }
                        if(parser.getName().equals("REMARK"))
                        {
                            inREMARK = true;
                        }
                        if(parser.getName().equals("LNG"))
                        {
                            inLNG = true;
                        }
                        if(parser.getName().equals("LAT"))
                        {
                            inLAT = true;
                        }
//                        if(parser.getName().equals("message"))
//                        {
//                            //message 태그를 만나면 에러 출력
//                            check.setText(check.getText()+"에러");
//                            // 여기에 에러코드에 따라 다른 메세지를 출력하도록 할 수 있다.
//                        }
                        break;

                    case XmlPullParser.TEXT: // parser가 내용에 접근했을때
                        if(inOBJECTID)
                        {
                            // isTitle이 true일 때 태그의 내용을 저장
                            OBJECTID = parser.getText();
                            inOBJECTID = false;
                        }
                        if(inSHUNT_NAM)
                        {
                            // isTitle이 true일 때 태그의 내용을 저장
                            SHUNT_NAM = parser.getText();
                            inSHUNT_NAM = false;
                        }
                        if(inADR_NAM)
                        {
                            // isTitle이 true일 때 태그의 내용을 저장
                            ADR_NAM = parser.getText();
                            inADR_NAM = false;
                        }
                        if(inHOU_CNT_M)
                        {
                            // isTitle이 true일 때 태그의 내용을 저장
                            HOU_CNT_M = parser.getText();
                            inHOU_CNT_M = false;
                        }
                        if(inHOU_CNT_C)
                        {
                            // isTitle이 true일 때 태그의 내용을 저장
                            HOU_CNT_C = parser.getText();
                            inHOU_CNT_C = false;
                        }
                        if(inOPR_YN)
                        {
                            // isTitle이 true일 때 태그의 내용을 저장
                            OPR_YN = parser.getText();
                            inOPR_YN = false;
                        }
                        if(inTEL_NO_CN)
                        {
                            // isTitle이 true일 때 태그의 내용을 저장
                            TEL_NO_CN = parser.getText();
                            inTEL_NO_CN = false;
                        }
                        if(inHJD_CDE)
                        {
                            // isTitle이 true일 때 태그의 내용을 저장
                            HJD_CDE = parser.getText();
                            inHJD_CDE = false;
                        }
                        if(inHJD_NAM)
                        {
                            // isTitle이 true일 때 태그의 내용을 저장
                            HJD_NAM = parser.getText();
                            inHJD_NAM = false;
                        }
                        if(inSHUNT_LVL)
                        {
                            // isTitle이 true일 때 태그의 내용을 저장
                            SHUNT_LVL = parser.getText();
                            inSHUNT_LVL = false;
                        }
                        if(inREMARK)
                        {
                            // isTitle이 true일 때 태그의 내용을 저장
                            REMARK = parser.getText();
                            inREMARK = false;
                        }
                        if(inLNG)
                        {
                            // isTitle이 true일 때 태그의 내용을 저장
                            LNG = parser.getText();
                            inLNG = false;
                        }
                        if(inLAT)
                        {
                            // isTitle이 true일 때 태그의 내용을 저장
                            LAT = parser.getText();
                            inLAT = false;
                        }
                        break;
//                    case XmlPullParser.END_TAG:
//                        if(parser.getName().equals("row"))
//                        {
//                            check.setText(check.getText()+"고유번호 : "+OBJECTID + "\n대피소명칭 : "+SHUNT_NAM + "\n소재지 : "+ADR_NAM+
//                                    "\n최대수용인원 : " +HOU_CNT_M+ "\n현재수용인원 : " +HOU_CNT_C+ "\n현재운영여부" +OPR_YN+ "\n전화번호 : " +TEL_NO_CN+
//                                    "\n행정동코드 : " +HJD_CDE+ "\n행정동명칭 : " +HJD_NAM+ "\n대피단계 : " +SHUNT_LVL+ "\n비고 : "+REMARK+
//                                    "\n경도 : " +LNG+ "\n위도 : " +LAT+"\n");
//                            inrow = false;
//                        }
//                        break;
                }
                parserEvent = parser.next();
           }
        }
        catch (Exception e)
        {
            //check.setText("에러가 났습니다!");
            e.printStackTrace();
        }

    }
//============== onCreate메서드 + 데이터 파싱 ====================

    private void requestMyLocation()
    {
        LocationManager manager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try
        {
            long minTime = 10000;
            float minDistance = 0;
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener()
                    {
                        @Override
                        public void onLocationChanged(Location location)
                        {
                            Log.d(TAG, "onLocationChanged");
                            showCurrentLocation(location);
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras)
                        {

                        }

                        @Override
                        public void onProviderEnabled(String provider)
                        {

                        }

                        @Override
                        public void onProviderDisabled(String provider)
                        {

                        }
                    }
            );

            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null)
            {
                showCurrentLocation(lastLocation);
            }

            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener()
                    {
                        @Override
                        public void onLocationChanged(Location location)
                        {
                            showCurrentLocation(location);
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras)
                        {

                        }

                        @Override
                        public void onProviderEnabled(String provider)
                        {

                        }

                        @Override
                        public void onProviderDisabled(String provider)
                        {

                        }
                    }
            );
        }
        catch(SecurityException e)
        {
            e.printStackTrace();
        }
    }

    private void showCurrentLocation(Location location)
    {
        LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));

        showMyLocationMarker(location);
    }

    private void showMyLocationMarker(Location location)
    {
        if (myLocationMarker == null)
        {
            myLocationMarker = new MarkerOptions();
            myLocationMarker.position(new LatLng(location.getLatitude(), location.getLongitude()));
            myLocationMarker.title("***내 위치 ***\n");
            myLocationMarker.snippet("GPS로 확인한 위치");
            myLocationMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation));
            map.addMarker(myLocationMarker);
        }
        else
        {
            myLocationMarker.position(new LatLng(location.getLatitude(), location.getLongitude()));
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        if (map != null)
        {
            map.setMyLocationEnabled(false);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if (map != null)
        {
            map.setMyLocationEnabled(true);
        }
    }
    //====================================================


}
