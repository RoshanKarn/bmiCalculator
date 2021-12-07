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
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class signUp extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText nameEditText, ageEditText, emailEditText, passwordEditText;
    private Button signup;
    private TextView backToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        nameEditText =(EditText) findViewById(R.id.nameEditText);
        ageEditText =(EditText) findViewById(R.id.ageEditText);
        emailEditText = (EditText) findViewById(R.id.signupEmailEditText);
        passwordEditText= (EditText) findViewById(R.id.singupPassEditText);
        signup= (Button) findViewById(R.id.signUpButton);
        signup.setOnClickListener(this);
        backToLogin = (TextView) findViewById(R.id.backToLogin);
        backToLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backToLogin:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.signUpButton:
                registerUser();
                break;

        }

    }

    private void registerUser() {
        String  email = emailEditText.getText().toString().trim();
        String password= passwordEditText.getText().toString().trim();
        String age= ageEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();

        if (name.isEmpty()){
            nameEditText.setError("Name is required");
            nameEditText.requestFocus();
            return;
        }
        if (age.isEmpty()){
            ageEditText.setError("Name is required");
            ageEditText.requestFocus();
            return;
        }
        if (password.isEmpty()){
            passwordEditText.setError("Name is required");
            passwordEditText.requestFocus();
            return;
        }
        if (email.isEmpty()){
            emailEditText.setError("Name is required");
            emailEditText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Please provide valid Email address");
            emailEditText.requestFocus();
            return;
        }
        if (password.length() <6){
            passwordEditText.setError("Password should be atleast 6 characters long");
            passwordEditText.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            userData user = new userData(name,age,email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(signUp.this, "you're singed up, now go back to login"
                                        ,Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(signUp.this, "Signup Failed, Please try again",Toast
                                        .LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(signUp.this, "Signup Failed, Please try again",Toast
                                    .LENGTH_LONG).show();
                        }

                    }
                });
    }
}