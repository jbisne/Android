package com.study.android.androidproject;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;

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
import java.util.List;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback
{
    private static final String TAG = "lecture";

    private static final int THREAD_ID = 10000;

    // 구글 맵 참조변수 생성
    GoogleMap mMap;

    MarkerOptions myLocationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.enableDefaults();

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

        // SupportMapFragment을 통해 레이아웃에 만든 fragment의 ID를 참조하고 구글맵을 호출한다.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this); // getMapAsync must be called on the main thread.

     ////////////////// 구글맵 호출 ///////////////////////////

        StrictMode.enableDefaults();

        TextView check = (TextView)findViewById(R.id.result);
        //파싱된 결과확인용. check 들어간 애들로 텍스트 출력해서 파싱이
        //된건지 안된건지 확인할수있다.

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
                        if(parser.getName().equals("message"))
                        {
                            //message 태그를 만나면 에러 출력
                            check.setText(check.getText()+"에러");
                            // 여기에 에러코드에 따라 다른 메세지를 출력하도록 할 수 있다.
                        }
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

// 여기의 주석 열고닫고를통해 파싱된데이터 확인가능
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("row"))
                        {
//                            check.setText(check.getText()+"고유번호 : "+OBJECTID + "\n대피소명칭 : "+SHUNT_NAM + "\n소재지 : "+ADR_NAM+
//                                    "\n최대수용인원 : " +HOU_CNT_M+ "\n현재수용인원 : " +HOU_CNT_C+ "\n현재운영여부" +OPR_YN+ "\n전화번호 : " +TEL_NO_CN+
//                                    "\n행정동코드 : " +HJD_CDE+ "\n행정동명칭 : " +HJD_NAM+ "\n대피단계 : " +SHUNT_LVL+ "\n비고 : "+REMARK+
//                                    "\n경도 : " +LNG+ "\n위도 : " +LAT+"\n");
//                            inrow = false;
                        }
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


    ///////////////////// onCreate ///////////////////////////

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        Log.d(TAG, "GoogleMap is ready.");

        // 구글 맵 객체를 불러온다.
        mMap = googleMap;

        requestMyLocation();
        // 내 위치 표시

        // for loop를 통한 n개의 마커 생성
//        for (int i=0; i< ; i++)
//        {
//             //1. 마커 옵션 설정 (만드는 과정)
//            MarkerOptions markerOptions = new MarkerOptions();
//            markerOptions   // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
//               .position(,)
//               .title("ㅗ");
//
//             //2. 마커 생성 (마커를 나타냄)
//            mMap.addMarker(markerOptions);
//        }

//        ArrayList<MapItem> mapItems = new ArrayList<MapItem>();
//        for (int i=0; i< mapItems.size(); i++)
//        {
//            MarkerOptions markerOptions = new MarkerOptions();
//            Log.d(TAG,"출력 확인");
//            //1. 마커 옵션 설정 (만드는 과정)
//            markerOptions   // LatLng에 대한 어리에를 만들어서 이용할 수도 있다.
//                    .position(new LatLng(mapItems.get(i).getLAT(), mapItems.get(i).getLNG()))
//                    .title(mapItems.get(i).getSHUNT_NAM());
//
//            //2. 마커 생성 (마커를 나타냄)
//            mMap.addMarker(markerOptions);
//        }


//        ArrayList 시험삼아 해본것.
//        ArrayList<MapItem> mapItems = new ArrayList<MapItem>();
//
//        MarkerOptions markerOptions = new MarkerOptions();
//
//        for (int i = 0; i < mapItems.size(); i++)
//        {
//            markerOptions.position(new LatLng(mapItems.get(i).getLNG(), mapItems.get(i).getLAT()));
//            markerOptions.title(mapItems.get(i).getSHUNT_NAM());
//            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation));
//            Log.d(TAG,"출력 확인");
//        }

        try
        {
            MapsInitializer.initialize(this);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    /////////// onMapReady()로 내 위치 표시 /////////////

// 간단하게 위치마킹하는 예제
//        // 서울에 대한 위치 설정
//        LatLng seoul = new LatLng(37.4786513, 126.8790874);
//
//        // 구글 맵에 표시할 마커에 대한 옵션 설정
//        MarkerOptions makerOptions = new MarkerOptions();
//        makerOptions
//                .position(seoul)
//                .title("원하는 위치(위도, 경도)에 마커를 표시했습니다.");
//
//        // 마커를 생성한다.
//        mMap.addMarker(makerOptions);
//
//        //카메라를 서울 위치로 옮긴다.
//        //mMap.moveCamera(CameraUpdateFactory.newLatLng(seoul));
//
//        //처음 줌 레벨 설정 (해당좌표=>서울, 줌레벨(16)을 매개변수로 넣으면 된다.)
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul,16));


    }

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

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));

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
            mMap.addMarker(myLocationMarker);
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

        if (mMap != null)
        {
            mMap.setMyLocationEnabled(false);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if (mMap != null)
        {
            mMap.setMyLocationEnabled(true);
        }
    }
}