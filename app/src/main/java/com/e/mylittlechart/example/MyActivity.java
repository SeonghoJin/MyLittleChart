package com.e.mylittlechart.example;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.e.mylittlechart.R;
import com.e.mylittlechart.adress.activity_adress;
import com.e.mylittlechart.database.LocalPersonalDatabase;
import com.e.mylittlechart.requestcode.MyRequestCode;

public interface MyActivity {

    void init();

    void bindingObject();

    void bindingEvent();

    void settings();

}
