package com.anita.myapplication;


import static java.lang.Double.parseDouble;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NextIncome extends AppCompatActivity {
    Button go;
    EditText e1,e2,e3;
    String base,LTA,HRA,SA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_income);
        e1= findViewById(R.id.edit1);
        e2= findViewById(R.id.edit2);
        e3= findViewById(R.id.edit3);
        Intent i=getIntent();
        base=i.getStringExtra("baseincome");
        LTA=i.getStringExtra("LTA");
        HRA=i.getStringExtra("HRA");
        SA=i.getStringExtra("SA");

        go= findViewById(R.id.go);
        go.setOnClickListener(view -> finish());

    }
    public void finish(){
        parseDouble(e1.getText().toString());
        parseDouble(e2.getText().toString());
        Intent i=new Intent(NextIncome.this,Finalinc.class);
        i.putExtra("80A",e1.getText().toString());
        i.putExtra("hra",HRA);
        i.putExtra("lta",LTA);
        i.putExtra("sa",SA);
        i.putExtra("80B",e2.getText().toString());
        i.putExtra("80C",e3.getText().toString());
        startActivity(i);

    }
}

