package com.birzeit.memsystem.Doctor;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.birzeit.memsystem.Adapter.PatientListAdapter;
import com.birzeit.memsystem.Models.PatientList;
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
import java.util.List;

public class ListOfPatientActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView check_recycle;
    List<PatientList> checkList;
    String doctorName = "";

    public TextView name_txt, email_txt;

    public String fullname="", email = "";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_patient);

        check_recycle = findViewById(R.id.check_recycle);
        checkList = new ArrayList<>();

        Intent intent = getIntent();
        doctorName = intent.getStringExtra("fullnameData");

        setupNavigation();
        updateNavHeader();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        } else {
            String URL = "http://192.168.1.28:80/MEM_System/PatientsList.php?doctorname="+doctorName;

            ListOfPatientActivity.DownloadTextTask runner = new ListOfPatientActivity.DownloadTextTask();
            runner.execute(URL);
        }
    }


    public void setupNavigation(){
        //ToolBar
        setSupportActionBar(toolbar);

        //NavigationDrawer Menu
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(ListOfPatientActivity.this,
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

            Intent intent = new Intent(ListOfPatientActivity.this, DoctorHomeActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_profile) {
            Intent intent = new Intent(ListOfPatientActivity.this, DoctorProfileActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_listOfPatients){
            Intent intent = new Intent(ListOfPatientActivity.this, ListOfPatientActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_setting){
            Intent intent = new Intent(ListOfPatientActivity.this, EditDoctorInfoActivity.class);
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


            String obj[] = s.split("////");
            PatientList list;
            for(int i = 0 ; i < obj.length ; i++)
            {

                if(! obj[i].equals(null)) {
                    String objects[] = obj[i].split(",");

                    if(!objects.equals(null)) {

                        int patientid = Integer.parseInt(objects[0]);
                        String fullname = objects[1];
                        String email = objects[2];
                        String phonenum = objects[3];
                        String gender = objects[4];
                        String address = objects[5];
                        String relative1 = objects[6];
                        String relative2 = objects[7];

                        list = new PatientList(patientid, fullname, email, phonenum, gender, address, relative1, relative2);
                        checkList.add(list);
                    }
                }
            }

            check_recycle.setLayoutManager(new LinearLayoutManager(ListOfPatientActivity.this));
            PatientListAdapter adapter = new PatientListAdapter(ListOfPatientActivity.this, checkList);
            check_recycle.setAdapter(adapter);
        }
    }
}