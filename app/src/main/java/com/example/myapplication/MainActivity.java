package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView register;
    private EditText emailEditText, passwordEditText;
    private Button login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register= (TextView) findViewById(R.id.registerTextView);
        register.setOnClickListener(this);
        login= (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(this);
        emailEditText= (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText)  findViewById(R.id.editTextPassword);
        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registerTextView:
                startActivity(new Intent(this, signUp.class));
                break;
            case R.id.loginButton:
                userLogin();
                break;
        }


    }

    private void userLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        if (email.isEmpty()){
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Please provide a valid email address");
            emailEditText.requestFocus();
            return;
        }
        if (password.isEmpty()){
            passwordEditText.setError("password is required");
            passwordEditText.requestFocus();
            return;
        }
        if (password.length() <6){
            passwordEditText.setError("password must be 6 characters long");
            passwordEditText.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(MainActivity.this, datainput.class));
                } else {
                    Toast.makeText(MainActivity.this,"Failed to login please try again",Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}