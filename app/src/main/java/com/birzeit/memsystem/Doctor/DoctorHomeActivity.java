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
import androidx.recyclerview.widget.RecyclerView;

import com.birzeit.memsystem.Models.PatientList;
import com.birzeit.memsystem.R;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DoctorHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView check_recycle;
    List<PatientList> checkList;
    String doctorName = "";

    public TextView name_txt, email_txt;

    public String fullname="", email="", role="";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);

        Intent intent = getIntent();
        doctorName = intent.getStringExtra("fullnameData");

        setupViews();
        setupNavigation();
        updateNavHeader();

    }

    private void setupViews() {
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.nav_menu);
        toolbar = findViewById(R.id.toolbar);
    }

    public void setupNavigation(){
        //ToolBar
        setSupportActionBar(toolbar);

        //NavigationDrawer Menu
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(DoctorHomeActivity.this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_listOfChecks);
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
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        if(item.getItemId() == R.id.nav_home){

            Intent intent = new Intent(DoctorHomeActivity.this, DoctorHomeActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_profile) {
            Intent intent = new Intent(DoctorHomeActivity.this, DoctorProfileActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_listOfPatients) {
            Intent intent = new Intent(DoctorHomeActivity.this, ListOfPatientActivity.class);;
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();
        }else if(item.getItemId() == R.id.nav_listOfParamedic){
                Intent intent = new Intent(DoctorHomeActivity.this, ListOfParamedicActivity.class);
                intent.putExtra("fullnameData", fullname);
                intent.putExtra("emailData", email);
                startActivity(intent);
                finish();
        }else if(item.getItemId() == R.id.nav_setting){
            Intent intent = new Intent(DoctorHomeActivity.this, EditDoctorInfoActivity.class);
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

        Intent intent = getIntent();
        fullname = intent.getStringExtra("fullnameData");
        email = intent.getStringExtra("emailData");
        role=intent.getStringExtra("roleData");

        name_txt.setText(fullname);
        email_txt.setText(email);
    }

    public void emergency_stuff_list_btn_Action(View view) {
    }

    public void patient_list_btn_Action(View view) {
        Intent intent = new Intent(DoctorHomeActivity.this, ListOfPatientActivity.class);
        intent.putExtra("fullnameData", fullname);
        intent.putExtra("emailData", email);
        startActivity(intent);
        finish();
    }
}