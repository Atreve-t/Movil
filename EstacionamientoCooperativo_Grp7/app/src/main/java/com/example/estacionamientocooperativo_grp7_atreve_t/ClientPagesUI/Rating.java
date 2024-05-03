package com.example.estacionamientocooperativo_grp7_atreve_t.ClientPagesUI;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.estacionamientocooperativo_grp7_atreve_t.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Rating extends AppCompatActivity {
    Button btnReservas;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reservasRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
    }
}
