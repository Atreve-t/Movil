package com.example.estacionamientocooperativo_grp7_atreve_t.OfferPagesUI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.estacionamientocooperativo_grp7_atreve_t.ClientPagesUI.MyCarsPage;
import com.example.estacionamientocooperativo_grp7_atreve_t.ClientPagesUI.ParkingReservationPage;
import com.example.estacionamientocooperativo_grp7_atreve_t.Modelos.Automovil;
import com.example.estacionamientocooperativo_grp7_atreve_t.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RequestBidder extends Activity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ofertasRef;
    private DataSnapshot dataSnapshot;
    //ListView
    private ListView lvmyrequest;
    private List<String> listrequest;
    private ArrayAdapter<String> adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bidder_request);

        firebaseDatabase = FirebaseDatabase.getInstance("https://atreve-t-isi-default-rtdb.firebaseio.com/");
        // Obtener referencia a la colección "automoviles"
        ofertasRef = firebaseDatabase.getReference().child("ofertas");
        lvmyrequest = findViewById(R.id.lvrequest);
        listrequest = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");

        //Falta trabajar esto
        /*// Realizar una consulta para buscar el usuario con el correo electrónico proporcionado
        ofertasRef.orderByChild("propietarioId").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RequestBidder.this.dataSnapshot = dataSnapshot;
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
                        listrequest.add(autoInfo);
                        adaptador = new ArrayAdapter<>(getApplicationContext(), R.layout.item_layout, listrequest);
                        lvmyrequest.setAdapter(adaptador);
                    }
                }
                listrequest.add(autosText.toString());
                adaptador = new ArrayAdapter<>(getApplicationContext(), R.layout.item_layout, listrequest);
                lvmyrequest.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar cualquier error de base de datos
                Log.e("Firebase", "Error al leer datos de Firebase: " + databaseError.getMessage(), databaseError.toException());
                Toast.makeText(RequestBidder.this, "Error al leer datos de Firebase", Toast.LENGTH_SHORT).show();
            }
        });

        lvmyrequest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });*/
    }
}
