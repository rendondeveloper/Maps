package com.example.rendondev.maps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GPSTracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        init();
        mapFragment.getMapAsync(this);
    }

    private void init()
    {
        tracker = new GPSTracker(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng locationCurrent;

        mMap.setMyLocationEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            mMap.getUiSettings().setScrollGesturesEnabled(true);
        }

        if(tracker.canGetLocation())
        {
            locationCurrent = new LatLng(tracker.getLatitude(), tracker.getLongitude());
        }
        else
        {
            tracker.showSettingsAlert();
            locationCurrent = new LatLng(-34, 151);
        }

        final CameraPosition MEX = new CameraPosition.Builder().target(locationCurrent).zoom(15.0f).build();
        final CameraUpdate update = CameraUpdateFactory.newCameraPosition(MEX);
        mMap.moveCamera(update);

        final List<LatLng> list = geList();
        for (LatLng item:
        list) {
           mMap.addMarker(new MarkerOptions().position(item).title("Concesionaria").snippet("Bajaj SA de CV").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }

        poligono() ;
        //mMap.addMarker(new MarkerOptions().position(locationCurrent).title("Ubicacion Actual").icon());

    }


    private List<LatLng> geList()
    {
        final List<LatLng> list  = new ArrayList<>();

        list.add(new LatLng(19.3935054,-99.1897021));
        list.add(new LatLng(19.3854904,-99.1865695));
        list.add(new LatLng(19.4063779,-99.1641248));
        list.add(new LatLng(19.4132994,-99.1647256));
        list.add(new LatLng(19.4279913,-99.1794456));
        list.add(new LatLng(19.4316645,-99.1527834));
        list.add(new LatLng(19.415732, -99.1476833));
        list.add(new LatLng(19.4068344,-99.1320259));
        list.add(new LatLng(19.4183045,-99.1294998));
        return list;
    }

    public void poligono() {
        final PolygonOptions opcionesPoligono = new PolygonOptions()
                .add(new LatLng(19.3935054,-99.1897021))
                .add(new LatLng(19.3854904,-99.1865695))
                .add(new LatLng(19.4063779,-99.1641248))
                .add(new LatLng(19.4132994,-99.1647256))
                .add(new LatLng(19.4279913,-99.1794456));
        Polygon poligono = mMap.addPolygon(opcionesPoligono);
        poligono.setStrokeColor(Color.RED);
    }
}
