package com.birzeit.memsystem.Patient;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.birzeit.memsystem.LoginActivity;
import com.birzeit.memsystem.R;

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
import java.util.ArrayList;

public class PatientRegisterActivity extends AppCompatActivity {

    public EditText address_edt;
    public EditText relative1num_edt;
    public EditText relative2num_edt;
    public Spinner spinner;

    private ArrayList<String> doctors = new ArrayList<>();
    RequestQueue requestQueue;
    ArrayAdapter<String> adapt;

    public String fullname, username, password, email, phonenum, gender, role, address, relative1, relative2, doctorname;
    public String URL = "http://192.168.1.28:80/MEM_System/PatientRegister.php";

    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);

        setupViews();
        populateSpinner();
    }

    private void setupViews() {

        address = "";
        relative1 = "";
        relative2 = "";
        doctorname = "";

        address_edt = findViewById(R.id.address_edt);
        relative1num_edt = findViewById(R.id.relative1num_edt);
        relative2num_edt = findViewById(R.id.relative2num_edt);
        requestQueue = Volley.newRequestQueue(this);
        spinner = findViewById(R.id.spinner);
    }


    private void populateSpinner() {

        String url = "http://192.168.1.28:80/MEM_System/DoctorSpinner.php";

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray ja = response.getJSONArray("result");

                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jsonObject = ja.getJSONObject(i);
                                String name = jsonObject.getString("fullname");
                                doctors.add(name);

                                adapt = new ArrayAdapter<>(PatientRegisterActivity.this, R.layout.custom_spinner, doctors);
                                adapt.setDropDownViewResource(R.layout.custom_spinner_dropdown);
                                spinner.setAdapter(adapt);

                                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        doctorname = (String) parent.getItemAtPosition(position);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });


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
        requestQueue.add(jor);
    }

    private String processRequest(String restUrl) throws UnsupportedEncodingException {

        Intent intent = getIntent();
        fullname = intent.getStringExtra("nameData");
        username = intent.getStringExtra("usernameData");
        email = intent.getStringExtra("emailData");
        phonenum = intent.getStringExtra("phoneData");
        password = intent.getStringExtra("passwordData");
        gender = intent.getStringExtra("genderData");
        role = intent.getStringExtra("roleData");

        address = address_edt.getText().toString().trim();
        relative1 = relative1num_edt.getText().toString().trim();
        relative2 = relative2num_edt.getText().toString().trim();

        String text = "";

        if (!fullname.equals("") && !email.equals("") && !phonenum.equals("") && !username.equals("") && !password.equals("") && !gender.equals("") && !role.equals("") && !address.equals("") && !relative1.equals("") && !relative2.equals("") && !doctorname.equals("")) {

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

            data += "&" + URLEncoder.encode("role", "UTF-8")
                    + "=" + URLEncoder.encode(role, "UTF-8");

            data += "&" +URLEncoder.encode("address", "UTF-8")
                    + "=" + URLEncoder.encode(address, "UTF-8");

            data += "&" + URLEncoder.encode("relative1", "UTF-8")
                    + "=" + URLEncoder.encode(relative1, "UTF-8");

            data += "&" + URLEncoder.encode("relative2", "UTF-8")
                    + "=" + URLEncoder.encode(relative2, "UTF-8");

            data += "&" + URLEncoder.encode("doctorname", "UTF-8")
                    + "=" + URLEncoder.encode(doctorname, "UTF-8");

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

            Toast.makeText(PatientRegisterActivity.this, result, Toast.LENGTH_LONG).show();
            if(result.trim().equals("Register Success")) {
                Intent intent = new Intent(PatientRegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    public void registerOnClick(View view) {

        InputMethodManager mgr = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(),0);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 123);

        } else {
            PatientRegisterActivity.SendPostRequest runner = new PatientRegisterActivity.SendPostRequest();
            runner.execute(URL);
        }
    }
}