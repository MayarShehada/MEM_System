package com.birzeit.memsystem.Doctor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.birzeit.memsystem.Adapter.ParamedicListAdapter;
import com.birzeit.memsystem.LoginActivity;
import com.birzeit.memsystem.Models.DoctorNotification;
import com.birzeit.memsystem.Models.ParamedicList;
import com.birzeit.memsystem.MySingleton;
import com.birzeit.memsystem.R;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class ListOfParamedicActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView check_recycle;
    List<ParamedicList> paramedicList;

    public TextView name_txt, email_txt, counter;

    public String fullname1 = "", email1 = "";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    public EditText searchView;

    ParamedicListAdapter adapter;

    List<DoctorNotification> notificationDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_paramedic);

        setupViews();

        Intent intent = getIntent();
        fullname1 = intent.getStringExtra("fullnameData");
        email1 = intent.getStringExtra("emailData");

        setupNavigation();
        updateNavHeader();

        paramedicList = new ArrayList<>();

        notificationDoctor = new ArrayList<>();
        getNotificationNumber();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        } else {
            String URL = "http://192.168.1.28:80/MEM_System/ParamedicList.php";
            ListOfParamedicActivity.DownloadTextTask runner = new ListOfParamedicActivity.DownloadTextTask();
            runner.execute(URL);
        }


        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String text) {

        ArrayList<ParamedicList> filterList = new ArrayList<>();
        for (ParamedicList item : paramedicList)
        {
            if (item.getFullname().toLowerCase().contains(text.toLowerCase()))
            {
                filterList.add(item);
            }
        }
        adapter.filteredList(filterList);
    }

    public void setupViews() {

        check_recycle = findViewById(R.id.check_recycle);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.nav_menu);
        toolbar = findViewById(R.id.toolbar);
        searchView = findViewById(R.id.searchView);

        counter = findViewById(R.id.counter);
    }

    public void notification_btn_Action(View view){
        Intent intent = new Intent(ListOfParamedicActivity.this, DoctorNotificationActivity.class);
        intent.putExtra("fullnameData", fullname1);
        intent.putExtra("emailData", email1);
        startActivity(intent);
        finish();
    }

    public void getNotificationNumber(){

        String URL2 = "http://192.168.1.28/MEM_System/getDoctorNotificationJson.php?doctorName="+ fullname1 ;

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

                                if(fullname1.equals(doctorName)){
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
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(ListOfParamedicActivity.this,
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
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        if(item.getItemId() == R.id.nav_home){

            Intent intent = new Intent(ListOfParamedicActivity.this, DoctorHomeActivity.class);
            intent.putExtra("fullnameData", fullname1);
            intent.putExtra("emailData", email1);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_profile) {
            Intent intent = new Intent(ListOfParamedicActivity.this, DoctorProfileActivity.class);
            intent.putExtra("fullnameData", fullname1);
            intent.putExtra("emailData", email1);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_listOfPatients){
            Intent intent = new Intent(ListOfParamedicActivity.this, ListOfPatientActivity.class);
            intent.putExtra("fullnameData", fullname1);
            intent.putExtra("emailData", email1);
            startActivity(intent);
            finish();
        }else if(item.getItemId() == R.id.nav_listOfParamedic){
            Intent intent = new Intent(ListOfParamedicActivity.this, ListOfParamedicActivity.class);
            intent.putExtra("fullnameData", fullname1);
            intent.putExtra("emailData", email1);
            startActivity(intent);
            finish();
        }else if(item.getItemId() == R.id.nav_setting){
            Intent intent = new Intent(ListOfParamedicActivity.this, EditDoctorInfoActivity.class);
            intent.putExtra("fullnameData", fullname1);
            intent.putExtra("emailData", email1);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_logOut){
            Intent intent = new Intent(ListOfParamedicActivity.this, LoginActivity.class);
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

        name_txt.setText(fullname1);
        email_txt.setText(email1);
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
            ParamedicList list;
            for(int i = 0 ; i < obj.length ; i++)
            {

                if(! obj[i].equals(null)) {
                    String objects[] = obj[i].split(",");

                    if(!objects.equals(null)) {

                        String paramedicId = objects[0];
                        String fullname = objects[1];
                        String email = objects[2];
                        String phonenum = objects[3];
                        String gender = objects[4];
                        String ambulanceId = objects[5];

                        list = new ParamedicList(paramedicId, fullname, email, phonenum, gender, ambulanceId, fullname1, email1);
                        paramedicList.add(list);
                    }
                }
            }

            Intent intent = new Intent(ListOfParamedicActivity.this, ParamedicInfoActivity.class);
            intent.putExtra("fullnameData", fullname1);
            intent.putExtra("emailData", email1);

            check_recycle.setLayoutManager(new LinearLayoutManager(ListOfParamedicActivity.this));
            adapter = new ParamedicListAdapter(ListOfParamedicActivity.this, paramedicList);
            check_recycle.setAdapter(adapter);
        }
    }
}