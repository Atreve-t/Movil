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

import com.example.estacionamientocooperativo_grp7_atreve_t.Modelos.*;
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
        // Obtener referencia a la colección "ofertas"
        ofertasRef = firebaseDatabase.getReference().child("ofertas");
        lvmyrequest = findViewById(R.id.lvrequest);
        listrequest = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");

        // Realizar una consulta para buscar los garages donde el propietarioId coincida con el email proporcionado
        DatabaseReference garagesRef = firebaseDatabase.getReference().child("garages");
        garagesRef.orderByChild("propietarioId").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot garagesSnapshot) {
                if (garagesSnapshot.exists()) {
                    // Iterar sobre los garages encontrados
                    for (DataSnapshot garageSnapshot : garagesSnapshot.getChildren()) {
                        // Obtener el garageId del garage actual
                        String garageId = garageSnapshot.getKey();
                        // Realizar una consulta para buscar las ofertas correspondientes en la colección "ofertas" utilizando el garageId
                        DatabaseReference ofertasRef = firebaseDatabase.getReference().child("ofertas");
                        ofertasRef.orderByChild("garajeId").equalTo(garageId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot ofertasSnapshot) {
                                if (ofertasSnapshot.exists()) {
                                    // Iterar sobre las ofertas encontradas
                                    for (DataSnapshot ofertaSnapshot : ofertasSnapshot.getChildren()) {
                                        // Obtener el auto actual
                                        Oferta oferta = ofertaSnapshot.getValue(Oferta.class);

                                        // Construir una cadena con la información del auto
                                        String ofertaInfo = "Cliente: " + oferta.getClientId() + "\n";
                                        ofertaInfo += "Fecha Inicio: " + "\n";
                                        //ofertaInfo += "Fecha Fin: " + oferta.getFechaFin() + "\n";
                                        ofertaInfo += "Oferta Actual: " + oferta.getOfertaActual() + "\n\n";

                                        // Agregar la información del auto al StringBuilder
                                        listrequest.add(ofertaInfo);
                                        adaptador = new ArrayAdapter<>(getApplicationContext(), R.layout.item_layout, listrequest);
                                        lvmyrequest.setAdapter(adaptador);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError ofertasError) {
                                // Manejar cualquier error de base de datos
                                Log.e("Firebase", "Error al leer datos de Firebase (ofertas): " + ofertasError.getMessage(), ofertasError.toException());
                                Toast.makeText(RequestBidder.this, "Error al leer datos de Firebase (ofertas)", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    // No se encontraron garages para el email proporcionado
                    listrequest.add("No se encontraron garages para este email.");
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError garagesError) {
                // Manejar cualquier error de base de datos
                Log.e("Firebase", "Error al leer datos de Firebase (garages): " + garagesError.getMessage(), garagesError.toException());
                Toast.makeText(RequestBidder.this, "Error al leer datos de Firebase (garages)", Toast.LENGTH_SHORT).show();
            }
        });


        lvmyrequest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });
    }
}
