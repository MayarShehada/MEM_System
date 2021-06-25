package com.birzeit.memsystem.Doctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.birzeit.memsystem.R;
import com.google.android.material.navigation.NavigationView;

public class PatientInfoHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    TextView prof_name_txt;

    public TextView name_txt, email_txt;
    public String fullname="", email = "";
    public String pa_id, pa_name, pa_email, pa_phone, pa_gender, pa_address, pa_iotIP, pa_mac, pa_relative1, pa_relative2;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info_home);

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
    }

    public void setupNavigation(){
        //ToolBar
        setSupportActionBar(toolbar);

        //NavigationDrawer Menu
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(PatientInfoHomeActivity.this,
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

            Intent intent = new Intent(PatientInfoHomeActivity.this, DoctorHomeActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_profile) {
            Intent intent = new Intent(PatientInfoHomeActivity.this, DoctorProfileActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_listOfPatients){
            Intent intent = new Intent(PatientInfoHomeActivity.this, ListOfPatientActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();
        }else if(item.getItemId() == R.id.nav_listOfParamedic){
            Intent intent = new Intent(PatientInfoHomeActivity.this, ListOfParamedicActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();
        }else if(item.getItemId() == R.id.nav_setting){
            Intent intent = new Intent(PatientInfoHomeActivity.this, EditDoctorInfoActivity.class);
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

    public void check_details_btn_action(View view) {
        pa_id = getIntent().getStringExtra("patientId_data");
        pa_name = getIntent().getStringExtra("Doctor_NameData");
        pa_email = getIntent().getStringExtra("Doctor_EmailData");
        Intent intent = new Intent(PatientInfoHomeActivity.this, DoctorListOfChecksActivity.class);
        intent.putExtra("fullnameData", fullname);
        intent.putExtra("emailData", email);
        intent.putExtra("patientId_data", pa_id);
        startActivity(intent);
        finish();
    }

    public void patient_details_btn_action(View view) {

        pa_id = getIntent().getStringExtra("patientId_data");
        pa_name = getIntent().getStringExtra("Doctor_NameData");
        pa_email = getIntent().getStringExtra("Doctor_EmailData");
        Intent intent = new Intent(PatientInfoHomeActivity.this, PatientInfoActivity.class);
        intent.putExtra("fullnameData", fullname);
        intent.putExtra("emailData", email);
        intent.putExtra("patientId_data", pa_id);
        intent.putExtra("patientFullName_data", pa_name);
        intent.putExtra("email_data", pa_email);
        intent.putExtra("phonenum_data", pa_phone);
        intent.putExtra("gender_data", pa_gender);
        intent.putExtra("address_data", pa_address);
        intent.putExtra("iotIp_data", pa_iotIP);
        intent.putExtra("iotMac_data", pa_mac);
        intent.putExtra("relative1_data", pa_relative1);
        intent.putExtra("relative2_data", pa_relative2);

        startActivity(intent);
        finish();

    }
}