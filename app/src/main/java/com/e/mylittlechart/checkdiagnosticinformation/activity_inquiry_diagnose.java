package com.e.mylittlechart.checkdiagnosticinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.e.mylittlechart.R;
import com.e.mylittlechart.example.MyActivity;

public class activity_inquiry_diagnose extends AppCompatActivity implements MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_diagnose);
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

    }

    @Override
    public void bindingEvent() {

    }

    @Override
    public void settings() {

    }
}