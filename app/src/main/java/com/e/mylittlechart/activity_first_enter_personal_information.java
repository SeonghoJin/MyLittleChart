package com.e.mylittlechart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class activity_first_enter_personal_information extends AppCompatActivity {

    EditText name;
    EditText registation_number;
    EditText phone_number;
    EditText basic_adress;
    EditText detail_adress;
    Button cancel;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_enter_personal_information);
        bindingObject();
        bindingEvent();
    }

    private void bindingObject(){
        name = findViewById(R.id.registration_number);
        registation_number = findViewById(R.id.registration_number);
        phone_number = findViewById(R.id.registration_number);
        basic_adress = findViewById(R.id.basic_adress);
        detail_adress = findViewById(R.id.detail_adress);
        cancel = findViewById(R.id.cancel);
        save = findViewById(R.id.save);
    }

    private void bindingEvent(){
       save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();
           }
       });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

}