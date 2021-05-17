package com.birzeit.memsystem.Patient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.birzeit.memsystem.Doctor.DoctorHomeActivity;
import com.birzeit.memsystem.ListOfChecksActivity;
import com.birzeit.memsystem.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PatientHomeActivity extends AppCompatActivity {

    public TextView clock_txt;
    public TextView date_txt;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

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

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        date = dateFormat.format(calendar.getTime());
        date_txt.setText(date);
    }

    private void setupViews() {

        clock_txt = findViewById(R.id.clock_txt);
        date_txt = findViewById(R.id.date_txt);
    }


    public void info_btn_OnClick(View view) {
        Intent intent = new Intent(PatientHomeActivity.this, NormalCaseActivity.class);
        startActivity(intent);
        finish();
    }

    public void listofchecks_btn_OnClik(View view) {
        Intent intent = new Intent(PatientHomeActivity.this, ListOfChecksActivity.class);
        startActivity(intent);
        finish();
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