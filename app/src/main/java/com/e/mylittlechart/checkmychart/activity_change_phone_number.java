package com.e.mylittlechart.checkmychart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.e.mylittlechart.R;

public class activity_change_phone_number extends AppCompatActivity {

    Button cancel;
    Button save;
    EditText phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone_number);

        bindingObject();
        bindingEvent();
    }

    private void bindingObject(){
        phone_number = findViewById(R.id.phone_number);
        cancel = findViewById(R.id.cancel);
        save = findViewById(R.id.save);
    }

    private void bindingEvent(){
        cancel.setOnClickListener(new View.OnClickListener() {
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