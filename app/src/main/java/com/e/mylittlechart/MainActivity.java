package com.e.mylittlechart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.e.mylittlechart.DiagnoseRecord.DiagnoseRecord;
import com.e.mylittlechart.DiagnoseRecord.DiagnosisClassification;
import com.e.mylittlechart.checkdiagnosticinformation.activity_check_my_diagnostic_information;
import com.e.mylittlechart.checkmychart.activity_check_my_chart;
import com.e.mylittlechart.database.LocalDiagnoseRecordDatabase;
import com.e.mylittlechart.database.LocalPersonalDatabase;
import com.e.mylittlechart.example.MyActivity;
import com.e.mylittlechart.requestcode.MyRequestCode;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements MyActivity {
    Button enter_profile;
    Button enter_qrcode_generator;
    Button enter_diagnostic_information;
    LocalPersonalDatabase localPersonalDatabase;
    LocalDiagnoseRecordDatabase localdiagnoseRecordDatabase;
    Boolean startActivityFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startLoading();

        init();
        bindingObject();
        bindingEvent();
        settings();
    }

    private void startLoading(){
        Intent intent = new Intent(getApplicationContext(), activity_splash.class);
        startActivityForResult(intent, MyRequestCode.ACTIVITY_MAIN_SEND);
    }

    public void init(){
        localPersonalDatabase = new LocalPersonalDatabase(getApplicationContext());
        localdiagnoseRecordDatabase = new LocalDiagnoseRecordDatabase(getApplicationContext());
        startActivityFlag = localPersonalDatabase.empty();
    }

    public void bindingObject(){
        enter_profile = findViewById(R.id.enter_profile);
        enter_qrcode_generator = findViewById(R.id.enter_qrcode_generator);
        enter_diagnostic_information = findViewById(R.id.enter_diagnostic_information);
    }

    public void bindingEvent(){
        enter_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), activity_check_my_chart.class);
                startActivity(intent);
            }
        });
        enter_qrcode_generator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), activity_generate_qrcode.class);
                startActivity(intent);
            }
        });
        enter_diagnostic_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), activity_check_my_diagnostic_information.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void settings() {
        final Testable testLocalDiagnoseRecordDatabase = new Testable() {
            @Override
            public Testable excute() {
                if(localdiagnoseRecordDatabase.count() <= 10) {
                    DiagnoseRecord testDiagnoseRecord = new DiagnoseRecord();
                    testDiagnoseRecord.setDate("2020/08/22");
                    testDiagnoseRecord.setDiagnosisclassification(DiagnosisClassification.BEHAVIORAL_CORRECTION_THERAPY);
                    testDiagnoseRecord.setDisease("우한");
                    localdiagnoseRecordDatabase.insert(testDiagnoseRecord);
                    DiagnoseRecord testDiagnoseRecord2 = localdiagnoseRecordDatabase.getData(localdiagnoseRecordDatabase.count() - 1);
                }
                return this;

            }
        }.excute();
        
    }

    private void startAnimation(){
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.alpha);

        enter_profile.startAnimation(animation);
        enter_qrcode_generator.startAnimation(animation);
        enter_diagnostic_information.startAnimation(animation);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MyRequestCode.ACTIVITY_MAIN_SEND){
            if(resultCode == MyRequestCode.SEND_OK_ACTIVITY_SPLASH){
                if(startActivityFlag){
                    Intent intent = new Intent(getApplicationContext(), activity_first_enter_personal_information.class);
                    startActivity(intent);
                }
                else{
                    startAnimation();
                }
            }
        }
    }
}