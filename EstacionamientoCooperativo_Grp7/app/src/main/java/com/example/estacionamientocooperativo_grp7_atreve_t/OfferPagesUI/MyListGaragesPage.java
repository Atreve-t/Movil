package com.example.estacionamientocooperativo_grp7_atreve_t.OfferPagesUI;

import com.example.estacionamientocooperativo_grp7_atreve_t.Modelos.*;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.estacionamientocooperativo_grp7_atreve_t.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyListGaragesPage extends Activity {
    TextView tvListGarages;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference garagesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_garages);

        tvListGarages = findViewById(R.id.tvListGarages);
        firebaseDatabase = FirebaseDatabase.getInstance("https://atreve-t-isi-default-rtdb.firebaseio.com/");
        // Obtener referencia a la colección "automoviles"
        garagesRef = firebaseDatabase.getReference().child("garages");

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");

        // Realizar una consulta para buscar el usuario con el correo electrónico proporcionado
        garagesRef.orderByChild("propietarioId").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StringBuilder garagesText = new StringBuilder();
                if (!dataSnapshot.exists()) {
                    garagesText.append("Aún no tiene garages registrados");
                } else {
                    // Iterar sobre los garages encontrados
                    for (DataSnapshot garageSnapshot : dataSnapshot.getChildren()) {
                        Garage garage = garageSnapshot.getValue(Garage.class);

                        // Construir una cadena con la información del auto
                        String garageInfo = "Direccion: " + garage.getDireccion() + "\n";
                        garageInfo += "Dimensiones: " + garage.getLargo() + " x " + garage.getAncho() + " x " + garage.getAlto() + "\n";
                        garageInfo += "Estado: " + garage.getEstado() + "\n\n";

                        // Agregar la información del auto al StringBuilder
                        garagesText.append(garageInfo);
                    }
                }
                // Establecer la cadena de información de los autos en el TextView
                tvListGarages.setText(garagesText.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar cualquier error de base de datos
                Log.e("Firebase", "Error al leer datos de Firebase: " + databaseError.getMessage(), databaseError.toException());
                Toast.makeText(MyListGaragesPage.this, "Error al leer datos de Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
