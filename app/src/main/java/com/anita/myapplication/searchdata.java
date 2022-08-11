package com.anita.myapplication;



import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Model.Data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class searchdata extends AppCompatActivity {

    DatabaseReference mref;
    private ListView listdata;
    private AutoCompleteTextView txtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchdata);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser= mAuth.getCurrentUser();


        String uid = null;
        if (mUser != null) {
            uid = mUser.getUid();
        }
        //mref= FirebaseDatabase.getInstance().getReference("IncomeData");
        if (uid != null) {
            mref = FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid);
        }


        listdata= findViewById(R.id.listdata);
        txtSearch= findViewById(R.id.txtSearch);

        ValueEventListener event=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                populateSearch(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        mref.addListenerForSingleValueEvent(event);

        ImageView back_arrow=findViewById(R.id.back);
        back_arrow.setOnClickListener(v -> onBackPressed());

    }

    private void populateSearch(DataSnapshot snapshot) {

        Log.d("IncomeData","Reading Data");
        ArrayList<String> names=new ArrayList<>();

        if(snapshot.exists()){
            for(DataSnapshot ds:snapshot.getChildren()){
                Data data=ds.getValue(Data.class);
                String name= null;
                if (data != null) {
                    name = data.getDate();
                }

                names.add(name);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,names);

            txtSearch.setAdapter(adapter);

            txtSearch.setOnItemClickListener((parent, view, position, id) -> {
                String name=txtSearch.getText().toString();
                searchType(name);
            });

        }else{
            Log.d("IncomeData","No data Found");
        }

    }

    private void searchType(String name) {

        Query query=mref.orderByChild("date").equalTo(name);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    ArrayList<String> listtype=new ArrayList<>();
                    for(DataSnapshot ds:snapshot.getChildren()){
                        Data data=ds.getValue(Data.class);
                        //listtype.add(data.getType());
                        String strIncome= null;
                        if (data != null) {
                            strIncome = String.valueOf(data.getAmount());
                        }
                        if (data != null) {
                            listtype.add(strIncome+"\n"+data.getType()+"\n"+data.getNote()+"\n"+data.getDate());
                        }
                    }

                    ArrayAdapter<String> adapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,listtype);
                    listdata.setAdapter(adapter);

                }else{
                    Log.d("IncomeData","No data found");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
