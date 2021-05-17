package com.birzeit.memsystem.Relative;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.birzeit.memsystem.R;

public class RelativeWelcomeActivity extends AppCompatActivity {

    private static int splash_time_out = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative_welcome);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(RelativeWelcomeActivity.this, RelativeHomeActivity.class);
                startActivity(intent);
                finish();
            }

        }, splash_time_out);
    }
}