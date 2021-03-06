package com.birzeit.memsystem.Paramedic;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.birzeit.memsystem.LoginActivity;
import com.birzeit.memsystem.R;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class EditInformationParamedicActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public EditText name_edt, email_edt, phone_edt, gender_edt, ambulanceID_edt;
    public TextView name_txt, email_txt;

    public String fullname="", email = "";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information_paramedic);

        setupViews();
        Intent intent = getIntent();
        fullname = intent.getStringExtra("fullnameData");
        email = intent.getStringExtra("emailData");

        setupNavigation();
        updateNavHeader();
    }

    private void setupViews() {

        name_edt = findViewById(R.id.name_edt);
        email_edt = findViewById(R.id.email_edt);
        phone_edt = findViewById(R.id.phone_edt);
        gender_edt = findViewById(R.id.gender_edt);
        ambulanceID_edt = findViewById(R.id.ambulanceID_edt);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.nav_menu);
        toolbar = findViewById(R.id.toolbar);
    }

    public void setupNavigation(){
        //ToolBar
        setSupportActionBar(toolbar);

        //NavigationDrawer Menu
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(EditInformationParamedicActivity.this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_setting);
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

            Intent intent = new Intent(EditInformationParamedicActivity.this, ParamedicHomeActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_profile){
            Intent intent = new Intent(EditInformationParamedicActivity.this, ParamedicProfileActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_map){
            Intent intent = new Intent(EditInformationParamedicActivity.this, MapActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_gpsTrack){
            Intent intent = new Intent(EditInformationParamedicActivity.this, GPS_TrackingActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_setting){
            Intent intent = new Intent(EditInformationParamedicActivity.this, EditParamedicInfoActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_logOut){
            Intent intent = new Intent(EditInformationParamedicActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
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

    public void saveChanges_Btn_OnClick(View view) {
    }
}