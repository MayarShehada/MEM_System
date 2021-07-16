package com.birzeit.memsystem.Patient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.birzeit.memsystem.LoginActivity;
import com.birzeit.memsystem.Models.Check;
import com.birzeit.memsystem.MySingleton;
import com.birzeit.memsystem.R;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LastCheckActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private TextView heartBeat_txt, bodyTemp_txt, bloodPressure_txt, dateOfCheck_txt;
    private TextView name_txt, email_txt;
    public String fullname, email, role = "", patientId="";

    List<Check> lastCheck;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_check);

        setupViews();
        setupNavigation();
        updateNavHeader();

        patientId = getIntent().getStringExtra("patientIdData");

        lastCheck = new ArrayList<>();
        loadData();

    }

    public void setupViews() {

        heartBeat_txt = findViewById(R.id.heartBeat_txt);
        bodyTemp_txt = findViewById(R.id.bodyTemp_txt);
        bloodPressure_txt = findViewById(R.id.bloodPress_txt);
        dateOfCheck_txt = findViewById(R.id.dateTime_txt);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.nav_menu);
        toolbar = findViewById(R.id.toolbar);
    }

    public void setupNavigation(){
        //ToolBar
        setSupportActionBar(toolbar);

        //NavigationDrawer Menu
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(LastCheckActivity.this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_lastCheck);
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    private void setupTexts(){

        String heart = "";
        String temp = "";
        String pressure = "";
        String date = "";

        heart = lastCheck.get(0).getHeartBeat();
        temp =  lastCheck.get(0).getBodyTemp();
        pressure = lastCheck.get(0).getBloodPressure();
        date = lastCheck.get(0).getDateOfCheck();

        heartBeat_txt.setText(heart);
        bodyTemp_txt.setText(temp);
        bloodPressure_txt.setText(pressure);
        dateOfCheck_txt.setText(date);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        if(item.getItemId() == R.id.nav_home){
            Intent intent = new Intent(LastCheckActivity.this, PatientHomeActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            intent.putExtra("roleData",role);
            intent.putExtra("patientIdData",patientId);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_makeCheck){
            Intent intent = new Intent(LastCheckActivity.this, MakeCheckActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            intent.putExtra("roleData",role);
            intent.putExtra("patientIdData",patientId);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_lastCheck){
            Intent intent = new Intent(LastCheckActivity.this, LastCheckActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            intent.putExtra("roleData",role);
            intent.putExtra("patientIdData",patientId);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_listOfChecks){
            Intent intent = new Intent(LastCheckActivity.this, ListOfChecksActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            intent.putExtra("roleData",role);
            intent.putExtra("patientIdData",patientId);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_listOfEmergency){
            Intent intent = new Intent(LastCheckActivity.this, ListOfEmergencyActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            intent.putExtra("roleData",role);
            intent.putExtra("patientIdData",patientId);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_normalCase){
            Intent intent = new Intent(LastCheckActivity.this, NormalCaseActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            intent.putExtra("roleData",role);
            intent.putExtra("patientIdData",patientId);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_profile){
            Intent intent = new Intent(LastCheckActivity.this, PatientProfileActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            intent.putExtra("roleData",role);
            intent.putExtra("patientIdData",patientId);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_setting){
            Intent intent = new Intent(LastCheckActivity.this, EditPatientInfoActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            intent.putExtra("roleData",role);
            intent.putExtra("patientIdData",patientId);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_logOut){
            Intent intent = new Intent(LastCheckActivity.this, LoginActivity.class);
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

        Intent intent = getIntent();
        fullname = intent.getStringExtra("fullnameData");
        email = intent.getStringExtra("emailData");
        role = intent.getStringExtra("roleData");

        name_txt.setText(fullname);
        email_txt.setText(email);
    }

    private void loadData() {
        String URL = "http://192.168.1.28:80/MEM_System/lastCheck.php?cat="+ patientId ;

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray ja = response.getJSONArray("result");

                            Check check;

                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jsonObject = ja.getJSONObject(i);
                                String checkid = jsonObject.getString("checkId");
                                String hertBeat = jsonObject.getString("heartBeat");
                                String bodyTemp = jsonObject.getString("bodyTemp");
                                String bloodPressure = jsonObject.getString("bloodPressure");
                                String location = jsonObject.getString("location");
                                String dateOfCheck = jsonObject.getString("dateOfCheck");
                                String flag = jsonObject.getString("flag");
                                String patient_id = jsonObject.getString("patientId");

                                if(patientId.equals(patient_id)){
                                    check = new Check(Integer.parseInt(checkid), hertBeat, bodyTemp, bloodPressure,location, dateOfCheck,role, flag,fullname,email);
                                    lastCheck.add(check);

                                    setupTexts();
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "Error");
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(jor);
    }
}