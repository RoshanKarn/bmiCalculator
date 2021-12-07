package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class datainput extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText heightEditText, weightEditText;
    private Spinner genderSpinner;
    private FirebaseAuth mAuth;
    private Button calculate;
    Double bmiCalculated;
    public String bmiCategory;
    private static final String SHARED_PREF_NAME= "userInputData";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datainput);
        heightEditText= (EditText) findViewById(R.id.heightEditText);
        weightEditText= (EditText) findViewById(R.id.weightEditText);
        genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        calculate = (Button) findViewById(R.id.calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(datainput.this, display.class);
                startActivity(intent);
                String heightStr = heightEditText.getText().toString();
                double height = Double.parseDouble(heightStr);
                String weightStr = weightEditText.getText().toString();
                double weight = Double.parseDouble(weightStr);
                bmiCalculated = weight / height * height;
                getIntent().putExtra("KeyCalculated", bmiCalculated);
                if (bmiCalculated < 18.5) {
                    bmiCategory = "UNDERWEIGHT";
                } else if (bmiCalculated >= 18.5 && bmiCalculated <= 24.9) {
                    bmiCategory = "NORMAL WEIGHT";
                } else if (bmiCalculated >= 25 && bmiCalculated <= 29.9) {
                    bmiCategory = "OVERWEIGHT";
                } else {
                    bmiCategory = "OBESITY";
                }
                getIntent().putExtra("KeyCategory", bmiCategory);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.GENDER, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);
        genderSpinner.setOnItemSelectedListener(this);

        //calculate.setOnClickListener(View.OnClickListener);


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String gender = genderSpinner.getSelectedItem().toString().trim();
        getIntent().putExtra("gender", gender);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();

    }
}