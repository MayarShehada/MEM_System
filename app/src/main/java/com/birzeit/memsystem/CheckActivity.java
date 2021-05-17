package com.birzeit.memsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CheckActivity extends AppCompatActivity {

    private TextView heartBeat_txt;
    private TextView bodyTemp_txt;
    private TextView bloodPressure_txt;
    private TextView dateOfCheck_txt;

    public int checkId;
    public String heartBeat, bodyTemp, bloodPressure, dateOfCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        setupViews();
        setupIntent();
    }

    private void setupViews() {

        heartBeat_txt = findViewById(R.id.heartBeat_txt);
        bodyTemp_txt = findViewById(R.id.bodyTemp_txt);
        bloodPressure_txt = findViewById(R.id.bloodPressure_txt);
        dateOfCheck_txt = findViewById(R.id.dateOfCheck_txt);
    }

    private void setupIntent(){
        Intent intent = getIntent();

        checkId = intent.getIntExtra("checkId_data", 0);
        heartBeat = intent.getStringExtra("heartBeat_data");
        bodyTemp = intent.getStringExtra("bodyTemp_data");
        bloodPressure = intent.getStringExtra("bloodPressure_data");
        dateOfCheck = intent.getStringExtra("dateOfCheck_data");

        heartBeat_txt.setText(heartBeat);
        bodyTemp_txt.setText(bodyTemp);
        bloodPressure_txt.setText(bloodPressure);
        dateOfCheck_txt.setText(dateOfCheck);
    }
}