package com.example.clift.ui.student.ui.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clift.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import static android.content.ContentValues.TAG;

public class MapsFragment extends Fragment {

    private double lat=-0.225219;
    private double lng=-78.5248;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng sydney = new LatLng(lat, lng);
            Marker marker = googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker").draggable(true));
            getParentFragmentManager().setFragmentResultListener("key", getActivity(), new FragmentResultListener() {
                @Override
                public void onFragmentResult(@NonNull @NotNull String requestKey, @NonNull @NotNull Bundle result) {
                    lat = result.getDouble("lat");
                    lng = result.getDouble("lng");
                    Log.println(Log.INFO,"lat", String.valueOf(lat));
                    Log.println(Log.INFO,"lng", String.valueOf(lng));
                    LatLng coords = new LatLng(lat, lng);
                    marker.setPosition(coords);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(coords));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coords,18),5000 , null);

                }
            });
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,18),5000 , null);
        }
    };

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =

                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {

            mapFragment.getMapAsync(callback);
        }
    }
}