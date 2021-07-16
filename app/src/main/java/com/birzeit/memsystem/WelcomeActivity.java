package com.birzeit.memsystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.birzeit.memsystem.Doctor.DoctorHomeActivity;
import com.birzeit.memsystem.Paramedic.ParamedicHomeActivity;
import com.birzeit.memsystem.Patient.PatientHomeActivity;
import com.birzeit.memsystem.Relative.RelativeHomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WelcomeActivity extends AppCompatActivity {

    public String username, role = "", email = "", fullname = "", id="";
    RequestQueue requestQueue;

    private static int splash_time_out = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Intent intent = getIntent();
        username = intent.getStringExtra("usernameData");

        requestQueue = Volley.newRequestQueue(this);

        checkUser();
    }

    private void  checkUser() {

        String url = "http://192.168.1.28:80/MEM_System/CheckUser.php?username="+username;

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray ja = response.getJSONArray("result");


                            JSONObject jsonObject = ja.getJSONObject(0);
                            role = jsonObject.getString("role");
                            email = jsonObject.getString("email");
                            fullname = jsonObject.getString("fullname");
                            id = jsonObject.getString("id");


                            new Handler().postDelayed(new Runnable(){
                                @Override
                                public void run() {

                                    if (role.equals("Patient")) {
                                        Intent intent = new Intent(WelcomeActivity.this, PatientHomeActivity.class);
                                        intent.putExtra("fullnameData", fullname);
                                        intent.putExtra("roleData", role);
                                        intent.putExtra("emailData", email);
                                        intent.putExtra("idData", id);
                                        startActivity(intent);
                                        finish();
                                    } else if (role.equals("Paramedic")) {
                                        Intent intent = new Intent(WelcomeActivity.this, ParamedicHomeActivity.class);
                                        intent.putExtra("fullnameData", fullname);
                                        intent.putExtra("roleData", role);
                                        intent.putExtra("emailData", email);
                                        intent.putExtra("idData", id);
                                        startActivity(intent);
                                        finish();
                                    } else if (role.equals("Relative")) {
                                        Intent intent = new Intent(WelcomeActivity.this, RelativeHomeActivity.class);
                                        intent.putExtra("fullnameData", fullname);
                                        intent.putExtra("roleData", role);
                                        intent.putExtra("emailData", email);
                                        intent.putExtra("idData", id);
                                        startActivity(intent);
                                        finish();
                                    } else if (role.equals("Doctor")) {
                                        Intent intent = new Intent(WelcomeActivity.this, DoctorHomeActivity.class);
                                        intent.putExtra("fullnameData", fullname);
                                        intent.putExtra("roleData", role);
                                        intent.putExtra("emailData", email);
                                        intent.putExtra("idData", id);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                                }, splash_time_out);
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
}