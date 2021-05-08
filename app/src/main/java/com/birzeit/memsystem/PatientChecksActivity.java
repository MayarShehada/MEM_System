package com.birzeit.memsystem;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PatientChecksActivity extends AppCompatActivity {

    public TextView clock_txt;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_checks);

        setupViews();

        Thread myThread = null;

        Runnable runnable = new CountDownRunner();
        myThread= new Thread(runnable);
        myThread.start();
    }


    private void clockSetup() {

        runOnUiThread(new Runnable() {
            public void run() {
                try{

                    Date dt = new Date();
                    int hours = dt.getHours();
                    int minutes = dt.getMinutes();
                    int seconds = dt.getSeconds();
                    String curTime = hours + ":" + minutes + ":" + seconds;
                    clock_txt.setText(curTime);
                }catch (Exception e) {}
            }
        });

//        calendar = Calendar.getInstance();
//        dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
//        date = dateFormat.format(calendar.getTime());
//        clock_txt.setText(date);
    }

    private void setupViews() {
        clock_txt = findViewById(R.id.clock_txt);
    }


    public void info_btn_OnClick(View view) {
    }

    public void emarg_btn_OnClik(View view) {
    }


    class CountDownRunner implements Runnable{
        // @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                try {
                    clockSetup();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }catch(Exception e){
                }
            }
        }
    }

}