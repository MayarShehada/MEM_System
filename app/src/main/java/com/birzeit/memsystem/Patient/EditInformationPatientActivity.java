package com.birzeit.memsystem.Patient;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.birzeit.memsystem.LoginActivity;
import com.birzeit.memsystem.Models.Patient;
import com.birzeit.memsystem.R;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class EditInformationPatientActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public TextView name_edt, email_edt, phone_edt, gender_edt, address_edt, relative1_edt, relative2_edt;
    public TextView name_txt, email_txt;

    public String fullname, email = "", patientId = "", role ="";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information_patient);

        setupViews();

        Intent intent = getIntent();
        fullname = intent.getStringExtra("fullnameData");
        email = intent.getStringExtra("emailData");
        role = intent.getStringExtra("roleData");
        patientId = getIntent().getStringExtra("patientIdData");

        setupNavigation();
        updateNavHeader();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        } else {
            String URL = "http://192.168.1.28:80/MEM_System/PatientProfile.php?email="+email;
            EditInformationPatientActivity.DownloadTextTask runner = new EditInformationPatientActivity.DownloadTextTask();
            runner.execute(URL);
        }
    }

    private void setupViews() {
        name_edt = findViewById(R.id.name_edt);
        email_edt = findViewById(R.id.email_edt);
        phone_edt = findViewById(R.id.phone_edt);
        gender_edt = findViewById(R.id.gender_edt);
        address_edt = findViewById(R.id.address_edt);
        relative1_edt = findViewById(R.id.relative1_edt);
        relative2_edt = findViewById(R.id.relative2_edt);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.nav_menu);
        toolbar = findViewById(R.id.toolbar);

    }

    public void setupNavigation(){
        //ToolBar
        setSupportActionBar(toolbar);

        //NavigationDrawer Menu
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(EditInformationPatientActivity.this,
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
            Intent intent = new Intent(EditInformationPatientActivity.this, PatientHomeActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            intent.putExtra("roleData",role);
            intent.putExtra("patientIdData", patientId);

            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_makeCheck){
            Intent intent = new Intent(EditInformationPatientActivity.this, MakeCheckActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            intent.putExtra("roleData",role);
            intent.putExtra("patientIdData", patientId);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_lastCheck){
            Intent intent = new Intent(EditInformationPatientActivity.this, LastCheckActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            intent.putExtra("roleData",role);
            intent.putExtra("patientIdData", patientId);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_listOfChecks){
            Intent intent = new Intent(EditInformationPatientActivity.this, ListOfChecksActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            intent.putExtra("roleData",role);
            intent.putExtra("patientIdData", patientId);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_listOfEmergency){
            Intent intent = new Intent(EditInformationPatientActivity.this, ListOfEmergencyActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            intent.putExtra("roleData",role);
            intent.putExtra("patientIdData", patientId);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_normalCase){
            Intent intent = new Intent(EditInformationPatientActivity.this, NormalCaseActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            intent.putExtra("roleData",role);
            intent.putExtra("patientIdData", patientId);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_profile){
            Intent intent = new Intent(EditInformationPatientActivity.this, PatientProfileActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            intent.putExtra("roleData",role);
            intent.putExtra("patientIdData", patientId);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_setting){
            Intent intent = new Intent(EditInformationPatientActivity.this, EditPatientInfoActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            intent.putExtra("roleData",role);
            intent.putExtra("patientIdData", patientId);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_logOut){
            Intent intent = new Intent(EditInformationPatientActivity.this, LoginActivity.class);
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

    private InputStream OpenHttpConnection(String urlString) throws IOException {
        InputStream in = null;
        int response = -1;

        java.net.URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");
        try {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        } catch (Exception ex) {
            Log.d("Networking", ex.getLocalizedMessage());
            throw new IOException("Error connecting");
        }
        return in;
    }

    private String DownloadText(String URL) {
        int BUFFER_SIZE = 2000;
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
        } catch (IOException e) {
            Log.d("Networking", e.getLocalizedMessage());
            return "";
        }

        InputStreamReader isr = new InputStreamReader(in);
        int charRead;
        String str = "";
        char[] inputBuffer = new char[BUFFER_SIZE];
        try {
            while ((charRead = isr.read(inputBuffer)) > 0) {
                //---convert the chars to a String---
                String readString =
                        String.copyValueOf(inputBuffer, 0, charRead);
                str += readString;
                inputBuffer = new char[BUFFER_SIZE];
            }
            in.close();
        } catch (IOException e) {
            Log.d("Networking", e.getLocalizedMessage());
            return "";
        }
        return str;
    }

    private class DownloadTextTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return DownloadText(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {


            ArrayList<Patient> list = new ArrayList<>();

            String obj[] = s.split("////");

            for (int i = 0; i < obj.length; i++) {

                if (!obj[i].equals(null)) {
                    String objects[] = obj[i].split(",");

                    if (!objects.equals(null)) {

//                        String fullName = objects[0];
                        String userName = objects[1];
//                        String email = objects[2];
                        String phone = objects[3];
                        String gender = objects[4];
                        String address = objects[5];
                        String relative1 = objects[6];
                        String relative2 = objects[7];
                        String doctorName = objects[8];

                        Patient patient = new Patient(fullname, userName, email, phone, gender, address, relative1, relative2, doctorName);
                        list.add(patient);
                    }
                }
            }

            name_edt.setText(list.get(0).getFullname());
            email_edt.setText(list.get(0).getEmail());
            phone_edt.setText(list.get(0).getPhonenum());
            gender_edt.setText(list.get(0).getGender());
            address_edt.setText(list.get(0).getAddress());
            relative1_edt.setText(list.get(0).getRelative1());;
            relative2_edt.setText(list.get(0).getRelative2());
        }
    }
    
    public void saveChanges_Btn_OnClick(View view) {
    }
}