package com.e.mylittlechart;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.e.mylittlechart.database.LocalPersonalDatabase;
import com.e.mylittlechart.example.MyActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class activity_generate_qrcode extends AppCompatActivity implements MyActivity {
    Button enter;
    ImageView qrcode;
    LocalPersonalDatabase localPersonalDatabase;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qrcode);
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
        enter = findViewById(R.id.enter);
        qrcode = findViewById(R.id.qrcode);
    }

    public void bindingEvent(){
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void settings() {
        String name = localPersonalDatabase.getName();
        String phoneNumber = localPersonalDatabase.getPhoneNumber();
        String registrationNumber = localPersonalDatabase.getRegistrationNumber();
        String adress = localPersonalDatabase.getBasicAdress()
                        + '\n'
                        + localPersonalDatabase.getDetailedAdress();

        data = "{"
                +
                    "name : " + "'"+ name +"'" +"," +
                    "phonenumber : " + "'"+ phoneNumber +"'" + ","+
                    "registrationnumber : " + "'"+ registrationNumber +"'" +"," +
                    "adress : " + "'"+ adress +"'"
                +
                "}";
        Log.d("test",data);
        generateQrCode(data);
    }

    private void generateQrCode(String text){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrcode.setImageBitmap(bitmap);
        }catch (Exception e){}
    }
}