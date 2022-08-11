package com.anita.myapplication;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class about extends AppCompatActivity {

    TextView vname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        vname=findViewById(R.id.vname);
        vname.setText("v"+String.valueOf(BuildConfig.VERSION_NAME));

        ImageView back_arrow=findViewById(R.id.back);
        back_arrow.setOnClickListener(v -> onBackPressed());
    }
}