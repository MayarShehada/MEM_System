package com.birzeit.memsystem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.birzeit.memsystem.Doctor.DoctorRegister;
import com.birzeit.memsystem.Paramedic.ParamedicRegisterActivity;
import com.birzeit.memsystem.Patient.PatientRegisterActivity;
import com.birzeit.memsystem.Relative.RelativeRegisterActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class RegisterActivity extends AppCompatActivity {

    public EditText fullName_edt;
    public EditText email_edt;
    public EditText userName_edt;
    public EditText password_edt;
    public EditText confirmPass_edt;
    public EditText phone_edt;
    public RadioGroup genderRadioGroup;
    public RadioButton female_rdBtn;
    public RadioButton male_rdBtn;
    public RadioButton radio1_button;
    public Spinner spinner;

    public Button register_btn;


    public String fullname, username, password, repassword, email, phonenum, gender, role;
    public String URL = "http://192.168.1.28:80/MEM_System/Register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupViews();
        populateSpinner();
    }

    private void setupViews() {

        fullname = "";
        username = "";
        password = "";
        repassword = "";
        email = "";
        phonenum = "";
        gender = "";
        role = "";

        fullName_edt = findViewById(R.id.fullName_edt);
        email_edt = findViewById(R.id.email_edt);
        userName_edt = findViewById(R.id.userName_edt);
        password_edt = findViewById(R.id.password_edt);
        confirmPass_edt = findViewById(R.id.confirmPass_edt);
        phone_edt = findViewById(R.id.phone_edt);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        male_rdBtn = findViewById(R.id.male_rdBtn);
        female_rdBtn = findViewById(R.id.female_rdBtn);
        spinner = findViewById(R.id.spinner);

        register_btn = findViewById(R.id.register_btn);

        int radioId1 = genderRadioGroup.getCheckedRadioButtonId();
        radio1_button = findViewById(radioId1);
    }

    private void populateSpinner() {

        ArrayAdapter<String> adapt = new ArrayAdapter<>(this, R.layout.custom_spinner, getResources().getStringArray(R.array.roles));
        adapt.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinner.setAdapter(adapt);
    }

    private String processRequest(String restUrl) throws UnsupportedEncodingException {

        fullname = fullName_edt.getText().toString().trim();
        email = email_edt.getText().toString().trim();
        phonenum = phone_edt.getText().toString().trim();
        username = userName_edt.getText().toString().trim();
        password = password_edt.getText().toString().trim();
        repassword = confirmPass_edt.getText().toString().trim();

        if(male_rdBtn.isChecked()){
            gender = "Male";
        }else if(female_rdBtn.isChecked()){
            gender = "Female";
        }

        role = spinner.getSelectedItem().toString().trim();

        String text = "";

        if (!fullname.equals("") && !email.equals("") && !phonenum.equals("") && !username.equals("") && !password.equals("") && !repassword.equals("") && !gender.equals("") && !role.equals("")) {

            String data = URLEncoder.encode("fullname", "UTF-8")
                    + "=" + URLEncoder.encode(fullname, "UTF-8");

            data += "&" + URLEncoder.encode("username", "UTF-8")
                    + "=" + URLEncoder.encode(username, "UTF-8");

            data += "&" + URLEncoder.encode("password", "UTF-8")
                    + "=" + URLEncoder.encode(password, "UTF-8");

            data += "&" + URLEncoder.encode("repassword", "UTF-8")
                    + "=" + URLEncoder.encode(repassword, "UTF-8");

            data += "&" + URLEncoder.encode("email", "UTF-8")
                    + "=" + URLEncoder.encode(email, "UTF-8");

            data += "&" + URLEncoder.encode("phonenum", "UTF-8")
                    + "=" + URLEncoder.encode(phonenum, "UTF-8");

            data += "&" + URLEncoder.encode("gender", "UTF-8")
                    + "=" + URLEncoder.encode(gender, "UTF-8");

            data += "&" + URLEncoder.encode("role", "UTF-8")
                    + "=" + URLEncoder.encode(role, "UTF-8");

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

            Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_LONG).show();
            if(result.trim().equals("Register Success")) {
                if (role.equals("Doctor")) {
                    Intent intent = new Intent(RegisterActivity.this, DoctorRegister.class);
                    intent.putExtra("nameData", fullname);
                    intent.putExtra("usernameData", username);
                    intent.putExtra("emailData", email);
                    intent.putExtra("phoneData", phonenum);
                    intent.putExtra("passwordData", password);
                    intent.putExtra("genderData", gender);
                    intent.putExtra("roleData", role);
                    startActivity(intent);
                } else if (role.equals("Paramedic")) {
                    Intent intent = new Intent(RegisterActivity.this, ParamedicRegisterActivity.class);
                    intent.putExtra("nameData", fullname);
                    intent.putExtra("usernameData", username);
                    intent.putExtra("emailData", email);
                    intent.putExtra("phoneData", phonenum);
                    intent.putExtra("passwordData", password);
                    intent.putExtra("genderData", gender);
                    intent.putExtra("roleData", role);
                    startActivity(intent);
                } else if (role.equals("Patient")) {
                    Intent intent = new Intent(RegisterActivity.this, PatientRegisterActivity.class);
                    intent.putExtra("nameData", fullname);
                    intent.putExtra("usernameData", username);
                    intent.putExtra("emailData", email);
                    intent.putExtra("phoneData", phonenum);
                    intent.putExtra("passwordData", password);
                    intent.putExtra("genderData", gender);
                    intent.putExtra("roleData", role);
                    startActivity(intent);
                } else if (role.equals("Relative")) {
                    Intent intent = new Intent(RegisterActivity.this, RelativeRegisterActivity.class);
                    intent.putExtra("nameData", fullname);
                    intent.putExtra("usernameData", username);
                    intent.putExtra("emailData", email);
                    intent.putExtra("phoneData", phonenum);
                    intent.putExtra("passwordData", password);
                    intent.putExtra("genderData", gender);
                    intent.putExtra("roleData", role);
                    startActivity(intent);
                }
            }
        }
    }

    public void nextOnClick(View view) {

        InputMethodManager mgr = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(),0);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 123);

        } else {
            SendPostRequest runner = new SendPostRequest();
            runner.execute(URL);
        }
    }

        public void loginButtonAction (View view){

            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

