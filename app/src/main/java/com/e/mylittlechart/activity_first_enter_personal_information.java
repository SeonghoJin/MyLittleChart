package com.e.mylittlechart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e.mylittlechart.adress.activity_adress;
import com.e.mylittlechart.database.LocalPersonalDatabase;
import com.e.mylittlechart.requestcode.MyRequestCode;

public class activity_first_enter_personal_information extends AppCompatActivity {

    EditText name;
    EditText registration_number;
    EditText phone_number;
    EditText basic_adress;
    EditText detail_adress;
    Button cancel;
    Button save;
    LocalPersonalDatabase localPersonalDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_enter_personal_information);
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        init();
        bindingObject();
        bindingEvent();
    }

    private void init(){
        localPersonalDatabase = new LocalPersonalDatabase(getApplicationContext());
    }

    private void bindingObject(){
        name = findViewById(R.id.name);
        registration_number = findViewById(R.id.registration_number);
        phone_number = findViewById(R.id.phone_number);
        basic_adress = findViewById(R.id.basic_adress);
        detail_adress = findViewById(R.id.detail_adress);
        cancel = findViewById(R.id.cancel);
        save = findViewById(R.id.save);

    }

    private void bindingEvent(){
       save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String nameStr = name.getText().toString();
               String phone_numberStr = phone_number.getText().toString();
               String registartion_numberStr = registration_number.getText().toString();
               String basic_adressStr = basic_adress.getText().toString();
               String detail_adressStr = detail_adress.getText().toString();

               boolean saveFlag = true;
               saveFlag = saveFlag & (nameStr.length() > 0);
               saveFlag = saveFlag & (phone_numberStr.length() > 0);
               saveFlag = saveFlag & (registartion_numberStr.length() > 0);
               saveFlag = saveFlag & (basic_adressStr.length() > 0);
               saveFlag = saveFlag & (detail_adressStr.length() > 0);

               if(saveFlag){
                   localPersonalDatabase.insertColumn();
                   localPersonalDatabase.setName(nameStr);
                   localPersonalDatabase.setPhoneNumber(phone_numberStr);
                   localPersonalDatabase.setRegistrationNumber(registartion_numberStr);
                   localPersonalDatabase.setBasicAdress(basic_adressStr);
                   localPersonalDatabase.setDetailedAdress(detail_adressStr);
                   finish();
               }
               else{
                   Toast.makeText(getApplicationContext(),"비어 있는 칸이 있습니다",Toast.LENGTH_LONG).show();
               }
           }
       });

       basic_adress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View view, boolean b) {
               if(b == true) {
                   Intent intent = new Intent(getApplicationContext(), activity_adress.class);
                   startActivityForResult(intent, MyRequestCode.ACTIVITY_FIRST_ENTER_PERSONAL_INFORMATION_SEND);
               }
           }
       });

       cancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               termiate();
           }
       });

    }

    private void termiate(){
        moveTaskToBack(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAndRemoveTask();
        }
        Process.killProcess(Process.myPid());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == MyRequestCode.ACTIVITY_FIRST_ENTER_PERSONAL_INFORMATION_SEND){
            if(resultCode == MyRequestCode.SEND_OK_ACTIVITY_ADRESS){
                String arg1 = data.getStringExtra("arg1");
                String arg2 = data.getStringExtra("arg2");
                String arg3 = data.getStringExtra("arg3");

                basic_adress.setText(arg1 + " " + arg2 + " " + arg3);
            }
        }
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
    public void onBackPressed() {
        super.onBackPressed();
        termiate();
    }


}