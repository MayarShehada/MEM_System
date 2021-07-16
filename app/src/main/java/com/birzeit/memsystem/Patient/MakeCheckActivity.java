package com.birzeit.memsystem.Patient;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
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
import com.birzeit.memsystem.MySingleton;
import com.birzeit.memsystem.R;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MakeCheckActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView name_txt, email_txt, text_view_progress, textView11;
    public String fullname, email, role = "", patientId = "";
    public ProgressBar progress_bar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    String heartBeat = "", bodyTemp = "", bloodPress = "", DateTime = "", location = "", doctorName = "";
    int flag = 0, checkId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_check);

        Intent intent = getIntent();
        fullname = intent.getStringExtra("fullnameData");
        email = intent.getStringExtra("emailData");
        role = intent.getStringExtra("roleData");
        patientId = getIntent().getStringExtra("patientIdData");

        setupViews();

        lastCheck = new ArrayList<>();

        getDoctorName();

        get_Check_Id();

        setupNavigation();
        updateNavHeader();
    }

    void calculate() {

        int heart = 0;
        double temp = 0;
        int bloodPress1 = 0;
        int bloodPress2 = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

        Random random = new Random();

        heart = 59 + random.nextInt(100 - 59);
        temp = 36.0 + (37.5 - 36.0) * random.nextDouble();
        bloodPress1 = 90 + random.nextInt(120 - 90);
        bloodPress2 = 59 + random.nextInt(80 - 59);

        double temp2 = Double.parseDouble(String.format("%.1f", temp));

        flag = calculateNormal(heart, temp, bloodPress1, bloodPress2);

        heartBeat = String.valueOf(heart);
        bodyTemp = String.valueOf(temp2);
        bloodPress = bloodPress1 + "/" + bloodPress2;
        DateTime = currentDateandTime;
        location = "--";

    }

    public int calculateNormal(int heartbeat, double bodyTemp, int bloodPress1, int bloodPress2) {

        int x = 0;
        if (heartbeat >= 60 && heartbeat <= 100) {
            if (bodyTemp >= 36.1 && bodyTemp <= 37.2) {
                if (bloodPress1 >= 90 && bloodPress1 <= 120 && bloodPress2 >= 60 && bloodPress2 <= 80) {
                    x = 0;
                }else{
                    x = 1;
                }
            }else {
                x = 1;
            }
        }else {
            x = 1;
        }
        return x;
    }

    List<Check> lastCheck;

    public void get_Check_Id(){

        String URL2 = "http://192.168.1.28:80/MEM_System/lastCheck.php?cat="+ patientId ;

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, URL2, null,
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
                                }
                            }

                            checkId = lastCheck.get(0).getCheckId() + 1;

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

    public void getDoctorName(){

        String URL3 = "http://192.168.1.28:80/MEM_System/doctorName.php?cat="+ patientId ;

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, URL3, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray ja = response.getJSONArray("result");

                            Check check;

                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jsonObject = ja.getJSONObject(i);
                                doctorName = jsonObject.getString("doctorname");
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

    public void setupViews() {

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.nav_menu);
        toolbar = findViewById(R.id.toolbar);
        progress_bar = findViewById(R.id.progress_bar);
        text_view_progress = findViewById(R.id.text_view_progress);
        textView11 = findViewById(R.id.textView11);
    }

    Handler handler = new Handler();
    private int progressStatus = 0;

    private void prepareProgressBar() {

        while(progressStatus != 100) {
            new Thread(new Runnable() {
                public void run() {
                    while (progressStatus < 100) {
                        progressStatus += 1;
                        // Update the progress bar and display the
                        //current value in the text view
                        handler.post(new Runnable() {
                            public void run() {
                                progress_bar.setProgress(progressStatus);
                                text_view_progress.setText(progressStatus + " %");
                            }
                        });
                        try {
                            // Sleep for 100 milliseconds.
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    textView11.setText("Examination has been completed");
                }
            }).start();
        }
    }

    public void setupNavigation(){
        //ToolBar
        setSupportActionBar(toolbar);

        //NavigationDrawer Menu
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(MakeCheckActivity.this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_makeCheck);
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
            Intent intent = new Intent(MakeCheckActivity.this, PatientHomeActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            intent.putExtra("roleData",role);
            intent.putExtra("patientIdData",patientId);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_lastCheck){
            Intent intent = new Intent(MakeCheckActivity.this, LastCheckActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            intent.putExtra("roleData",role);
            intent.putExtra("patientIdData",patientId);
            startActivity(intent);
            finish();

        } else if(item.getItemId() == R.id.nav_listOfChecks){
            Intent intent = new Intent(MakeCheckActivity.this, ListOfChecksActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            intent.putExtra("roleData",role);
            intent.putExtra("patientIdData",patientId);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_normalCase){
            Intent intent = new Intent(MakeCheckActivity.this, NormalCaseActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            intent.putExtra("roleData",role);
            intent.putExtra("patientIdData",patientId);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_profile){
            Intent intent = new Intent(MakeCheckActivity.this, PatientProfileActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            intent.putExtra("roleData",role);
            intent.putExtra("patientIdData",patientId);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_setting){
            Intent intent = new Intent(MakeCheckActivity.this, EditPatientInfoActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            intent.putExtra("roleData",role);
            intent.putExtra("patientIdData",patientId);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_logOut){
            Intent intent = new Intent(MakeCheckActivity.this, LoginActivity.class);
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

    private String processRequest(String restUrl) throws UnsupportedEncodingException {

        prepareProgressBar();

        calculate();

        String text = "";

        String checkIds = String.valueOf(checkId);

        if (!heartBeat.equals("") && !bodyTemp.equals("") && !bloodPress.equals("") && !location.equals("") && !DateTime.equals("") && flag != 0 && !patientId.equals("") && !checkIds.equals("") && !doctorName.equals("") && !fullname.equals("")) {

            String data = URLEncoder.encode("heartBeat", "UTF-8")
                    + "=" + URLEncoder.encode(heartBeat, "UTF-8");

            data += "&" + URLEncoder.encode("bodyTemp", "UTF-8")
                    + "=" + URLEncoder.encode(bodyTemp, "UTF-8");

            data += "&" + URLEncoder.encode("bloodPressure", "UTF-8")
                    + "=" + URLEncoder.encode(bloodPress, "UTF-8");

            data += "&" + URLEncoder.encode("location", "UTF-8")
                    + "=" + URLEncoder.encode(location, "UTF-8");

            data += "&" + URLEncoder.encode("dateOfCheck", "UTF-8")
                    + "=" + URLEncoder.encode(DateTime, "UTF-8");

            data += "&" + URLEncoder.encode("flag", "UTF-8")
                    + "=" + URLEncoder.encode(String.valueOf(flag), "UTF-8");

            data += "&" + URLEncoder.encode("patientId", "UTF-8")
                    + "=" + URLEncoder.encode(patientId, "UTF-8");

            data += "&" + URLEncoder.encode("checkId", "UTF-8")
                    + "=" + URLEncoder.encode(checkIds, "UTF-8");

            data += "&" + URLEncoder.encode("doctorName", "UTF-8")
                    + "=" + URLEncoder.encode(doctorName, "UTF-8");

            data += "&" + URLEncoder.encode("patientName", "UTF-8")
                    + "=" + URLEncoder.encode(fullname, "UTF-8");

            BufferedReader reader = null;

            // Send data
            try {
                // Defined URL  where to send data
                java.net.URL url = new URL(restUrl);

                // Send POST data request

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                // Get the server response

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "\n");
                }

                text = sb.toString();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {

                    reader.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        // Show response on activity
        return text;
    }

    public class SendPostRequest extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                return processRequest(strings[0]);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            get_Check_Id();
            Toast.makeText(MakeCheckActivity.this, result , Toast.LENGTH_LONG).show();
        }
    }

    public void make_check_BtnAction(View view) {

        String URL = "http://192.168.1.28:80/MEM_System/insertRandomData.php";

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);
        } else{
            MakeCheckActivity.SendPostRequest runner = new SendPostRequest();
            runner.execute(URL);
        }
    }
}