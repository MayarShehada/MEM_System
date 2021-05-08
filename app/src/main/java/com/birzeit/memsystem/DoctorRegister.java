package com.birzeit.memsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class DoctorRegister extends AppCompatActivity {

    public EditText employeeId_edt;
    public Spinner spinner;

    public String fullname, username, password, email, phonenum, gender, role, employeeid, specialty;
    public String URL = "http://192.168.1.124:80/MEM_System/DoctorRegister.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);

        setupViews();
        populateSpinner();
    }

    private void setupViews() {

        employeeid = "";
        specialty = "";

        employeeId_edt = findViewById(R.id.employeeId_edt);
        spinner = findViewById(R.id.spinner);
    }

    private void populateSpinner() {

        ArrayAdapter<String> adapt = new ArrayAdapter<>(this, R.layout.custom_spinner, getResources().getStringArray(R.array.specialties));
        adapt.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinner.setAdapter(adapt);
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

        employeeid = employeeId_edt.getText().toString().trim();
        specialty = spinner.getSelectedItem().toString().trim();

        String text = "";

        if (!fullname.equals("") && !email.equals("") && !phonenum.equals("") && !username.equals("") && !password.equals("") && !gender.equals("") && !role.equals("") && !employeeid.equals("") && !specialty.equals("")) {

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

            Toast.makeText(DoctorRegister.this, result, Toast.LENGTH_LONG).show();
            if(result.trim().equals("Register Success")) {
                Intent intent = new Intent(DoctorRegister.this, LoginActivity.class);
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
            DoctorRegister.SendPostRequest runner = new DoctorRegister.SendPostRequest();
            runner.execute(URL);
        }
    }
}