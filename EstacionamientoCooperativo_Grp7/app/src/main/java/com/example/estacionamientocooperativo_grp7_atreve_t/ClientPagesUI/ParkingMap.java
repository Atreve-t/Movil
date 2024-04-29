package com.example.estacionamientocooperativo_grp7_atreve_t.ClientPagesUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.estacionamientocooperativo_grp7_atreve_t.R;

public class ParkingMap extends AppCompatActivity {

    ImageView mapa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_map);
        mapa= findViewById(R.id.imageMapa);
        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ParkingMap.this, ParkingReservationPage.class);
                startActivity(intent);
            }
        });
    }
}