package com.e.mylittlechart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.e.mylittlechart.*;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.e.mylittlechart.checkmychart.activity_check_my_chart;
import com.e.mylittlechart.requestcode.MyRequestCode;

public class MainActivity extends AppCompatActivity {
    Button enter_profile;
    Button enter_qrcode_generator;
    Button enter_diagnostic_information;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startLoading();

        bindingObject();
        bindingEvent();
        startAnimation();
    }

    private void startLoading(){
        Intent intent = new Intent(getApplicationContext(), activity_splash.class);
        startActivityForResult(intent, MyRequestCode.ACTIVITY_MAIN_SEND);
    }

    private void bindingObject(){
        enter_profile = findViewById(R.id.enter_profile);
        enter_qrcode_generator = findViewById(R.id.enter_qrcode_generator);
        enter_diagnostic_information = findViewById(R.id.enter_diagnostic_information);
    }

    private void bindingEvent(){
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
                String start_activity_flag = data.getStringExtra("result");
                if(start_activity_flag.equals("true")){
                    Intent intent = new Intent(getApplicationContext(), activity_first_enter_personal_information.class);
                    startActivity(intent);
                }
            }
        }
    }
}