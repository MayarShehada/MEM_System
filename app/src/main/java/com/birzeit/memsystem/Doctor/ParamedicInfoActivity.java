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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParamedicInfoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public TextView prof_name_txt, prof_email_txt, prof_phone_txt, prof_gender_txt, prof_ambulanceId_txt;
    public TextView name_txt, email_txt, counter;
    public String pa_id, pa_name, pa_email, pa_phone, pa_gender, pa_ambulance_id;

    public String fullname="", email = "";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    List<DoctorNotification> notificationDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paramedic_info);

        setupViews();
        loadData();

        Intent intent = getIntent();
        fullname = intent.getStringExtra("doctorName_data");
        email = intent.getStringExtra("doctorEmail_data");

        notificationDoctor = new ArrayList<>();
        getNotificationNumber();

        setupNavigation();
        updateNavHeader();
    }

    private void setupViews() {

        prof_name_txt = findViewById(R.id.prof_name_txt);
        prof_email_txt = findViewById(R.id.prof_email_txt);
        prof_phone_txt = findViewById(R.id.prof_phone_txt);
        prof_gender_txt = findViewById(R.id.prof_gender_txt);
        prof_ambulanceId_txt=findViewById(R.id.prof_ambulance_id_txt);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.nav_menu);
        toolbar = findViewById(R.id.toolbar);
        counter = findViewById(R.id.counter);
    }

    public void loadData(){
        Intent intent = getIntent();

        pa_name = intent.getStringExtra("paramadicFullName_data");
        pa_email = intent.getStringExtra("email_data");
        pa_phone = intent.getStringExtra("phonenum_data");
        pa_gender = intent.getStringExtra("gender_data");
        pa_ambulance_id = intent.getStringExtra("ambulanceid_data");

        prof_name_txt.setText(pa_name);
        prof_email_txt.setText(pa_email);
        prof_phone_txt.setText(pa_phone);
        prof_gender_txt.setText(pa_gender);
        prof_ambulanceId_txt.setText(pa_ambulance_id);
    }

    public void notification_btn_Action(View view){
        Intent intent = new Intent(ParamedicInfoActivity.this, DoctorNotificationActivity.class);
        intent.putExtra("fullnameData", fullname);
        intent.putExtra("emailData", email);
        startActivity(intent);
        finish();
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
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(ParamedicInfoActivity.this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_listOfParamedic);
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

            Intent intent = new Intent(ParamedicInfoActivity.this, DoctorHomeActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_profile) {
            Intent intent = new Intent(ParamedicInfoActivity.this, DoctorProfileActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_listOfPatients){
            Intent intent = new Intent(ParamedicInfoActivity.this, ListOfPatientActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();
        }else if(item.getItemId() == R.id.nav_listOfParamedic){
            Intent intent = new Intent(ParamedicInfoActivity.this, ListOfParamedicActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();
        }else if(item.getItemId() == R.id.nav_setting){
            Intent intent = new Intent(ParamedicInfoActivity.this, EditDoctorInfoActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_logOut){
            Intent intent = new Intent(ParamedicInfoActivity.this, LoginActivity.class);
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

}