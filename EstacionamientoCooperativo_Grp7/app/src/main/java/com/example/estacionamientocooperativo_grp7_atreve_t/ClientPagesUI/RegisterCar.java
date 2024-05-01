package com.example.estacionamientocooperativo_grp7_atreve_t.ClientPagesUI;

import androidx.appcompat.app.AppCompatActivity;
import com.example.estacionamientocooperativo_grp7_atreve_t.Modelos.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.estacionamientocooperativo_grp7_atreve_t.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterCar extends AppCompatActivity {

    Button btnRegistrar;
    EditText patente, descripcion, largo, alto, ancho;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference automovilesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_car);

        firebaseDatabase = FirebaseDatabase.getInstance("https://atreve-t-isi-default-rtdb.firebaseio.com/");
        // Obtener referencia a la colección "automoviles"
        automovilesRef = firebaseDatabase.getReference().child("automoviles");

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");

        patente = findViewById(R.id.etPatent);
        descripcion = findViewById(R.id.etDescription);
        largo = findViewById(R.id.lengthInput);
        alto = findViewById(R.id.heightInput);
        ancho = findViewById(R.id.widthInput);

        btnRegistrar = findViewById(R.id.btnRegistrarAuto);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String spatente = patente.getText().toString();
                String sdescripcion = descripcion.getText().toString();
                Integer dlargo = Integer.parseInt(alto.getText().toString());
                Integer dalto = Integer.parseInt(ancho.getText().toString());
                Integer dancho = Integer.parseInt(largo.getText().toString());

                Automovil car = new Automovil(email,spatente,sdescripcion,dalto,dancho,dlargo);
                automovilesRef.child(automovilesRef.push().getKey()).setValue(car);
                Intent intent = new Intent(RegisterCar.this, MyCarsPage.class);
                startActivity(intent);
            }
        });

    }
}