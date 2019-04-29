package com.study.android.androidproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {
    private static final String TAG = "project";

    double myMapx;
    double myMapy;

    SupportMapFragment mapFragment;
    GoogleMap map;
    TextView textView9;

    MarkerOptions tourLocationMarker1;
    MarkerOptions myLocationMarker2;
    MarkerOptions nearTourLocationMarker;

    LatLng tourPoint;
    LatLng curPoint;

    Double mapx1=0.0d;
    Double mapy1=0.0d;

    Double mapx2=0.0d;
    Double mapy2=0.0d;
    private int type=0;

    ArrayList<TourDTOParcelable> mdtos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

        textView9 = findViewById(R.id.textView9);
        Intent intent = getIntent();
        type = intent.getIntExtra("type",1);


        if (type == 10) { //마커찍기
            mapx2 =intent.getDoubleExtra("mymapx",0);
            mapy2 = intent.getDoubleExtra("mymapy",0);

            mdtos = intent.getParcelableArrayListExtra("mdtos");
            Log.d(TAG, "mapmdtos"+mdtos.size());

        }

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Log.d(TAG, "GoogleMap is ready.");

                map = googleMap;

                if (type == 7){
                    requestTourLocation();
                } else if (type == 8){
                    requestTourLocation();
                    requestMyLocation();
                } else if (type == 9) {
                    requestMyLocation();
                } else if (type == 10) {
                    requestTourLocation();
                }
            }
        });
        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestTourLocation() {
        LocationManager manager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            long minTime = 10000;
            float minDistance = 0;
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location locationA) {
                            showCurrentTourLocation(locationA);
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {
                        }

                        @Override
                        public void onProviderEnabled(String provider) {
                        }

                        @Override
                        public void onProviderDisabled(String provider) {
                        }
                    }
            );

            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                showCurrentTourLocation(lastLocation);
            }
            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location locationA) {
                            showCurrentTourLocation(locationA);
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    }
            );
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void requestMyLocation() {
        LocationManager manager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            long minTime = 10000;
            float minDistance = 0;
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location locationB) {
                            Log.d(TAG, "onLocationChanged");
                            showMyCurrentLocation(locationB);
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    }
            );

            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                showMyCurrentLocation(lastLocation);

            }
            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location locationB) {
                            showMyCurrentLocation(locationB);

                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    }
            );
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void showCurrentTourLocation(Location locationA) {

        if (type != 10) {
            mapx1 = Double.parseDouble(TourSelectedActivity.mapx);
            mapy1 = Double.parseDouble(TourSelectedActivity.mapy);
            tourPoint = new LatLng(mapy1, mapx1);

            if (type != 8) {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(tourPoint, 15));
            }
        }
        showTourLocationMarker(locationA);
    }

    private void showMyCurrentLocation(Location locationB) {

        mapx2 = locationB.getLongitude();
        mapy2 = locationB.getLatitude();

        if (type == 9) {

            Intent resultIntent = new Intent(getApplicationContext(),MainActivity.class);
            resultIntent.putExtra("myMapx",mapx2);
            resultIntent.putExtra("myMapy",mapy2);
            setResult(200,resultIntent);
            finish();
        }
        else {
            curPoint = new LatLng(locationB.getLatitude(), locationB.getLongitude());
            distanceTo(mapx2, mapy2);
            showMyLocationMarker(locationB);
        }
    }

    private void showTourLocationMarker(Location locationA) {


        if (tourLocationMarker1 == null && type != 10 ) {
            mapx1 = Double.parseDouble(TourSelectedActivity.mapx);
            mapy1 = Double.parseDouble(TourSelectedActivity.mapy);
            // 여기를 파싱을 메인액티비티(맵)에서 햇으면 변수설정
            // 어떻게 해줘야함?

            tourLocationMarker1 = new MarkerOptions();
            tourLocationMarker1.position(new LatLng(mapy1, mapx1));
            tourLocationMarker1.title("관광지 위치\n");
            tourLocationMarker1.snippet(TourSelectedActivity.title);
            tourLocationMarker1.icon(BitmapDescriptorFactory.fromResource(R.drawable.tourlocation));
            map.addMarker(tourLocationMarker1);
        } else if (type == 10){

            myLocationMarker2 = new MarkerOptions();
            myLocationMarker2.position(new LatLng(mapy2, mapx2));
            myLocationMarker2.title("내위치\n");
            myLocationMarker2.snippet("GPS로 확인한 위치");
            myLocationMarker2.icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation));
            map.addMarker(myLocationMarker2);

            for (int i=0; i < mdtos.size(); i++){

                double x = Double.parseDouble(mdtos.get(i).getMapx());
                double y = Double.parseDouble(mdtos.get(i).getMapy());

                nearTourLocationMarker = new MarkerOptions();
                nearTourLocationMarker.position(new LatLng(y,x));
                nearTourLocationMarker.title(mdtos.get(i).getTitle()+"\n");
                nearTourLocationMarker.snippet(mdtos.get(i).getAddr1());
                //주소도 넣기
                nearTourLocationMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.tourlocation));
                map.addMarker(nearTourLocationMarker);


            }
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mapy2,mapx2), 13));
        }
        else {
            tourLocationMarker1.position(new LatLng(mapy1, mapx1));
        }
    }

    private void showMyLocationMarker(Location locationB) {
        if (myLocationMarker2 == null) {
            myLocationMarker2 = new MarkerOptions();
            myLocationMarker2.position(new LatLng(locationB.getLatitude(), locationB.getLongitude()));
            myLocationMarker2.title("내위치\n");
            myLocationMarker2.snippet("GPS로 확인한 위치");
            myLocationMarker2.icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation));
            map.addMarker(myLocationMarker2);
        }
        else {
            myLocationMarker2.position(new LatLng(locationB.getLatitude(), locationB.getLongitude()));
        }
    }

    public void distanceTo(double mapx2, double mapy2){

        mapx1 = Double.parseDouble(TourSelectedActivity.mapx);
        mapy1 = Double.parseDouble(TourSelectedActivity.mapy);

        Location tour = new Location("tour point");
        tour.setLatitude(mapy1);
        tour.setLongitude(mapx1);

        Location cur = new Location("my point");
        cur.setLatitude(mapy2);
        cur.setLongitude(mapx2);

        Location middle = new Location("middle point");
        middle.setLatitude((mapy1+mapy2)/2);
        middle.setLongitude((mapx1+mapx2)/2);

        double temp = tour.distanceTo(cur);
        int distance = (int)((Math.round(temp)) / 1000);

        LatLng mPoint = new LatLng(middle.getLatitude(), middle.getLongitude());

        if (distance > 30){
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(mPoint, 9));
        } else{
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(mPoint, 13));
        }

        textView9.setVisibility(View.VISIBLE);
        textView9.setText("현재나의 위치와 관광지까지의 거리는\n 약 : " + distance +"km 입니다.");
    }


    @Override
    protected void onPause() {
        super.onPause();

        if (map != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            map.setMyLocationEnabled(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (map != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            map.setMyLocationEnabled(true);
        }
    }
}
