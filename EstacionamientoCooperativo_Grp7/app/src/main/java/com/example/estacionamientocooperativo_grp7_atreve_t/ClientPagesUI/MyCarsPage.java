package com.example.estacionamientocooperativo_grp7_atreve_t.ClientPagesUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.estacionamientocooperativo_grp7_atreve_t.Modelos.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.estacionamientocooperativo_grp7_atreve_t.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyCarsPage extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference automovilesRef;
    private DataSnapshot dataSnapshot;
    //ListView
    private ListView lvMisAutos;
    private List<String> listAutos;
    private ArrayAdapter<String> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cars_page);

        firebaseDatabase = FirebaseDatabase.getInstance("https://atreve-t-isi-default-rtdb.firebaseio.com/");
        // Obtener referencia a la colección "automoviles"
        automovilesRef = firebaseDatabase.getReference().child("automoviles");
        lvMisAutos = findViewById(R.id.lvMyCars);
        listAutos = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");

        // Realizar una consulta para buscar el usuario con el correo electrónico proporcionado
        automovilesRef.orderByChild("propietarioId").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MyCarsPage.this.dataSnapshot = dataSnapshot;
                StringBuilder autosText = new StringBuilder();
                if (!dataSnapshot.exists()) {
                    autosText.append("Aún no tiene autos registrados");
                } else {
                    // Iterar sobre los autos encontrados
                    for (DataSnapshot autoSnapshot : dataSnapshot.getChildren()) {
                        // Obtener el auto actual
                        Automovil automovil = autoSnapshot.getValue(Automovil.class);

                        // Construir una cadena con la información del auto
                        String autoInfo = "Patente: " + automovil.getPatente() + "\n";
                        autoInfo += "Descripción: " + automovil.getDescripcion() + "\n";
                        autoInfo += "Dimensiones: " + automovil.getLargo() + " x " + automovil.getAncho() + " x " + automovil.getAlto() + "\n\n";

                        // Agregar la información del auto al StringBuilder
                        listAutos.add(autoInfo);
                        adaptador = new ArrayAdapter<>(getApplicationContext(), R.layout.item_layout, listAutos);
                        lvMisAutos.setAdapter(adaptador);
                    }
                }
                listAutos.add(autosText.toString());
                adaptador = new ArrayAdapter<>(getApplicationContext(), R.layout.item_layout, listAutos);
                lvMisAutos.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar cualquier error de base de datos
                Log.e("Firebase", "Error al leer datos de Firebase: " + databaseError.getMessage(), databaseError.toException());
                Toast.makeText(MyCarsPage.this, "Error al leer datos de Firebase", Toast.LENGTH_SHORT).show();
            }
        });

        lvMisAutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (dataSnapshot != null) {
                    // Obtener el ID del automóvil seleccionado
                    DataSnapshot selectedAutoSnapshot = dataSnapshot.getChildren().iterator().next();
                    String selectedAutoId = selectedAutoSnapshot.getKey();

                    // Crear el Intent y pasar el ID del automóvil
                    Intent intent = new Intent(MyCarsPage.this, ParkingReservationPage.class);
                    intent.putExtra("GarageId", getIntent().getStringExtra("GarageId"));
                    intent.putExtra("CarToPark", selectedAutoId);
                    startActivity(intent);
                } else {
                    // Manejo en caso de que el DataSnapshot sea nulo
                    Toast.makeText(MyCarsPage.this, "No se pudo obtener la información del automóvil seleccionado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}