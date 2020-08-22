package com.e.mylittlechart.checkdiagnosticinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.e.mylittlechart.R;
import com.e.mylittlechart.example.MyActivity;

public class activity_check_my_diagnostic_information extends AppCompatActivity implements MyActivity {

    Button cancel;
    Button enter_diagnose_inquiry;
    Button enter_diagnose_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_my_diagnostic_information);

        init();
        bindingObject();
        bindingEvent();
        settings();

    }

    @Override
    public void init() {

    }

    @Override
    public void bindingObject() {
        cancel = findViewById(R.id.cancel);
        enter_diagnose_inquiry = findViewById(R.id.enter_diagnose_inquiry);
        enter_diagnose_save = findViewById(R.id.enter_diagnose_save);
    }

    @Override
    public void bindingEvent() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        enter_diagnose_inquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), activity_inquiry_diagnose.class);
            }
        });
        enter_diagnose_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void settings() {

    }

}