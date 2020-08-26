package com.e.mylittlechart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.e.mylittlechart.database.LocalPersonalDatabase;
import com.e.mylittlechart.example.MyActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.QRCodeDecoderMetaData;
import com.google.zxing.qrcode.encoder.QRCode;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

public class activity_generate_qrcode extends AppCompatActivity implements MyActivity {
    Button enter;
    Button download;
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
        download = findViewById(R.id.download);
    }

    public void bindingEvent(){
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBitmaptoJpeg(((BitmapDrawable)qrcode.getDrawable()).getBitmap());
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
                    "\"name\" : " + "\""+ name +"\"" +"," +
                    "\"phonenumber\" : " + "\""+ phoneNumber +"\"" + ","+
                    "\"registrationnumber\" : " + "\""+ registrationNumber +"\"" +"," +
                    "\"adress\" : " + "\""+ adress +"\""
                +
                "}";
        Log.d("test",data);
        generateQrCode(data);
    }

    private void generateQrCode(String text){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            Charset charset = Charset.forName("UTF-8");
            CharsetEncoder encoder = charset.newEncoder();

            byte[] b = encoder.encode(CharBuffer.wrap(text)).array();
            String data = new String(b, "iso8859-1");

            Writer writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(data,BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(matrix);
            qrcode.setImageBitmap(bitmap);

        }catch (Exception e){}
    }

    public static void saveBitmaptoJpeg(Bitmap bitmap){

        String my_path = Environment.getExternalStorageDirectory()+"/DCIM/Camera/MyQrCode.jpg";

        File file_path;
        try{
            file_path = new File(my_path);
            FileOutputStream out = new FileOutputStream(my_path);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
        }catch(FileNotFoundException exception){
            Log.e("FileNotFoundException", exception.getMessage());
        }catch(IOException exception){
            Log.e("IOException", exception.getMessage());
        }
    }


}