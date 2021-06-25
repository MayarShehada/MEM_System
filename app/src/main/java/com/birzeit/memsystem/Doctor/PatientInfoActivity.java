package com.birzeit.memsystem.Doctor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.birzeit.memsystem.R;
import com.google.android.material.navigation.NavigationView;

public class PatientInfoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public TextView prof_name_txt, prof_email_txt, prof_phone_txt, prof_gender_txt, prof_address_txt, prof_relative1_txt, prof_relative2_txt;
    public TextView name_txt, email_txt;
    public String pa_id, pa_name, pa_email, pa_phone, pa_gender, pa_address, pa_iotIP, pa_mac, pa_relative1, pa_relative2;

    public String fullname="", email = "";

    public Button pCall_btn , rCall_btn , rrCall_btn ;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private static final int REQUEST_CALL = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);

        setupViews();
        loadData();

        Intent intent = getIntent();
        fullname = intent.getStringExtra("Doctor_NameData");
        email = intent.getStringExtra("Doctor_EmailData");

        setupNavigation();
        updateNavHeader();
    }

    private void setupViews() {

        prof_name_txt = findViewById(R.id.prof_name_txt);
        prof_email_txt = findViewById(R.id.prof_email_txt);
        prof_phone_txt = findViewById(R.id.prof_phone_txt);
        prof_gender_txt = findViewById(R.id.prof_gender_txt);
        prof_address_txt=findViewById(R.id.prof_address_txt);
        prof_relative1_txt = findViewById(R.id.prof_relative1_txt);
        prof_relative2_txt = findViewById(R.id.prof_relative2_txt);

        pCall_btn=findViewById(R.id.pCall_btn);
        rCall_btn=findViewById(R.id.rCall_btn);
        rrCall_btn=findViewById(R.id.rrCall_btn);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.nav_menu);
        toolbar = findViewById(R.id.toolbar);

    }

    public void loadData(){
        Intent intent = getIntent();

        pa_id = intent.getStringExtra("patientId_data");
        pa_name = intent.getStringExtra("patientFullName_data");
        pa_email = intent.getStringExtra("email_data");
        pa_phone = intent.getStringExtra("phonenum_data");
        pa_gender = intent.getStringExtra("gender_data");
        pa_address = intent.getStringExtra("address_data");
        pa_iotIP = intent.getStringExtra("iotIp_data");
        pa_mac = intent.getStringExtra("iotMac_data");
        pa_relative1 = intent.getStringExtra("relative1_data");
        pa_relative2 = intent.getStringExtra("relative2_data");

        prof_name_txt.setText(pa_name);
        prof_email_txt.setText(pa_email);
        prof_phone_txt.setText(pa_phone);
        prof_gender_txt.setText(pa_gender);
        prof_address_txt.setText(pa_address);
        prof_relative1_txt.setText(pa_relative1);
        prof_relative2_txt.setText(pa_relative2);
    }

    public void setupNavigation(){
        //ToolBar
        setSupportActionBar(toolbar);

        //NavigationDrawer Menu
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(PatientInfoActivity.this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_listOfPatients);
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.nav_home){

            Intent intent = new Intent(PatientInfoActivity.this, DoctorHomeActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_profile) {
            Intent intent = new Intent(PatientInfoActivity.this, DoctorProfileActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_listOfPatients){
            Intent intent = new Intent(PatientInfoActivity.this, ListOfPatientActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();
        }else if(item.getItemId() == R.id.nav_listOfParamedic){
            Intent intent = new Intent(PatientInfoActivity.this, ListOfParamedicActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();
        }else if(item.getItemId() == R.id.nav_setting){
            Intent intent = new Intent(PatientInfoActivity.this, EditDoctorInfoActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_logOut){

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateNavHeader() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_menu);
        View headerView = navigationView.getHeaderView(0);
        name_txt = headerView.findViewById(R.id.name_txt);
        email_txt = headerView.findViewById(R.id.email_txt);

        name_txt.setText(fullname);
        email_txt.setText(email);
    }

    public void call_relative2_BtnAction(View view) {
        makePhoneCall();
    }

    public void call_relative1_BtnAction(View view) {
        makePhoneCall();
    }

    public void call_patient_BtnAction(View view) { makePhoneCall(); }

    private void makePhoneCall() {

        pa_phone = getIntent().getStringExtra("phonenum_data");
        pa_relative1 = getIntent().getStringExtra("relative1_data");
        pa_relative2 = getIntent().getStringExtra("relative2_data");
        String number = "";
        if(pCall_btn.isClickable())
        {
            number=pa_phone;
        }else if(rCall_btn.isClickable()){
            number=pa_relative1;
        }else {
            number=pa_relative2;
        }
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(PatientInfoActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(PatientInfoActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        } else {
            Toast.makeText(PatientInfoActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

}