package com.example.estacionamientocooperativo_grp7_atreve_t.ClientPagesUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.estacionamientocooperativo_grp7_atreve_t.BidderPage;
import com.example.estacionamientocooperativo_grp7_atreve_t.InitPage;
import com.example.estacionamientocooperativo_grp7_atreve_t.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class History extends AppCompatActivity {
    Button btnReservas;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reservasRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        btnReservas = findViewById(R.id.btnNuevaReserva);
        btnReservas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(History.this, ParkingMap.class);
                startActivity(intent);
            }
        });
    }
}
