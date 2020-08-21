package com.e.mylittlechart;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class activity_generate_qrcode extends AppCompatActivity {
    Button enter;
    ImageView qrcode;
    String dummy = "진성호";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qrcode);
        bindingObject();
        bindingEvent();
        generateQrCode(dummy);
    }

    private void bindingObject(){
        enter = findViewById(R.id.enter);
        qrcode = findViewById(R.id.qrcode);
    }

    private void bindingEvent(){
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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