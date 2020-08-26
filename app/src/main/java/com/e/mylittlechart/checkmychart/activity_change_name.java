package com.e.mylittlechart.checkmychart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e.mylittlechart.R;
import com.e.mylittlechart.database.LocalPersonalDatabase;
import com.e.mylittlechart.example.MyActivity;

public class activity_change_name extends AppCompatActivity implements MyActivity {

    Button cancel;
    Button save;
    EditText name;
    LocalPersonalDatabase localPersonalDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);

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
        cancel = findViewById(R.id.cancel);
        save = findViewById(R.id.save);
    }

    public void bindingEvent(){
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean saveFlag = true;
                String nameStr = name.getText().toString();
                saveFlag = saveFlag & (nameStr.length() > 0);

                if(saveFlag){
                    localPersonalDatabase.setName(nameStr);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"비어 있는 칸이 있습니다",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void settings() {
        name.setText(localPersonalDatabase.getName());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View focusedView = getCurrentFocus();
        if(focusedView != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return true;
    }
}