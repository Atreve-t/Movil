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
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.Locale;
import java.util.List;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import java.util.Calendar;

import android.app.TimePickerDialog;
import java.text.SimpleDateFormat;
import android.widget.ArrayAdapter;

public class RegisterGarage extends Activity implements OnMapReadyCallback {
    TextView tvAddress;
    EditText etLength, etWidth, etHeight;
    Button btnregister, btnpickDate;

    //ListView
    private ListView lvFechas;
    private List<String> listFechas;
    private ArrayAdapter<String> adaptador;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference garagesRef, espacioGaragesRef;
    LatLng ubication;
    //Mapa
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private FusedLocationProviderClient mFusedLocationClient;

    //
    HorarioGarage horarioGarage;
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
        btnpickDate = findViewById(R.id.btnPickDate);
        lvFechas = findViewById(R.id.lvFechas);
        listFechas = new ArrayList<>();
        horarioGarage = new HorarioGarage();
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

        // Fecha y hora
        btnpickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer length = Integer.parseInt(etLength.getText().toString());
                Integer width = Integer.parseInt(etWidth.getText().toString());
                Integer height = Integer.parseInt(etHeight.getText().toString());

                Garage garage = new Garage(email,tvAddress.getText().toString(),ubication.latitude, ubication.longitude,height,width,length,"libre",horarioGarage,Double.valueOf(20.5));
                String garageId = garagesRef.push().getKey();
                garagesRef.child(garageId).setValue(garage);
                EspacioGarage espacioGarage = new EspacioGarage(garageId,length,width);
                espacioGaragesRef.child(espacioGaragesRef.push().getKey()).setValue(espacioGarage);
                Intent intent = new Intent(RegisterGarage.this,MyListGaragesPage.class);
                startActivity(intent);
            }
        });
    }

    //Fecha y hora
    public void showDatePickerDialog() {
        // Obtener la fecha y hora actuales
        Calendar calendar = Calendar.getInstance();

        // Crear un diálogo de selección de fecha
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    // Cuando se selecciona una fecha, crear un diálogo de selección de hora para la hora inicial
                    TimePickerDialog startTimePickerDialog = new TimePickerDialog(
                            this,
                            (view1, hourOfDay, minute) -> {
                                // Cuando se selecciona una hora inicial, mostrar un diálogo de selección de hora para la hora final
                                TimePickerDialog endTimePickerDialog = new TimePickerDialog(
                                        this,
                                        (view2, endHourOfDay, endMinute) -> {
                                            // Crear un objeto Calendar para la fecha seleccionada
                                            Calendar selectedCalendar = Calendar.getInstance();
                                            selectedCalendar.set(year, month, dayOfMonth);

                                            // Crear un objeto Calendar para la hora de inicio seleccionada
                                            Calendar startTimeCalendar = Calendar.getInstance();
                                            startTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                            startTimeCalendar.set(Calendar.MINUTE, minute);

                                            // Crear un objeto Calendar para la hora de fin seleccionada
                                            Calendar endTimeCalendar = Calendar.getInstance();
                                            endTimeCalendar.set(Calendar.HOUR_OF_DAY, endHourOfDay);
                                            endTimeCalendar.set(Calendar.MINUTE, endMinute);

                                            // Agregar el horario al HorarioGarage
                                            horarioGarage.agregarHorario(dayOfMonth, (month + 1), year, startTimeCalendar, endTimeCalendar);

                                            // Mostrar las horas de inicio y fin en el TextView
                                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                                            String startTime = sdf.format(startTimeCalendar.getTime());
                                            String endTime = sdf.format(endTimeCalendar.getTime());
                                            String dateTimeRange = dayOfMonth + "/" + (month + 1) + "/" + year + "    Hora inicio: " + startTime + "\nHora fin: " + endTime;

                                            // Antes de llamar a notifyDataSetChanged()
                                            listFechas.add(dateTimeRange);
                                            adaptador = new ArrayAdapter<>(getApplicationContext(), R.layout.item_layout, listFechas);
                                            lvFechas.setAdapter(adaptador);
                                            adaptador.notifyDataSetChanged();

                                        },
                                        calendar.get(Calendar.HOUR_OF_DAY), // Hora actual
                                        calendar.get(Calendar.MINUTE), // Minuto actual
                                        false // Formato de hora de 24 horas
                                );
                                // Mostrar el diálogo de selección de hora final
                                endTimePickerDialog.show();
                            },
                            calendar.get(Calendar.HOUR_OF_DAY), // Hora actual
                            calendar.get(Calendar.MINUTE), // Minuto actual
                            false // Formato de hora de 24 horas
                    );
                    // Mostrar el diálogo de selección de hora inicial
                    startTimePickerDialog.show();
                },
                calendar.get(Calendar.YEAR), // Año actual
                calendar.get(Calendar.MONTH), // Mes actual
                calendar.get(Calendar.DAY_OF_MONTH) // Día actual
        );
        // Mostrar el diálogo de selección de fecha
        datePickerDialog.show();
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
