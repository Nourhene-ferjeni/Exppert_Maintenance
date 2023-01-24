package com.example.appmaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Choix extends AppCompatActivity {
    Button btnClient,btnContrat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix);

        btnClient=(Button) findViewById(R.id.btnClient);
        btnContrat=(Button) findViewById(R.id.btnContrat);

        btnClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Client.class));
            }
        });
        btnContrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Contrat.class));
            }
        });
    }
}