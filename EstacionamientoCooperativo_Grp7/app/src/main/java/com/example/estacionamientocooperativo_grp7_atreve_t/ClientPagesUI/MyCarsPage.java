package com.example.estacionamientocooperativo_grp7_atreve_t.ClientPagesUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.estacionamientocooperativo_grp7_atreve_t.Modelos.*;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.estacionamientocooperativo_grp7_atreve_t.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyCarsPage extends AppCompatActivity {
    TextView tvListAutos;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference automovilesRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cars_page);

        tvListAutos = findViewById(R.id.tvListAutos);
        firebaseDatabase = FirebaseDatabase.getInstance("https://atreve-t-isi-default-rtdb.firebaseio.com/");
        // Obtener referencia a la colección "automoviles"
        automovilesRef = firebaseDatabase.getReference().child("automoviles");

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");

        // Realizar una consulta para buscar el usuario con el correo electrónico proporcionado
        automovilesRef.orderByChild("propietarioId").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
                        autosText.append(autoInfo);
                    }
                }
                tvListAutos.setText(autosText.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar cualquier error de base de datos
                Log.e("Firebase", "Error al leer datos de Firebase: " + databaseError.getMessage(), databaseError.toException());
                Toast.makeText(MyCarsPage.this, "Error al leer datos de Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }
}