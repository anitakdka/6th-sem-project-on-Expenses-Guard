package com.anita.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class inc_emi extends AppCompatActivity {
    Button b1;
    Button b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);// To Display The Layout from activy_main resource layout file.
        setContentView(R.layout.activity_inc_emi);
        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);

        ImageView back_arrow=findViewById(R.id.back);
        back_arrow.setOnClickListener(v -> onBackPressed());

        b1.setOnClickListener(view -> {
            Intent intent= new Intent(inc_emi.this, income2.class);
            startActivity(intent); // On clicking on the button, Income Tax Calculator activity is called
        });

        b2.setOnClickListener(view -> {
            Intent intent=new Intent(inc_emi.this, emi.class);
            startActivity(intent);// On clicking on the button, EMI activity is called
        });
    }

}