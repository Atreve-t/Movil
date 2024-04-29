package com.example.estacionamientocooperativo_grp7_atreve_t;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InitPage extends AppCompatActivity {

    Button btnClientJ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_page);

        btnClientJ= findViewById(R.id.btnClient);

        btnClientJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InitPage.this, ClientPage.class);
                startActivity(intent);
            }
        });

    }
}