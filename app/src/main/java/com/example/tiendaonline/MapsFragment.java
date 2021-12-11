package com.example.tiendaonline;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {

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
            LatLng mallDelSur = new LatLng(-12.15432899156279, -76.98270589113237);
            LatLng tiendaJockey = new LatLng(-12.086794462628745, -76.97715371847153);
            LatLng metroIndependencia = new LatLng(-12.0065289, -77.0605732);
            LatLng tottus = new LatLng(-11.993987617466226, -77.06194639205934);

            googleMap.addMarker(new MarkerOptions().position(mallDelSur).title("Tienda En Mall Del Sur"));
            googleMap.addMarker(new MarkerOptions().position(tiendaJockey).title("Tienda En Jockey Plaza"));
            googleMap.addMarker(new MarkerOptions().position(metroIndependencia).title("Tienda En Metro de Independencia"));
            googleMap.addMarker(new MarkerOptions().position(tottus).title("Tienda En Metro"));

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(mallDelSur));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(tiendaJockey));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(metroIndependencia));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(tottus));

            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
    };

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