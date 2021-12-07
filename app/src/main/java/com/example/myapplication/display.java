package com.example.myapplication;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class display extends AppCompatActivity {
    private TextView nameTextView, bmiValueTextView, bmiCategoryTextView;
    private Button logout;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    ConstraintLayout constraintLayout;

    public display() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        bmiValueTextView= (TextView) findViewById(R.id.bmiValueTextView);
        bmiCategoryTextView= (TextView) findViewById(R.id.bmiCategoryTextView);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userData userName = snapshot.getValue(userData.class);
                if ((userName != null)){
                    String name= userName.name;
                    nameTextView.setText("Hi "+name);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(display.this,"Something is wrong, please try again",Toast.LENGTH_LONG).show();

            }
        });
        String bmiDisp = getIntent().getStringExtra("KeyCalculated");
        bmiValueTextView.setText("Your BMI = "+bmiDisp);

        String gendervalue = getIntent().getStringExtra("gender");
        if (gendervalue.equals("MALE")){
            constraintLayout.setBackgroundColor(Color.CYAN);
        }
        else if (gendervalue.equals("FEMALE")){
            constraintLayout.setBackgroundColor(Color.MAGENTA);
        }else{
            constraintLayout.setBackgroundResource(R.drawable.lgbtq);
        }
        String bmiCatDisp = getIntent().getStringExtra("KeyCategory");
        bmiCategoryTextView.setText("You are in the\t "+bmiCatDisp+"\ncategory");

        }


    }