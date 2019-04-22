package com.study.android.project03;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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

import android.support.v4.app.FragmentManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;



public class MainActivity extends FragmentActivity
{
    private static final String TAG = "lecture";

    SupportMapFragment mapFragment;
    //GoogleMap map; //내위치찾을떄 쓰던 변수

    MarkerOptions myLocationMarker;

    private GoogleMap mMap;  // 대피소들 위치 구하는 변수
    private GoogleMap map;  // 내 현재 위치 구하는 변수

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setUpMapIfNeeded();

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
                Log.d(TAG, "현재 내위치 구글맵");

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
    }
    ////////////////////// onCreate ////////////////////

    private void setUpMapIfNeeded()
    {
        if(mMap == null)
        {
            ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMapAsync(new OnMapReadyCallback()
                    {
                        @Override
                        public void onMapReady(GoogleMap googleMap)
                        {
                            Log.d(TAG, "대피소위치 구글맵");

                            mMap = googleMap;

                        }
                    });
            if (mMap != null)
            {
                setUpMap();
            }
        }
    }

    private void setUpMap()
    {
        PharmParser parser = new PharmParser();
        ArrayList<PharmDTO> list = null;
        try
        {
            list = parser.apiParserSearch();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        for(PharmDTO entity : list)
        {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(entity.getLNG(), entity.getLAT()));

            markerOptions.title(entity.getName());
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(R.drawable.mylocation));

            mMap.addMarker(markerOptions);
            Log.d(TAG,"파싱데이터 마킹 완료 로그");
        }
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

        if (map != null)
        {
            map.setMyLocationEnabled(false);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setUpMapIfNeeded();

        if (map != null)
        {
            map.setMyLocationEnabled(true);
        }
    }
    //====================================================
}
