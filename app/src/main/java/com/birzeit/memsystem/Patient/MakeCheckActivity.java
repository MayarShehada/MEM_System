package com.birzeit.memsystem.Patient;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import com.birzeit.memsystem.R;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MakeCheckActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView name_txt, email_txt, text_view_progress, textView11;
    public String fullname, email, role = "", patientId = "";
    public ProgressBar progress_bar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    String heartBeat = "", bodyTemp = "", bloodPress = "", DateTime = "", location = "";
    int flag = 0;

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

        setupNavigation();
        updateNavHeader();

//        getCurrentLocation();
    }

    private int REQUEST_CODE = 111;

    private void getCurrentLocation() {

        if (ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    MakeCheckActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE
            );
        } else {
            getLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(MakeCheckActivity.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(MakeCheckActivity.this)
                                .removeLocationUpdates(this);
                        if(locationRequest != null && locationResult.getLocations().size()>0){
                            int latestLocationIndex = locationResult.getLocations().size();
                            double latitude = 0.0 , longitude = 0.0;
                            latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                        textView11.setText("Lati = " + latitude + " Long " + longitude);
                        }
                    }
                }, Looper.getMainLooper());
    }

    void calculate() {

        int heart = 0;
        double temp = 0;
        int bloodPress1 = 0;
        int bloodPress2 = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

        Random random = new Random();

        heart = 55 + random.nextInt(105 - 55);
        temp = 36.0 + (37.5 - 36.0) * random.nextDouble();
        bloodPress1 = 90 + random.nextInt(120 - 90);
        bloodPress2 = 55 + random.nextInt(80 - 55);

        double temp2 = Double.parseDouble(String.format("%.1f", temp));

        flag = calculateNormal(heart, temp, bloodPress1, bloodPress2);

        heartBeat = String.valueOf(heart);
        bodyTemp = String.valueOf(temp2);
        bloodPress = bloodPress1 + "/" + bloodPress2;
        DateTime = currentDateandTime;
        location = "--";

    }

    public int calculateNormal(int heartbeat, double bodyTemp, int bloodPress1, int bloodPress2) {

        if (heartbeat >= 60 && heartbeat <= 100) {
            if (bodyTemp >= 36.1 && bodyTemp < 37.2) {
                if (bloodPress1 >= 90 && bloodPress1 <= 120 && bloodPress2 >= 60 && bloodPress2 <= 80) {
                    return 0;
                }
            }
        }
        return 1;
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
                            Thread.sleep(100);
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

        if (!heartBeat.equals("") && !bodyTemp.equals("") && !bloodPress.equals("") && !location.equals("") && !DateTime.equals("") && flag != 0 && !patientId.equals("") ) {

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
            Toast.makeText(MakeCheckActivity.this, result, Toast.LENGTH_LONG).show();
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