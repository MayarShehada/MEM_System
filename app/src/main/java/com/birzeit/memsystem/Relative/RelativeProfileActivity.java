package com.birzeit.memsystem.Relative;

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

import com.birzeit.memsystem.Doctor.CheckInfoActivity;
import com.birzeit.memsystem.LoginActivity;
import com.birzeit.memsystem.Patient.ListOfChecksActivity;
import com.birzeit.memsystem.Models.Relative;
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

public class RelativeProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public TextView prof_name_txt, prof_email_txt, prof_username_txt, prof_phone_txt, prof_gender_txt, prof_patientname_txt;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private TextView name_txt, email_txt;
    public String fullname, email, role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative_profile);

        setupViews();
        setupNavigation();
        updateNavHeader();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        } else {
            String URL = "http://192.168.1.28:80/MEM_System/RelativeProfile.php?email="+email;
            RelativeProfileActivity.DownloadTextTask runner = new RelativeProfileActivity.DownloadTextTask();
            runner.execute(URL);
        }
    }

    private void setupViews() {

        prof_name_txt = findViewById(R.id.prof_name_txt);
        prof_email_txt = findViewById(R.id.prof_email_txt);
        prof_username_txt = findViewById(R.id.prof_username_txt);
        prof_phone_txt = findViewById(R.id.prof_phone_txt);
        prof_gender_txt = findViewById(R.id.prof_gender_txt);
        prof_patientname_txt = findViewById(R.id.prof_patientname_txt);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.nav_menu);
        toolbar = findViewById(R.id.toolbar);
    }

    public void setupNavigation(){
        //ToolBar
        setSupportActionBar(toolbar);

        //NavigationDrawer Menu
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(RelativeProfileActivity.this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_profile);
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

            Intent intent = new Intent(RelativeProfileActivity.this, RelativeHomeActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_listOfChecks){
            Intent intent = new Intent(RelativeProfileActivity.this, ListOfChecksActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_profile){
            Intent intent = new Intent(RelativeProfileActivity.this, RelativeProfileActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_setting){
            Intent intent = new Intent(RelativeProfileActivity.this, EditRelativeInfoActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_logOut){
            Intent intent = new Intent(RelativeProfileActivity.this, LoginActivity.class);
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


            ArrayList<Relative> list = new ArrayList<>();

            String obj[] = s.split("////");

            for (int i = 0; i < obj.length; i++) {

                if (!obj[i].equals(null)) {
                    String objects[] = obj[i].split(",");

                    if (!objects.equals(null)) {

                        String fullName = objects[0];
                        String userName = objects[1];
                        String email = objects[2];
                        String phone = objects[3];
                        String gender = objects[4];
                        String patientname = objects[5];

                        Relative relative = new Relative(fullName, userName, email, phone, gender, patientname);
                        list.add(relative);
                    }
                }
            }

            prof_name_txt.setText(list.get(0).getFullname());
            prof_email_txt.setText(list.get(0).getEmail());
            prof_username_txt.setText(list.get(0).getUsername());
            prof_phone_txt.setText(list.get(0).getPhonenum());
            prof_gender_txt.setText(list.get(0).getGender());
            prof_patientname_txt.setText(list.get(0).getPatientname());

        }
    }
}