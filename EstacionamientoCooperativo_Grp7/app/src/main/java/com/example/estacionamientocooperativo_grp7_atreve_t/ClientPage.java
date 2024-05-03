package com.example.estacionamientocooperativo_grp7_atreve_t;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.estacionamientocooperativo_grp7_atreve_t.ClientPagesUI.History;
import com.example.estacionamientocooperativo_grp7_atreve_t.ClientPagesUI.MyCarsPage;
import com.example.estacionamientocooperativo_grp7_atreve_t.ClientPagesUI.ParkingMap;
import com.example.estacionamientocooperativo_grp7_atreve_t.ClientPagesUI.ProfilePage;
import com.example.estacionamientocooperativo_grp7_atreve_t.ClientPagesUI.RegisterCar;

public class ClientPage extends AppCompatActivity {

    Button btnProfileJ;
    Button btnCarListJ;
    Button btnRegisterCarJ;
    Button btnParkingMapJ;
    Button btnMyScheduleJ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_page);
        btnProfileJ = findViewById(R.id.btnProfile);
        btnCarListJ = findViewById(R.id.btnCarList);
        btnRegisterCarJ = findViewById(R.id.btnRegisterCar);
        btnParkingMapJ = findViewById(R.id.btnParkingMap);
        btnMyScheduleJ = findViewById(R.id.btnMySchedule);


        btnProfileJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientPage.this, ProfilePage.class);
                startActivity(intent);
            }
        });
        btnCarListJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientPage.this, MyCarsPage.class);
                startActivity(intent);
            }
        });
        btnRegisterCarJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientPage.this, RegisterCar.class);
                startActivity(intent);
            }
        });
        btnParkingMapJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientPage.this, ParkingMap.class);
                startActivity(intent);
            }
        });
        btnMyScheduleJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientPage.this, History.class);
                startActivity(intent);
            }
        });
    }
}