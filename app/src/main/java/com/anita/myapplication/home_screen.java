package com.anita.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class home_screen extends AppCompatActivity {
    private EditText mEmail;
    private EditText mPass;
    private CheckBox remember;

    private ProgressDialog mDialog;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        mAuth=FirebaseAuth.getInstance();
        mDialog=new ProgressDialog(this);

        loginDetails();
    }

    private void loginDetails() {
        mEmail = findViewById(R.id.email_login);
        mPass = findViewById(R.id.password_login);
        Button btnLogin = findViewById(R.id.btn_login);
        TextView mforget_password = findViewById(R.id.forgot_password);
        TextView mSignUpHere = findViewById(R.id.signup_reg);
        remember=findViewById(R.id.checkBox2);

        SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox=preferences.getString("remember","");
        if(checkbox.equals("true"))
        {
            Intent intent=new Intent(home_screen.this,first_home_page.class);
            startActivity(intent);
        }

        remember.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(buttonView.isChecked())
            {
                SharedPreferences preferences1 =getSharedPreferences("checkbox",MODE_PRIVATE);
                SharedPreferences.Editor editor= preferences1.edit();
                editor.putString("remember","true");
                editor.apply();
                Toast.makeText(home_screen.this,"Remember me Checked..",Toast.LENGTH_SHORT).show();
            }
            else if(!buttonView.isChecked())
            {
                SharedPreferences preferences1 =getSharedPreferences("checkbox",MODE_PRIVATE);
                SharedPreferences.Editor editor= preferences1.edit();
                editor.putString("remember","false");
                editor.apply();
                Toast.makeText(home_screen.this,"Remember me Unchecked..",Toast.LENGTH_SHORT).show();
            }
        });

        mSignUpHere.setOnClickListener(v -> {
            Intent intent= new Intent(home_screen.this,Registration.class);
            startActivity(intent);
        });

        mforget_password.setOnClickListener(v -> {
            Intent intent= new Intent(home_screen.this,resetpassword.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> {
            String email = mEmail.getText().toString().trim();
            String pass = mPass.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                mEmail.setError("Email  Required..",null);
                return;
            }
            if (TextUtils.isEmpty(pass)) {
                mPass.setError("Password Required..",null);
                return;
            }
            mDialog.setMessage("Logging in..");
            mDialog.show();

            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    mDialog.dismiss();
                    checkEmailVerification();
                }
                else
                {
                    mDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Login Failed..",Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
    private void checkEmailVerification()
    {
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag= null;
        if (firebaseUser != null) {
            emailflag = firebaseUser.isEmailVerified();
        }
        if(emailflag)
        {
            finish();
            Toast.makeText(getApplicationContext(),"Login Successful..",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(home_screen.this,first_home_page.class));
        }
        else
        {
            Toast.makeText(this,"Please verify your email..",Toast.LENGTH_LONG).show();
            mAuth.signOut();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}