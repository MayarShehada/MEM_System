package com.birzeit.memsystem.Doctor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.birzeit.memsystem.MySingleton;
import com.birzeit.memsystem.R;
import com.google.android.material.navigation.NavigationView;

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

public class EditDoctorInfoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public String fullname="", username="", password="", email="", phonenum="", gender="", employeeid="", specialty="",confirm_password="";
    public EditText prof_email_txt,prof_password_txt,prof_confirm_password_txt, prof_phone_txt, prof_gender_txt;
    public TextView prof_name_txt, prof_username_txt, prof_specialty_txt, prof_employeeid_txt;
    public TextView name_txt, email_txt;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doctor_info);

        setupViews();
        loadData();
        Intent intent = getIntent();
        fullname = intent.getStringExtra("fullnameData");
        email = intent.getStringExtra("emailData");

        setupNavigation();
        updateNavHeader();
    }

    public void loadData(){

        String DoctorEmail = getIntent().getStringExtra("emailData");
        String url = "http://192.168.1.28:80/MEM_System/DoctorJson.php?cat="+ DoctorEmail;

        // output = (TextView) findViewById(R.id.jsonData);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray ja = response.getJSONArray("result");

                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jsonObject = ja.getJSONObject(i);
                                String fullNames = jsonObject.getString("fullname");

                                String userNames = jsonObject.getString("username");
                                String email = jsonObject.getString("email");
                                String phonenum = jsonObject.getString("phonenum");
                                String gender = jsonObject.getString("gender");
                                String employeeid = jsonObject.getString("employeeid");
                                String specialty = jsonObject.getString("specialty");

                                prof_name_txt.setText(fullNames);
                                prof_username_txt.setText(userNames);
                                prof_username_txt.setEnabled(false);
                                prof_email_txt.setText(email);
                                prof_phone_txt.setText(phonenum);
                                prof_gender_txt.setText(gender);
                                prof_employeeid_txt.setText(employeeid);
                                prof_employeeid_txt.setEnabled(false);
                                prof_specialty_txt.setText(specialty);
                                prof_specialty_txt.setEnabled(false);

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

    public void upDate_btn_OnClick(View view){

        String URL = "http://192.168.1.28:80/MEM_System/UpDateDoctorInfo.php";
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        } else{
            EditDoctorInfoActivity.SendPostRequest runner = new SendPostRequest();
            runner.execute(URL);
        }
    }

    private void setupViews() {

        prof_name_txt = findViewById(R.id.prof_name_txt);
        prof_email_txt = findViewById(R.id.prof_email_txt);
        prof_username_txt = findViewById(R.id.prof_username_txt);
        prof_phone_txt = findViewById(R.id.prof_phone_txt);
        prof_gender_txt = findViewById(R.id.prof_gender_txt);
        prof_specialty_txt = findViewById(R.id.prof_specialty_txt);
        prof_employeeid_txt = findViewById(R.id.prof_employeeid_txt);
        prof_password_txt=findViewById(R.id.prof_password_txt);
        prof_confirm_password_txt=findViewById(R.id.prof_confirm_password_txt);


        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.nav_menu);
        toolbar = findViewById(R.id.toolbar);

    }

    public void setupNavigation(){
        //ToolBar
        setSupportActionBar(toolbar);

        //NavigationDrawer Menu
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(EditDoctorInfoActivity.this,
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.nav_home){

            Intent intent = new Intent(EditDoctorInfoActivity.this, DoctorHomeActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_profile) {
            Intent intent = new Intent(EditDoctorInfoActivity.this, DoctorProfileActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_listOfPatients){
            Intent intent = new Intent(EditDoctorInfoActivity.this, ListOfPatientActivity.class);
            intent.putExtra("fullnameData", fullname);
            intent.putExtra("emailData", email);
            startActivity(intent);
            finish();

        }else if(item.getItemId() == R.id.nav_setting){
            Intent intent = new Intent(EditDoctorInfoActivity.this, EditDoctorInfoActivity.class);
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

        Intent intent = getIntent();
        fullname = prof_name_txt.getText().toString();
        username = prof_username_txt.getText().toString();
        email = prof_email_txt.getText().toString();
        phonenum = prof_phone_txt.getText().toString();
        password = prof_password_txt.getText().toString();
        confirm_password = prof_confirm_password_txt.getText().toString();
        gender = prof_gender_txt.getText().toString();
        specialty=prof_specialty_txt.getText().toString();
        employeeid = prof_employeeid_txt.getText().toString().trim();


        String text = "";

        if (!fullname.equals("") && !email.equals("") && !phonenum.equals("") && !username.equals("") && !password.equals("") && !gender.equals("")  && !employeeid.equals("") && !specialty.equals("")) {

            String data = URLEncoder.encode("fullname", "UTF-8")
                    + "=" + URLEncoder.encode(fullname, "UTF-8");

            data += "&" + URLEncoder.encode("username", "UTF-8")
                    + "=" + URLEncoder.encode(username, "UTF-8");

            data += "&" + URLEncoder.encode("password", "UTF-8")
                    + "=" + URLEncoder.encode(password, "UTF-8");

            data += "&" + URLEncoder.encode("email", "UTF-8")
                    + "=" + URLEncoder.encode(email, "UTF-8");

            data += "&" + URLEncoder.encode("phonenum", "UTF-8")
                    + "=" + URLEncoder.encode(phonenum, "UTF-8");

            data += "&" + URLEncoder.encode("gender", "UTF-8")
                    + "=" + URLEncoder.encode(gender, "UTF-8");

            data += "&" +URLEncoder.encode("employeeid", "UTF-8")
                    + "=" + URLEncoder.encode(employeeid, "UTF-8");

            data += "&" + URLEncoder.encode("specialty", "UTF-8")
                    + "=" + URLEncoder.encode(specialty, "UTF-8");

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
    private class SendPostRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return processRequest(urls[0]);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }

}