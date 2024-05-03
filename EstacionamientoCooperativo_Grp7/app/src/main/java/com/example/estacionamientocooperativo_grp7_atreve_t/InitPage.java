package com.example.estacionamientocooperativo_grp7_atreve_t;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.estacionamientocooperativo_grp7_atreve_t.Modelos.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InitPage extends AppCompatActivity {

    Button btnClientJ, btnBidderJ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference usuariosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_page);

        firebaseDatabase = FirebaseDatabase.getInstance("https://atreve-t-isi-default-rtdb.firebaseio.com/");
        // Obtener referencia a la colección "usuarios"
        usuariosRef = firebaseDatabase.getReference().child("usuarios");

        btnClientJ= findViewById(R.id.btnClient);
        btnBidderJ = findViewById(R.id.btnBidder);


        // Ocultar botón btnClient si el tipo de usuario es ofertante
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");

        // Verificar y mostrar solo el botón correspondiente al tipo de usuario definido
        usuariosRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Usuario user = userSnapshot.getValue(Usuario.class);
                    if (user != null) {
                        // Verificar si el tipo de usuario ya está definido
                        if ("ofertante".equals(user.getTipo())) {
                            btnClientJ.setVisibility(View.GONE);
                            btnBidderJ.setVisibility(View.VISIBLE); // Mostrar botón de ofertante
                        } else if ("cliente".equals(user.getTipo())) {
                            btnClientJ.setVisibility(View.VISIBLE); // Mostrar botón de cliente
                            btnBidderJ.setVisibility(View.GONE);
                        } else {
                            // Si el tipo de usuario aún no está definido, mostrar ambos botones
                            btnClientJ.setVisibility(View.VISIBLE);
                            btnBidderJ.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar cualquier error de base de datos
                Log.e("Firebase", "Error al leer datos de Firebase: " + databaseError.getMessage(), databaseError.toException());
                Toast.makeText(InitPage.this, "Error al leer datos de Firebase", Toast.LENGTH_SHORT).show();
            }
        });

        btnClientJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Buscar el usuario con el email proporcionado
                usuariosRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            Usuario user = userSnapshot.getValue(Usuario.class);
                            if (user != null) {
                                if (user.getTipo() == null || user.getTipo().isEmpty()) {
                                    // Si el tipo de usuario aún no está definido, establecerlo como "cliente"
                                    user.setTipo("cliente");
                                    userSnapshot.getRef().setValue(user);
                                }
                                Intent intent = new Intent(InitPage.this, ClientPage.class);
                                startActivity(intent);
                            }
                        }

                        Intent intent = new Intent(InitPage.this, ClientPage.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Manejar cualquier error de base de datos
                        Log.e("Firebase", "Error al leer datos de Firebase: " + databaseError.getMessage(), databaseError.toException());
                        Toast.makeText(InitPage.this, "Error al leer datos de Firebase", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        btnBidderJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuariosRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            Usuario user = userSnapshot.getValue(Usuario.class);
                            if (user != null) {
                                if (user.getTipo() == null || user.getTipo().isEmpty()) {
                                    // Si el tipo de usuario aún no está definido, establecerlo como "ofertante"
                                    user.setTipo("ofertante");
                                    userSnapshot.getRef().setValue(user);
                                }
                                Intent intent = new Intent(InitPage.this, BidderPage.class);
                                startActivity(intent);
                            }
                        }

                        Intent intent = new Intent(InitPage.this, BidderPage.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Manejar cualquier error de base de datos
                        Log.e("Firebase", "Error al leer datos de Firebase: " + databaseError.getMessage(), databaseError.toException());
                        Toast.makeText(InitPage.this, "Error al leer datos de Firebase", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

}