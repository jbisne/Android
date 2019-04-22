package com.study.android.androidproject2;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // 과거에는 GoogleMap 객체를 얻어오기 위해
        // mapFragment.getMap() 메소드를 사용했으나
        // 현재 getMap() 은 deprecate 되었습니다.
        // 지금은 위와 같이 getMapAsync() 를 하고
        // 아래의 OnMapReady() 에서 처리를 GoogleMap 객체관련
        // 처리를 합니다.

    }

    /**
     * OnMapReady 는 map이 사용가능하면 호출되는 콜백 메소드입니다
     * 여기서 marker 나 line, listener, camera 이동 등을 설정해두면 됩니다.
     * 이번 예제에서는 서울역 근처에 marker를 더하고 적절한 title과, zoom을 설정해둡니다
     * 이 시점에서, 만약 사용자 기기게 Google Play service가 설치되지 않으면
     * 설치하라고 메세지가 뜨게 되고,  설치후에 다시 이 앱으로 제어권이 넘어옵니다
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        // ↑매개변수로 GoogleMap 객체가 넘어옵니다.


    }
}
