package com.example.estacionamientocooperativo_grp7_atreve_t.ClientPagesUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.estacionamientocooperativo_grp7_atreve_t.Modelos.*;
import com.example.estacionamientocooperativo_grp7_atreve_t.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ParkingReservationPage extends AppCompatActivity {
    TextView parking, tvCarToOffer, tvFechasSleccionadas, tvPrecio;
    EditText etmonto;
    Button btnSendOffer, btnFechaInicio, btnFechaFin;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference automovilesRef, ofertasRef, garagesRef;
    String UidcarToPark = "", garageIdParking = "";
    private Calendar calendarInicio;
    private Calendar calendarFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_reservation_page);
        parking = findViewById(R.id.tvParkingOffer);
        tvCarToOffer = findViewById(R.id.tvCarToParkOffer);
        tvFechasSleccionadas = findViewById(R.id.tvFechasSeleccionadas);
        tvPrecio = findViewById(R.id.tvPrecioGarage);
        btnSendOffer = findViewById(R.id.btnSendOffer);
        etmonto = findViewById(R.id.etOffer);
        btnFechaInicio = findViewById(R.id.btnFechaInicio);
        btnFechaFin = findViewById(R.id.btnFechaFin);
        calendarInicio = Calendar.getInstance();
        calendarFin = Calendar.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://atreve-t-isi-default-rtdb.firebaseio.com/");
        // Obtener referencia a la colección "ofertas"
        automovilesRef = firebaseDatabase.getReference().child("automoviles");
        ofertasRef = firebaseDatabase.getReference().child("ofertas");
        garagesRef = firebaseDatabase.getReference().child("garages");

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");

        Intent intent = getIntent();
        if (intent != null) {
            garageIdParking = intent.getStringExtra("GarageId");
            UidcarToPark = intent.getStringExtra("CarToPark");
        }

        automovilesRef.child(UidcarToPark).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Automovil automovil = dataSnapshot.getValue(Automovil.class);
                    if (automovil != null) {
                        // Obtener la placa y la descripción del automóvil
                        String patente = "Patente: " + automovil.getPatente() + "\n";
                        String descripcion ="Descripcion: " + automovil.getDescripcion() + "\n";
                        tvCarToOffer.setText(patente + descripcion);

                    } else {
                        Toast.makeText(ParkingReservationPage.this, "No se encontró el automóvil", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ParkingReservationPage.this, "No se encontró el automóvil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar cualquier error de base de datos
                Log.e("Firebase", "Error al leer datos de Firebase: " + databaseError.getMessage(), databaseError.toException());
            }
        });

        garagesRef.child(garageIdParking).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Garage garage = dataSnapshot.getValue(Garage.class);
                    if (garage != null) {
                        parking.setText(garage.getDireccion());
                        tvPrecio.setText(garage.getPrecio().toString());

                    } else {
                        Toast.makeText(ParkingReservationPage.this, "No se encontró el automóvil", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ParkingReservationPage.this, "No se encontró el automóvil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar cualquier error de base de datos
                Log.e("Firebase", "Error al leer datos de Firebase: " + databaseError.getMessage(), databaseError.toException());
            }
        });

        btnFechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(true);
            }
        });

        btnFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(false);
            }
        });

        btnSendOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double monto = Double.valueOf(etmonto.getText().toString());

                Oferta oferta = new Oferta(garageIdParking, email, UidcarToPark, monto, monto, "", calendarInicio, calendarFin);
                ofertasRef.child(ofertasRef.push().getKey()).setValue(oferta);

                //Intent intent = new Intent(ParkingReservationPage.this, MisReservas.class);
                //startActivity(intent);
            }
        });
    }

    public void showDatePickerDialog(final boolean isInicio) {
        // Obtener la fecha y hora actuales
        Calendar calendar = Calendar.getInstance();

        // Crear un diálogo de selección de fecha y hora
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    // Cuando se selecciona una fecha, obtener la fecha seleccionada
                    Calendar selectedCalendar = Calendar.getInstance();
                    selectedCalendar.set(year, month, dayOfMonth);

                    // Guardar el calendario seleccionado en la variable correspondiente
                    if (isInicio) {
                        calendarInicio = selectedCalendar;
                    } else {
                        calendarFin = selectedCalendar;
                    }

                    // Crear un diálogo de selección de hora
                    TimePickerDialog timePickerDialog = new TimePickerDialog(
                            this,
                            (view1, hourOfDay, minute) -> {
                                // Cuando se selecciona una hora, actualizar el calendario seleccionado con la hora seleccionada
                                selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                selectedCalendar.set(Calendar.MINUTE, minute);

                                // Formatear la fecha y hora seleccionadas
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                                String selectedDateTime = sdf.format(selectedCalendar.getTime());


                                if (!isInicio){
                                    tvFechasSleccionadas.append("Fecha y hora Fin: " + selectedDateTime);
                                }
                                else{
                                    tvFechasSleccionadas.append("Fecha y hora Inicio: " + selectedDateTime + "\n");
                                }
                            },
                            calendar.get(Calendar.HOUR_OF_DAY), // Hora actual
                            calendar.get(Calendar.MINUTE), // Minuto actual
                            false // Formato de hora de 24 horas
                    );

                    // Mostrar el diálogo de selección de hora
                    timePickerDialog.show();
                },
                calendar.get(Calendar.YEAR), // Año actual
                calendar.get(Calendar.MONTH), // Mes actual
                calendar.get(Calendar.DAY_OF_MONTH) // Día actual
        );

        // Mostrar el diálogo de selección de fecha
        datePickerDialog.show();
    }

}