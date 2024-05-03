package com.example.estacionamientocooperativo_grp7_atreve_t.ClientPagesUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.estacionamientocooperativo_grp7_atreve_t.Modelos.Oferta;
import com.example.estacionamientocooperativo_grp7_atreve_t.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ParkingReservationPage extends AppCompatActivity {

    DatabaseReference  garagesRef;
    TextView InfoGaraje;
    Button btnReservation;
    TimePicker tiempoInicio;
    TimePicker tiempoFin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_reservation_page);
        InfoGaraje=findViewById(R.id.textGarajeInfo);
        btnReservation=findViewById(R.id.btnParkingReservation);

        tiempoInicio =findViewById(R.id.BeginHourReservation);
        String horaInicio = String.valueOf(tiempoInicio.getHour());
        String minInicio = String.valueOf(tiempoInicio.getHour());

        tiempoFin =findViewById(R.id.EndHourReservation);

        String horaFin = String.valueOf(tiempoFin.getHour());
        String minFin = String.valueOf(tiempoFin.getHour());

        String markerTitle = getIntent().getStringExtra("markerTitle");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://atreve-t-isi-default-rtdb.firebaseio.com/");
        garagesRef = firebaseDatabase.getReference().child("garages");
        buscarGarajePorId(markerTitle);



        btnReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String garajeId = getIntent().getStringExtra("markerTitle");
                String estado = "ocupado"; // O el estado deseado
                int horaInicio = tiempoInicio.getHour();
                int minInicio = tiempoInicio.getMinute();

                // Obtener hora y minuto de fin
                int horaFin = tiempoFin.getHour();
                int minFin = tiempoFin.getMinute();

                // Convertir las horas y minutos a strings con el formato adecuado
                String horaInicioStr = String.format("%02d", horaInicio) + ":" + String.format("%02d", minInicio);
                String horaFinStr = String.format("%02d", horaFin) + ":" + String.format("%02d", minFin);

                Oferta oferta = new Oferta(garajeId, estado, horaInicioStr, horaFinStr);

                // Obtener una referencia a la colección de ofertas
                DatabaseReference ofertasRef = firebaseDatabase.getReference().child("ofertas");


                ofertasRef.push().setValue(oferta)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // La oferta se ha subido exitosamente
                                Toast.makeText(ParkingReservationPage.this, "Oferta creada y subida exitosamente", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Ocurrió un error al subir la oferta
                                Toast.makeText(ParkingReservationPage.this, "Error al subir la oferta: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
    private void confirmarReserva() {
        // Crear un objeto Oferta

    }
    private void buscarGarajePorId(String idGaraje) {
        // Hacer la consulta a la base de datos
        garagesRef.child(idGaraje).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    Long altoLong = dataSnapshot.child("alto").getValue(Long.class);
                    Long anchoLong = dataSnapshot.child("ancho").getValue(Long.class);String direccion = dataSnapshot.child("direccion").getValue(String.class);

                    // Formatear los datos en una cadena
                    String infoGarajeTexto = "Alto: " + altoLong + "\nAncho: " + anchoLong + "\nDirección: " + direccion;

                    // Asignar la cadena formateada al TextView InfoGaraje
                    InfoGaraje.setText(infoGarajeTexto);

                } else {
                    // Manejar el caso en el que no se encuentre el garaje con el ID dado
                    Toast.makeText(ParkingReservationPage.this, "No se encontró el garaje con el ID dado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar cualquier error de la base de datos
                Toast.makeText(ParkingReservationPage.this, "Error al buscar el garaje en la base de datos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}