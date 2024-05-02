package com.example.estacionamientocooperativo_grp7_atreve_t.OfferPagesUI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.example.estacionamientocooperativo_grp7_atreve_t.Modelos.*;

import com.example.estacionamientocooperativo_grp7_atreve_t.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.location.Address;

import java.io.IOException;
import java.util.Locale;
import java.util.List;

public class RegisterGarage extends Activity implements OnMapReadyCallback {
    TextView tvAddress;
    EditText etLength, etWidth, etHeight;
    Button btnregister;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference garagesRef, espacioGaragesRef;
    LatLng ubication;
    //Mapa
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_garage);
        firebaseDatabase = FirebaseDatabase.getInstance("https://atreve-t-isi-default-rtdb.firebaseio.com/");
        // Obtener referencia a la colección "garages"
        garagesRef = firebaseDatabase.getReference().child("garages");
        espacioGaragesRef = firebaseDatabase.getReference().child("espacioGarages");
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");

        tvAddress = findViewById(R.id.tvAddress);
        etLength = findViewById(R.id.etLengthRegisterGarage);
        etWidth = findViewById(R.id.etWidthRegisterGarage);
        etHeight = findViewById(R.id.etHeightRegisterGarage);
        btnregister = findViewById(R.id.btnRegistrarGarage);

        //Mapa
        mMapView = findViewById(R.id.mpDireccion);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        getLastKnownLocation();

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer length = Integer.parseInt(etLength.getText().toString());
                Integer width = Integer.parseInt(etWidth.getText().toString());
                Integer height = Integer.parseInt(etHeight.getText().toString());

                Garage garage = new Garage(email,tvAddress.getText().toString(),ubication.latitude, ubication.longitude,height,width,length,"libre");
                String garageId = garagesRef.push().getKey();
                garagesRef.child(garageId).setValue(garage);
                EspacioGarage espacioGarage = new EspacioGarage(garageId,length,width);
                espacioGaragesRef.child(espacioGaragesRef.push().getKey()).setValue(espacioGarage);
                Intent intent = new Intent(RegisterGarage.this,MyListGaragesPage.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        // Configurar el listener para detectar clics en el mapa
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                addRedMarker(latLng);
            }
        });
    }

    private void getLastKnownLocation() {
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
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null && mGoogleMap != null) {
                        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                        addCurrentLocationMarker(location);
                    } else {
                        Toast.makeText(this, "No se pudo obtener la ubicación actual", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addCurrentLocationMarker(Location location) {
        if (mGoogleMap != null && location != null) {
            mGoogleMap.clear();
            LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            // Crear un marcador en la ubicación actual
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(currentLatLng)
                    .title("Mi ubicación actual")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            mGoogleMap.addMarker(markerOptions);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
            getAddressFromLocation(currentLatLng);
        }
    }

    private void addRedMarker(LatLng latLng) {
        mGoogleMap.clear();
        // Crear un marcador en la ubicación donde se hizo clic
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        mGoogleMap.addMarker(markerOptions);

        // Obtener la latitud y longitud de donde se hizo clic
        getAddressFromLocation(latLng);
    }

    private void getAddressFromLocation(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        ubication = latLng;
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                String streetAddress = address.getAddressLine(0); // Calle y número
                String city = address.getLocality(); // Ciudad
                String state = address.getAdminArea(); // Estado o provincia
                String country = address.getCountryName(); // País
                String postalCode = address.getPostalCode(); // Código postal

                tvAddress.setText(streetAddress);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
