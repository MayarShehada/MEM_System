package com.birzeit.memsystem;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {

    private EditText userName_edt;
    private EditText password_edt;
    private Button login_btn;
    private CheckBox remember_check;
    private TextView signup_txt;
    RequestQueue requestQueue;
    public String username, password, user;
    public String URL = "http://192.168.1.28:80/MEM_System/Login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupViews();
        checkRememberMe();
    }

    private void setupViews() {
        username = "";
        password = "";
        user = "";
        userName_edt = findViewById(R.id.userName_edt);
        password_edt = findViewById(R.id.password_edt);
        login_btn = findViewById(R.id.login_btn);
        remember_check = findViewById(R.id.remember_check);
        signup_txt = findViewById(R.id.signup_txt);
        requestQueue = Volley.newRequestQueue(this);
    }

    private void checkRememberMe() {

        SharedPreferences prefs = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = prefs.getString("remember", "");

        if(checkbox.equals("true")){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }else if(checkbox.equals("false")){
            Toast.makeText(this, "Please Sign In.", Toast.LENGTH_LONG).show();
        }

        remember_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if(compoundButton.isChecked()){
                    SharedPreferences prefs = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "Checked", Toast.LENGTH_LONG).show();
                }else if (!compoundButton.isChecked()){
                    SharedPreferences prefs = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "UnChecked", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private String processRequest(String restUrl) throws UnsupportedEncodingException {
        
        username = userName_edt.getText().toString().trim();
        password = password_edt.getText().toString().trim();
        

        String text = "";

        if (!username.equals("") && !password.equals("")) {

            String  data =  URLEncoder.encode("username", "UTF-8")
                    + "=" + URLEncoder.encode(username, "UTF-8");

            data += "&" + URLEncoder.encode("password", "UTF-8")
                    + "=" + URLEncoder.encode(password, "UTF-8");

            //checkUser();

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

            Toast.makeText(LoginActivity.this, result, Toast.LENGTH_LONG).show();
            if(result.trim().equals("Login Success") ){
                Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                intent.putExtra("usernameData", username);
                startActivity(intent);
                finish();
            }else if (result.trim().equals("Login Failed")){
                Toast.makeText(LoginActivity.this, "Invalid Login Id/Password", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void loginOnClick(View view) {

        InputMethodManager mgr = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(),0);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 123);

        } else {
            LoginActivity.SendPostRequest runner = new LoginActivity.SendPostRequest();
            runner.execute(URL);
        }
    }

    public void registerButtonAction(View view) {

        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}