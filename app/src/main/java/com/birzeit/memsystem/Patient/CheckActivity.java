package com.birzeit.memsystem.Patient;

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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.birzeit.memsystem.MySingleton;
import com.birzeit.memsystem.R;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private TextView heartBeat_txt;
    private TextView bodyTemp_txt;
    private TextView bloodPressure_txt;
    private TextView dateOfCheck_txt;

    public int checkId;
    public String heartBeat, bodyTemp, bloodPressure, dateOfCheck,fullname, email,patientId="";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private TextView name_txt, email_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        setupViews();
        setupIntent();
        setupNavigation();
        updateNavHeader();
    }

    private void setupViews() {

        heartBeat_txt = findViewById(R.id.heartBeat_txt);
        bodyTemp_txt = findViewById(R.id.bodyTemp_txt);
        bloodPressure_txt = findViewById(R.id.bloodPressure_txt);
        dateOfCheck_txt = findViewById(R.id.dateOfCheck_txt);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.nav_menu);
        toolbar = findViewById(R.id.toolbar);
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
    public void setupNavigation(){
        //ToolBar
        setSupportActionBar(toolbar);

        //NavigationDrawer Menu
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(CheckActivity.this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
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

            Intent intent = new Intent(CheckActivity.this, PatientHomeActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);

            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_lastCheck){
            Intent intent = new Intent(CheckActivity.this, LastCheckActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);

            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_listOfChecks){
            LoadData();

        }else if(item.getItemId() == R.id.nav_normalCase){
            Intent intent = new Intent(CheckActivity.this, NormalCaseActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);

            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_profile){
            Intent intent = new Intent(CheckActivity.this, PatientProfileActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);

            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_setting){

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


        name_txt.setText(fullname);
        email_txt.setText(email);
    }

    public void LoadData()
    {
        String cat = getIntent().getStringExtra("fullnameData");
        String url = "http://192.168.1.28:80/MEM_System/getID.php?fullname="+cat;


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url,
                        null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String[] days;

                        try {

                            JSONArray array = response.getJSONArray("result");
                            days = new String[array.length()];
                            for(int i = 0; i<array.length(); i++){
                                JSONObject obj = array.getJSONObject(i);
                                patientId  = obj.getString("id");
                                Intent intent = new Intent(CheckActivity.this, ListOfChecksActivity.class);
                                intent.putExtra("fullnameData", fullname);
                                intent.putExtra("emailData", email);
                                intent.putExtra("patientIdData", patientId);
                                startActivity(intent);
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

}