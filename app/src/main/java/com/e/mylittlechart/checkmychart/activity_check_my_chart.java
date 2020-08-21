package com.e.mylittlechart.checkmychart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.e.mylittlechart.R;

public class activity_check_my_chart extends AppCompatActivity {
    Button enter;
    Button cancel;
    Button enter_change_name;
    Button enter_change_phone_number;
    Button enter_change_adress;
    Button enter_change_registration_number;
    TextView name;
    TextView registation_number;
    TextView phone_number;
    TextView adress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_my_chart);

        bindingObject();
        bindingEvent();
    }

    private void bindingObject(){
        name = findViewById(R.id.registration_number);
        registation_number = findViewById(R.id.registration_number);
        phone_number = findViewById(R.id.registration_number);
        adress = findViewById(R.id.adress);
        cancel = findViewById(R.id.cancel);
        enter = findViewById(R.id.save);
        enter_change_adress = findViewById(R.id.enter_change_adress);
        enter_change_name = findViewById(R.id.enter_change_name);
        enter_change_phone_number = findViewById(R.id.enter_change_phone_number);
        enter_change_registration_number = findViewById(R.id.enter_change_register_number);
    }

    private void bindingEvent(){
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        enter_change_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), activity_change_name.class);
                startActivity(intent);
            }
        });
        enter_change_phone_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), activity_change_phone_number.class);
                startActivity(intent);
            }
        });
        enter_change_registration_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), activity_change_registration_number.class);
                startActivity(intent);
            }
        });
        enter_change_adress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), activity_change_adress.class);
                startActivity(intent);
            }
        });

    }
}