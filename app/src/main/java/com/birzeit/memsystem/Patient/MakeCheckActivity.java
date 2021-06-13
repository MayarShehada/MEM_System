package com.birzeit.memsystem.Patient;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MakeCheckActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private TextView name_txt, email_txt, textView10;
    public String fullname, email, role="", patientId="";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    String heartBeat = "", bodyTemp = "", bloodPress = "", DateTime = "", location ="";
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_check);

        Intent intent = getIntent();
        fullname = intent.getStringExtra("fullnameData");
        email = intent.getStringExtra("emailData");

        patientId = getIntent().getStringExtra("patientIdData");

        setupViews();

        setupNavigation();
        updateNavHeader();

        getCurrentLocation();
    }

    private void getCurrentLocation() {


    }

    void calculate() {

        int heartbeat = 0;
        double bodyTemp = 0;
        int bloodPress1 = 0;
        int bloodPress2 = 0;

        Date time = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = df.format(time);

        Random random = new Random();

        heartbeat = 40 + random.nextInt(110 - 40);
        bodyTemp = 34.5 + (40 - 34.5) * random.nextDouble();
        bloodPress1 = 70 + random.nextInt(140 - 70);
        bloodPress2 = 40 + random.nextInt(100 - 40);

        String bloodPress = bloodPress1 + "/" + bloodPress2;

        double bodyTempr = Double.parseDouble(String.format("%.1f" ,bodyTemp));
        int flag = 0;
        flag = calculateNormal(heartbeat, bodyTemp, bloodPress1, bloodPress2);

    }

    public int calculateNormal(int heartbeat, double bodyTemp, int bloodPress1, int bloodPress2){

        if(heartbeat >= 60  && heartbeat <= 100){
            if(bodyTemp >= 36.1 && bodyTemp < 37.2){
                if(bloodPress1 >= 90 && bloodPress1 <= 120 && bloodPress2 >= 60 && bloodPress2 <= 80){
                    return 0;
                }
            }
        }
        return 1;
    }

    public void setupViews() {

        textView10 = findViewById(R.id.textView10);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.nav_menu);
        toolbar = findViewById(R.id.toolbar);
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
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_lastCheck){
            Intent intent = new Intent(MakeCheckActivity.this, LastCheckActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        } else if(item.getItemId() == R.id.nav_listOfChecks){
            Intent intent = new Intent(MakeCheckActivity.this, ListOfChecksActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_normalCase){
            Intent intent = new Intent(MakeCheckActivity.this, NormalCaseActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_profile){
            Intent intent = new Intent(MakeCheckActivity.this, PatientProfileActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_setting){
            Intent intent = new Intent(MakeCheckActivity.this, EditPatientInfoActivity.class);
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

    private String processRequest(String restUrl) throws UnsupportedEncodingException {

        int heart = 0;
        double temp = 0;
        int bloodPress1 = 0;
        int bloodPress2 = 0;

        Date time = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = df.format(time);

        Random random = new Random();

        heart = 40 + random.nextInt(110 - 40);
        temp = 34.5 + (40 - 34.5) * random.nextDouble();
        bloodPress1 = 70 + random.nextInt(140 - 70);
        bloodPress2 = 40 + random.nextInt(100 - 40);

        double temp2 = Double.parseDouble(String.format("%.1f" ,bodyTemp));

        flag = calculateNormal(heart, temp, bloodPress1, bloodPress2);

        heartBeat = String.valueOf(heart);
        bodyTemp = String.valueOf(temp2);
        bloodPress = bloodPress1 + "/" + bloodPress2;
        DateTime = date + " " + time;
        location = "--";

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
            textView10.setText(result.length()+"j");
        }
    }



    public void make_check_BtnAction(View view) {

        String URL = "http://192.168.1.124:80/MEM_System/insertRandomData.php";

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