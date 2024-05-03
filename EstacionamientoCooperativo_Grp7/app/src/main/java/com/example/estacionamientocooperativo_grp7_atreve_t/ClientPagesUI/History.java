package com.example.estacionamientocooperativo_grp7_atreve_t.ClientPagesUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.estacionamientocooperativo_grp7_atreve_t.Modelos.*;
import com.example.estacionamientocooperativo_grp7_atreve_t.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {
    Button btnReservas;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ofertasRef;
    private DataSnapshot dataSnapshot;
    //ListView
    private ListView lvMisReservas;
    private List<String> listReservas;
    private ArrayAdapter<String> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        btnReservas = findViewById(R.id.btnNuevaReserva);
        lvMisReservas = findViewById(R.id.lvReservas);
        listReservas = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance("https://atreve-t-isi-default-rtdb.firebaseio.com/");
        // Obtener referencia a la colección "automoviles"
        ofertasRef = firebaseDatabase.getReference().child("ofertas");
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");

        ofertasRef.orderByChild("clientId").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                History.this.dataSnapshot = dataSnapshot;
                StringBuilder autosText = new StringBuilder();
                if (!dataSnapshot.exists()) {
                    autosText.append("Aún no tiene solicitudes registradas");
                } else {
                    // Iterar sobre los autos encontrados
                    for (DataSnapshot ofertaSnapshot : dataSnapshot.getChildren()) {
                        // Obtener el auto actual
                        Oferta oferta = ofertaSnapshot.getValue(Oferta.class);

                        // Construir una cadena con la información de la oferta
                        String ofertaInfo = "Dirección Garage: " + "\n";
                        ofertaInfo += "Oferta Actual: " + "\n";
                        ofertaInfo += "Automovil: " + "\n\n";

                        // Agregar la información del auto al StringBuilder
                        listReservas.add(ofertaInfo);
                        adaptador = new ArrayAdapter<>(getApplicationContext(), R.layout.item_layout, listReservas);
                        lvMisReservas.setAdapter(adaptador);
                    }
                }
                listReservas.add(autosText.toString());
                adaptador = new ArrayAdapter<>(getApplicationContext(), R.layout.item_layout, listReservas);
                lvMisReservas.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar cualquier error de base de datos
                Log.e("Firebase", "Error al leer datos de Firebase: " + databaseError.getMessage(), databaseError.toException());
                Toast.makeText(History.this, "Error al leer datos de Firebase", Toast.LENGTH_SHORT).show();
            }
        });

        lvMisReservas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(History.this, Rating.class);
                startActivity(intent);
                /*if (dataSnapshot != null) {
                    // Obtener el ID del automóvil seleccionado
                    DataSnapshot selectedAutoSnapshot = dataSnapshot.getChildren().iterator().next();
                    String selectedAutoId = selectedAutoSnapshot.getKey();

                    // Crear el Intent y pasar el ID del automóvil
                    Intent intent = new Intent(History.this, Rating.class);
                    intent.putExtra("GarageId", getIntent().getStringExtra("GarageId"));
                    intent.putExtra("CarToPark", selectedAutoId);
                    startActivity(intent);
                } else {
                    // Manejo en caso de que el DataSnapshot sea nulo
                    Toast.makeText(History.this, "No se pudo obtener la información de la solicitud seleccionada", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        btnReservas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(History.this, ParkingMap.class);
                startActivity(intent);
            }
        });
    }
}
