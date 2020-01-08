package com.example.mygame;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.SupportMapFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MappingFragment extends Fragment implements OnMapReadyCallback{
    private String strLatitude, longitudeStr;
    private double dLatitude, longitudeDouble;
    private GoogleMap gMap;
    private Winner userPassed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mapping, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapp);
        mapFragment.getMapAsync(this);
    return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        LatLng location = new LatLng(dLatitude, longitudeDouble);
        gMap.addMarker(new MarkerOptions().position(location));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,16F));
    }

    public void getGameUserInfo(Winner gameUser) {
        userPassed = gameUser;
        if(gameUser.getLatitude()!= null && gameUser.getLongitude()!= null){
        strLatitude = gameUser.getLatitude();
        longitudeStr = gameUser.getLongitude();
        dLatitude =Double.parseDouble(strLatitude);
        longitudeDouble = Double.parseDouble(longitudeStr);
        onMapReady(gMap);
        }
    }
}
