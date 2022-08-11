package com.anita.myapplication;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Profile extends AppCompatActivity {

    Button changepass;
    Button deleteaccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser= mAuth.getCurrentUser();
        String uid = null;
        if (mUser != null) {
            uid = mUser.getUid();
        }
        DatabaseReference mUserInfoDatabase = null;
        if (uid != null) {
            mUserInfoDatabase = FirebaseDatabase.getInstance().getReference().child("UserInfo").child(uid);
        }
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userEmail = Objects.requireNonNull(dataSnapshot.child("Email").getValue()).toString();
                TextView email=findViewById(R.id.email_profile);
                email.setText(userEmail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };
        if (mUserInfoDatabase != null) {
            mUserInfoDatabase.addListenerForSingleValueEvent(valueEventListener);
        }

        changepass=findViewById(R.id.btn_changepass);
        deleteaccount=findViewById(R.id.btn_deleteaccount);

        changepass.setOnClickListener(v -> {
            Intent intent=new Intent(Profile.this,change_password.class);
            startActivity(intent);
        });

        deleteaccount.setOnClickListener(v -> {
            AlertDialog.Builder dialog=new AlertDialog.Builder(Profile.this);
            dialog.setTitle("Are you sure?");
            dialog.setMessage("Deleting this account will result in completely removing your account from expenses guard and you will no longer be able to access this account. " +
                    "In future if you wish to use the same email then you need to register again.");
            dialog.setPositiveButton("DELETE", (dialog1, which) -> {
                if (mUser != null) {
                    mUser.delete().addOnCompleteListener(task -> {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Profile.this,"Account Deleted Successfully..",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(Profile.this,home_screen.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(Profile.this, Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
            dialog.setNegativeButton("NO", (dialog12, which) -> dialog12.dismiss());
            AlertDialog alertDialog=dialog.create();
            alertDialog.show();
        });

        ImageView back_arrow=findViewById(R.id.back);
        back_arrow.setOnClickListener(v -> onBackPressed());
    }
}