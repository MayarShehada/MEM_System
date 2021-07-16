package com.birzeit.memsystem.Doctor;

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
import com.birzeit.memsystem.Models.DoctorNotification;
import com.birzeit.memsystem.MySingleton;
import com.birzeit.memsystem.R;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CheckInfoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private TextView heartBeat_txt;
    private TextView bodyTemp_txt;
    private TextView bloodPressure_txt;
    private TextView dateOfCheck_txt;

    public int checkId;
    public String heartBeat, bodyTemp, bloodPressure, dateOfCheck;

    public TextView name_txt, email_txt, counter;
    public String fullname="", email="", role="";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    List<DoctorNotification> notificationDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_info);

        setupViews();
        setupIntent();

        notificationDoctor = new ArrayList<>();

        setupNavigation();
        updateNavHeader();

        getNotificationNumber();
    }

    private void setupViews() {

        heartBeat_txt = findViewById(R.id.heartBeat_txt);
        bodyTemp_txt = findViewById(R.id.bodyTemp_txt);
        bloodPressure_txt = findViewById(R.id.bloodPressure_txt);
        dateOfCheck_txt = findViewById(R.id.dateOfCheck_txt);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.nav_menu);
        toolbar = findViewById(R.id.toolbar);

        counter = findViewById(R.id.counter);
    }

    public void notification_btn_Action(View view){
        Intent intent = new Intent(CheckInfoActivity.this, DoctorNotificationActivity.class);
        intent.putExtra("fullnameData", fullname);
        intent.putExtra("emailData", email);
        startActivity(intent);
        finish();
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

    public void getNotificationNumber(){

        String URL2 = "http://192.168.1.28/MEM_System/getDoctorNotificationJson.php?doctorName="+ fullname ;

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, URL2, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray ja = response.getJSONArray("result");

                            DoctorNotification noti;

                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jsonObject = ja.getJSONObject(i);
                                String checkid = jsonObject.getString("id");
                                String title = jsonObject.getString("title");
                                String doctorName = jsonObject.getString("doctorName");
                                String patientName = jsonObject.getString("patientName");
                                String checkId = jsonObject.getString("checkId");
                                String date = jsonObject.getString("date");

                                if(fullname.equals(doctorName)){
                                    noti = new DoctorNotification(checkid, title, doctorName, patientName,checkId, date);
                                    notificationDoctor.add(noti);
                                }
                            }
                            counter.setText(notificationDoctor.size() + " ");

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

    public void setupNavigation(){
        //ToolBar
        setSupportActionBar(toolbar);

        //NavigationDrawer Menu
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(CheckInfoActivity.this,
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

            Intent intent = new Intent(CheckInfoActivity.this, DoctorHomeActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_profile) {
            Intent intent = new Intent(CheckInfoActivity.this, DoctorProfileActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_listOfPatients) {
            Intent intent = new Intent(CheckInfoActivity.this, ListOfPatientActivity.class);;
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();
        }else if(item.getItemId() == R.id.nav_listOfParamedic){
            Intent intent = new Intent(CheckInfoActivity.this, ListOfParamedicActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();
        }else if(item.getItemId() == R.id.nav_setting){
            Intent intent = new Intent(CheckInfoActivity.this, EditDoctorInfoActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_logOut){
            Intent intent = new Intent(CheckInfoActivity.this, LoginActivity.class);
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
        role=intent.getStringExtra("roleData");

        name_txt.setText(fullname);
        email_txt.setText(email);
    }
}