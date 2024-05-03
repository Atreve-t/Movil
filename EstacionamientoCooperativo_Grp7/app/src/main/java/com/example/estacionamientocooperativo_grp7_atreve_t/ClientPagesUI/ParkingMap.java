package com.example.estacionamientocooperativo_grp7_atreve_t.ClientPagesUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.estacionamientocooperativo_grp7_atreve_t.Modelos.Garage;
import com.example.estacionamientocooperativo_grp7_atreve_t.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ParkingMap extends AppCompatActivity implements OnMapReadyCallback {

    TextView tvAddressParking;
    Button btnOffer;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference offerRef, garagesRef, garagesPrueba;
    List<Garage> garageList = new ArrayList<>();
    LatLng ubication;
    //Mapa
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private FusedLocationProviderClient mFusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_map);
        firebaseDatabase = FirebaseDatabase.getInstance("https://atreve-t-isi-default-rtdb.firebaseio.com/");
        // Obtener referencia a la colección "offer"
        offerRef = firebaseDatabase.getReference().child("offer");
        garagesRef = firebaseDatabase.getReference().child("garages");
        //{{{{
        garagesPrueba = firebaseDatabase.getReference().child("garajePrueba");

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");

        tvAddressParking = findViewById(R.id.tvParkingAddress);
        btnOffer = findViewById(R.id.btnOfferClient);

        //Mapa
        mMapView = findViewById(R.id.parkingMap);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        //Desactivado localizacionactual
        //getLastKnownLocation();

        btnOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        LatLng bolivia = new LatLng(-16.2902, -63.5887);
        mGoogleMap.addMarker(new MarkerOptions().position(bolivia).title("Bolivia"));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bolivia,7));


        /*/ Recuperar los garages de la base de datos y agregar los marcadores rojos
        garagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Overridew
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Verificar si el valor no es nulo
                    if (snapshot.child("latitude").getValue() != null && snapshot.child("longitude").getValue() != null) {
                        // Obtener la información del garage
                        Double latitude = snapshot.child("latitude").getValue(Double.class);
                        Double longitude = snapshot.child("longitude").getValue(Double.class);

                        // Crear el marcador rojo
                        LatLng garageLocation = new LatLng(latitude, longitude);
                        MarkerOptions markerOptions = new MarkerOptions()
                                .position(garageLocation)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        mGoogleMap.addMarker(markerOptions);
                    } else {
                        // Manejar el caso en el que el valor sea nulo
                        Toast.makeText(ParkingMap.this, "Valor nulo encontrado", Toast.LENGTH_SHORT).show();
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar cualquier error de la base de datos
                Toast.makeText(ParkingMap.this, "Error al recuperar los datos de la base de datos", Toast.LENGTH_SHORT).show();
            }
        });*/



        // Configurar el listener para detectar clics en el mapa
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //addRedMarker(latLng);
                //getAddressFromLocation(latLng);
            }
        });
        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // Aquí puedes obtener información sobre el marcador si es necesario
                // Por ejemplo, puedes obtener la posición del marcador
                LatLng markerPosition = marker.getPosition();
                String markerTitle = marker.getTitle();

                // Redirigir a MainActivity
                Toast.makeText(ParkingMap.this, "info "+ markerTitle, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ParkingMap.this, ParkingReservationPage.class);
                intent.putExtra("markerTitle", markerTitle);
                startActivity(intent);

                // Devolver true para indicar que se ha manejado el clic en el marcador
                // y que no se realizarán acciones adicionales (como abrir la información del marcador)
                return true;
            }
        });
        garagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String idDocumento = snapshot.getKey();
                    Double latitude = snapshot.child("latitud").getValue(Double.class);

                    Double longitude = snapshot.child("longitud").getValue(Double.class);
                    if (latitude != null && longitude != null) {
                        // Crear un objeto Garage con la latitud y longitud obtenidas
                        Garage garage = new Garage(latitude, longitude);
                        // Agregar el garaje a la lista
                        garageList.add(garage);
                        // Crear el marcador rojo en el mapa
                        LatLng garageLocation = new LatLng(latitude, longitude);
                        MarkerOptions markerOptions = new MarkerOptions()
                                .position(garageLocation)
                                .title(idDocumento)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        mGoogleMap.addMarker(markerOptions);


                    } else {
                        // Manejar el caso en el que no se encuentren latitud y longitud
                        Toast.makeText(ParkingMap.this, "Latitud o longitud nula encontrada", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar cualquier error de la base de datos
                Toast.makeText(ParkingMap.this, "Error al recuperar los datos de la base de datos", Toast.LENGTH_SHORT).show();
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

                tvAddressParking.setText(streetAddress);
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