package com.birzeit.memsystem.Doctor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.birzeit.memsystem.LoginActivity;
import com.birzeit.memsystem.Models.Check;
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

public class DoctorNotificationHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public TextView title_txt, heartBeat_txt, bodyTemp_txt, bloodPressure_txt, dateOfCheck_txt, prof_relative1_txt, prof_relative2_txt;

    public TextView name_txt, email_txt, counter;
    public Button  r1Call_btn , r2Call_btn ;

    public String fullname="", email="", role="";
    public String not_id = "", not_title = "", not_docName = "", not_patName = "", not_checkId = "", not_date = "";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    List<DoctorNotification> notificationDoctor;
    ArrayList<Check> checkList;
    ArrayList<String> relativesData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_notification_home);

        setupViews();
        notificationDoctor = new ArrayList<>();
        checkList = new ArrayList<>();
        relativesData = new ArrayList<>();

        setupNavigation();
        updateNavHeader();

        getNotificationNumber();


        Intent intent = getIntent();
        not_id = intent.getStringExtra("notiId_data");
        not_title = intent.getStringExtra("title_data");
        not_docName = intent.getStringExtra("doctorName_data");
        not_patName = intent.getStringExtra("patientName_data");
        not_checkId = intent.getStringExtra("checkId_data");
        not_date = intent.getStringExtra("date_data");

        Toast.makeText(this, not_checkId  +" "+ not_patName,Toast.LENGTH_SHORT).show();
        getPatientData();
        getCheckData();
    }


    private void setupViews() {

        title_txt = findViewById(R.id.title_txt);
        heartBeat_txt = findViewById(R.id.heartBeat_txt);
        bodyTemp_txt = findViewById(R.id.bodyTemp_txt);
        bloodPressure_txt = findViewById(R.id.bloodPressure_txt);
        dateOfCheck_txt = findViewById(R.id.dateOfCheck_txt);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.nav_menu);
        toolbar = findViewById(R.id.toolbar);

        r1Call_btn=findViewById(R.id.r1Call_btn);
        r2Call_btn=findViewById(R.id.r2Call_btn);

        counter = findViewById(R.id.counter);
    }

    public void notification_btn_Action(View view){
        Intent intent = new Intent(DoctorNotificationHomeActivity.this, DoctorNotificationActivity.class);
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

    public void getPatientData(){

        String URL3 = "http://192.168.1.28/MEM_System/getPatientInfo.php?name="+ not_patName ;

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, URL3, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray ja = response.getJSONArray("result");

                            DoctorNotification noti;

                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jsonObject = ja.getJSONObject(i);
                                String fullname = jsonObject.getString("fullname");
                                String username = jsonObject.getString("username");
                                String email = jsonObject.getString("email");
                                String phonenum = jsonObject.getString("phonenum");
                                String gender = jsonObject.getString("gender");
                                String address = jsonObject.getString("address");
                                String relative1 = jsonObject.getString("relative1");
                                String relative2 = jsonObject.getString("relative2");

                                relativesData.add(relative1);
                                relativesData.add(relative2);
                                relativesData.add(fullname);

                                title_txt.setText("Emergency Case For Patient " + relativesData.get(2));
                                prof_relative1_txt.setText(relativesData.get(0));
                                prof_relative2_txt.setText(relativesData.get(1));
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
    private void getCheckData() {
        String URL3 = "http://192.168.1.28/MEM_System/getCheckInfo.php?id="+ not_checkId ;

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, URL3, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray ja = response.getJSONArray("result");

                            Check check;

                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jsonObject = ja.getJSONObject(i);
                                String checkId = jsonObject.getString("checkId");
                                String heartBeat = jsonObject.getString("heartBeat");
                                String bodyTemp = jsonObject.getString("bodyTemp");
                                String bloodPressure = jsonObject.getString("bloodPressure");
                                String location = jsonObject.getString("location");
                                String dateOfCheck = jsonObject.getString("dateOfCheck");
                                String flag = jsonObject.getString("flag");
                                String patientId = jsonObject.getString("patientId");

                                check = new Check(Integer.valueOf(checkId), heartBeat, bodyTemp, bloodPressure, location, dateOfCheck,"Doctor", flag,fullname,email);
                                checkList.add(check);

                                heartBeat_txt.setText(checkList.get(0).getHeartBeat());
                                bodyTemp_txt.setText(checkList.get(0).getBodyTemp());
                                bloodPressure_txt.setText(checkList.get(0).getBloodPressure());
                                dateOfCheck_txt.setText(checkList.get(0).getDateOfCheck());
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

    public void setupNavigation(){
        //ToolBar
        setSupportActionBar(toolbar);

        //NavigationDrawer Menu
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(DoctorNotificationHomeActivity.this,
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

            Intent intent = new Intent(DoctorNotificationHomeActivity.this, DoctorHomeActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_profile) {
            Intent intent = new Intent(DoctorNotificationHomeActivity.this, DoctorProfileActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_listOfPatients) {
            Intent intent = new Intent(DoctorNotificationHomeActivity.this, ListOfPatientActivity.class);;
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();
        }else if(item.getItemId() == R.id.nav_listOfParamedic){
            Intent intent = new Intent(DoctorNotificationHomeActivity.this, ListOfParamedicActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();
        }else if(item.getItemId() == R.id.nav_setting){
            Intent intent = new Intent(DoctorNotificationHomeActivity.this, EditDoctorInfoActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_logOut){
            Intent intent = new Intent(DoctorNotificationHomeActivity.this, LoginActivity.class);
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

    public void emergencycase_Btn_OnClick(View view) {
    }

    public void endcase_Btn_OnClick(View view) {
    }

    public void call_relative1_BtnAction(View view) {
        makePhoneCall();
    }

    public void call_relative2_BtnAction(View view) {
        makePhoneCall();
    }

    private static final int REQUEST_CALL = 1 ;
    private void makePhoneCall() {


        String number = "";
        if(r1Call_btn.isClickable())
        {
            number = relativesData.get(0);
        }else if(r2Call_btn.isClickable()){
            number = relativesData.get(1);
        }
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(DoctorNotificationHomeActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(DoctorNotificationHomeActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        } else {
            Toast.makeText(DoctorNotificationHomeActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }
}