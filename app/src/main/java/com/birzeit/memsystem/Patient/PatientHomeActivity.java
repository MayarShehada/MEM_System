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
import com.birzeit.memsystem.LoginActivity;
import com.birzeit.memsystem.MySingleton;
import com.birzeit.memsystem.R;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PatientHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private TextView name_txt, email_txt;
    public String fullname, email, role, patientId="";;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

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
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(PatientHomeActivity.this,
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
            LoadData(PatientHomeActivity.class);

        }else if(item.getItemId() == R.id.nav_makeCheck){
            LoadData(MakeCheckActivity.class);

        }else if(item.getItemId() == R.id.nav_lastCheck){
            LoadData(LastCheckActivity.class);

        }else if(item.getItemId() == R.id.nav_listOfChecks){
            LoadData(ListOfChecksActivity.class);

        }else if(item.getItemId() == R.id.nav_listOfEmergency){
            LoadData(ListOfEmergencyActivity.class);

        }else if(item.getItemId() == R.id.nav_normalCase){
            LoadData(NormalCaseActivity.class);

        }else if(item.getItemId() == R.id.nav_profile){
            LoadData(PatientProfileActivity.class);

        }else if(item.getItemId() == R.id.nav_setting){
            LoadData(EditPatientInfoActivity.class);

        }else if(item.getItemId() == R.id.nav_logOut){
            Intent intent = new Intent(PatientHomeActivity.this, LoginActivity.class);
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

    public void make_check_BtnAction(View view) {
        LoadData(MakeCheckActivity.class);
    }

    public void last_check_details_btn_Action(View view) { LoadData(LastCheckActivity.class);}

    public void list_of_checks_btn_Action(View view) {
        LoadData(ListOfChecksActivity.class);
    }

    public void emergency_cases_BtnAction(View view) {
        LoadData(ListOfEmergencyActivity.class);
    }

    public void LoadData(Class activity)
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
                                Intent intent = new Intent(PatientHomeActivity.this, activity);
                                intent.putExtra("fullnameData", fullname);
                                intent.putExtra("emailData", email);
                                intent.putExtra("roleData",role);
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