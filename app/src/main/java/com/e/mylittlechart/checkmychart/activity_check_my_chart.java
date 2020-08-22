package com.e.mylittlechart.checkmychart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.e.mylittlechart.R;
import com.e.mylittlechart.database.LocalPersonalDatabase;
import com.e.mylittlechart.example.MyActivity;

public class activity_check_my_chart extends AppCompatActivity implements MyActivity {
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
    LocalPersonalDatabase localPersonalDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_my_chart);

        init();
        bindingObject();
        bindingEvent();
        settings();
    }

    @Override
    public void init() {
        localPersonalDatabase = new LocalPersonalDatabase(getApplicationContext());
    }

    public void bindingObject(){
        name = findViewById(R.id.name);
        registation_number = findViewById(R.id.registration_number);
        phone_number = findViewById(R.id.phone_number);
        adress = findViewById(R.id.adress);
        cancel = findViewById(R.id.cancel);
        enter = findViewById(R.id.save);
        enter_change_adress = findViewById(R.id.enter_change_adress);
        enter_change_name = findViewById(R.id.enter_change_name);
        enter_change_phone_number = findViewById(R.id.enter_change_phone_number);
        enter_change_registration_number = findViewById(R.id.enter_change_register_number);
    }

    public void bindingEvent(){
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

    @Override
    public void settings() {
        name.setText(localPersonalDatabase.getName());
        registation_number.setText(localPersonalDatabase.getRegistrationNumber());
        adress.setText(localPersonalDatabase.getBasicAdress() + '\n' + localPersonalDatabase.getDetailedAdress());
        phone_number.setText(localPersonalDatabase.getPhoneNumber());
    }

    @Override
    protected void onResume() {
        super.onResume();
        settings();
    }
}