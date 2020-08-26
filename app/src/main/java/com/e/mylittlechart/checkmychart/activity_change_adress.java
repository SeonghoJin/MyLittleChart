package com.e.mylittlechart.checkmychart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e.mylittlechart.R;
import com.e.mylittlechart.adress.activity_adress;
import com.e.mylittlechart.database.LocalPersonalDatabase;
import com.e.mylittlechart.example.MyActivity;
import com.e.mylittlechart.requestcode.MyRequestCode;

public class activity_change_adress extends AppCompatActivity  implements MyActivity {

    Button cancel;
    Button save;
    EditText basic_adress;
    EditText detail_adress;
    LocalPersonalDatabase localPersonalDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_adress);

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
        basic_adress = findViewById(R.id.basic_adress);
        detail_adress = findViewById(R.id.detail_adress);
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
        basic_adress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b == true) {
                    Intent intent = new Intent(getApplicationContext(), activity_adress.class);
                    startActivityForResult(intent, MyRequestCode.ACTIVITY_CHANGE_ADRESS_SEND);
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean saveFlag = true;
                String basicAdressStr = basic_adress.getText().toString();
                String detailAdressStr = detail_adress.getText().toString();
                saveFlag = saveFlag & (basicAdressStr.length() > 0);
                saveFlag = saveFlag & (detailAdressStr.length() > 0 );

                if(saveFlag){
                    localPersonalDatabase.setBasicAdress(basicAdressStr);
                    localPersonalDatabase.setDetailedAdress(detailAdressStr);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"비어 있는 칸이 있습니다",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void settings(){
        save.setFocusableInTouchMode(true);
        save.requestFocus();
        basic_adress.setText(localPersonalDatabase.getBasicAdress());
        detail_adress.setText(localPersonalDatabase.getDetailedAdress());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MyRequestCode.ACTIVITY_CHANGE_ADRESS_SEND){
            if(resultCode == MyRequestCode.SEND_OK_ACTIVITY_CHANGE_ADRESS){
                String arg1 = data.getStringExtra("arg1");
                String arg2 = data.getStringExtra("arg2");
                String arg3 = data.getStringExtra("arg3");
                basic_adress.setText(arg1 + " " + arg2 + " " + arg3);
            }
        }
    }

}