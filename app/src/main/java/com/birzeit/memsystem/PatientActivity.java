package com.birzeit.memsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PatientActivity extends AppCompatActivity {

    private TextView id_txt;
    private TextView fullname_txt;
    private TextView email_txt;
    private TextView phonenum_txt;
    private TextView gender_txt;
    private TextView address_txt;
    private TextView relative1_txt;
    private TextView relative2_txt;

    public int patientId;
    public String fullname,email,phonenum,gender,address,relative1,relative2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        setupViews();
        setupIntent();
    }

    private void setupViews() {

//        id_txt = findViewById(R.id.id_txt);
//        fullname_txt = findViewById(R.id.fullname_txt);
//        email_txt = findViewById(R.id.email_txt);
//        phonenum_txt = findViewById(R.id.phonenum_txt);
//        gender_txt = findViewById(R.id.gender_txt);
//        address_txt = findViewById(R.id.address_txt);
//        relative1_txt = findViewById(R.id.relative1_txt);
//        relative2_txt = findViewById(R.id.relative2_txt);

    }

    private void setupIntent(){
        Intent intent = getIntent();

        patientId = intent.getIntExtra("checkId_data", 0);
        fullname = intent.getStringExtra("heartBeat_data");
        email = intent.getStringExtra("bodyTemp_data");
        phonenum = intent.getStringExtra("bloodPressure_data");
        gender = intent.getStringExtra("dateOfCheck_data");
        address = intent.getStringExtra("dateOfCheck_data");
        relative1 = intent.getStringExtra("dateOfCheck_data");
        relative2 = intent.getStringExtra("dateOfCheck_data");

        id_txt.setText(patientId+"");
        fullname_txt.setText(fullname);
        email_txt.setText(email);
        phonenum_txt.setText(phonenum);
        gender_txt.setText(gender);
        address_txt.setText(address);
        relative1_txt.setText(relative1);
        relative2_txt.setText(relative2);

    }
}