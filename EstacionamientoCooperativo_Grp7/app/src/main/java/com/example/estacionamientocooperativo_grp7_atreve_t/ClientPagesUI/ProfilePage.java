package com.example.estacionamientocooperativo_grp7_atreve_t.ClientPagesUI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//linea para ruta
import com.example.estacionamientocooperativo_grp7_atreve_t.R;

public class ProfilePage extends AppCompatActivity {
    Button btnCalificarCliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        btnCalificarCliente = findViewById(R.id.btnEditProfile);

        btnCalificarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(ProfilePage.this, ProfileEditPage.class);
                //startActivity(intent);
            }
        });
    }
}