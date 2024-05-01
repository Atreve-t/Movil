package com.example.estacionamientocooperativo_grp7_atreve_t;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.estacionamientocooperativo_grp7_atreve_t.OfferPagesUI.MyListGaragesPage;
import com.example.estacionamientocooperativo_grp7_atreve_t.ClientPagesUI.ProfilePage;
import com.example.estacionamientocooperativo_grp7_atreve_t.OfferPagesUI.RegisterGarage;
import com.example.estacionamientocooperativo_grp7_atreve_t.OfferPagesUI.RequestBidder;


public class BidderPage extends Activity {
    Button btnProfileJ, btnListGarageJ, btnRegisterGarageJ, btnRequestJ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bidder_page);
        btnProfileJ = findViewById(R.id.btnProfile);
        btnListGarageJ = findViewById(R.id.btnListGarage);
        btnRegisterGarageJ = findViewById(R.id.btnRegisterGarage);
        btnRequestJ = findViewById(R.id.btnRequests);

        btnProfileJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BidderPage.this, ProfilePage.class);
                startActivity(intent);
            }
        });
        btnListGarageJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BidderPage.this, MyListGaragesPage.class);
                startActivity(intent);
            }
        });
        btnRegisterGarageJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BidderPage.this, RegisterGarage.class);
                startActivity(intent);
            }
        });
        btnRequestJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BidderPage.this, RequestBidder.class);
                startActivity(intent);
            }
        });

    }
}
