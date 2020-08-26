package com.e.mylittlechart;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class activity_read_qrCode extends AppCompatActivity {
    private IntentIntegrator qrScan;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_qr_code);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false);
        qrScan.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                finish();
            } else {
                String content = FilterIntent(result);
                createPersonDataIntoPdf(content);
                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public String FilterIntent(IntentResult result){
        String content = result.getContents();
        String realcontent= "";
        boolean flag = false;
        for(int i = 0; i < content.length(); i++){
            if(flag)break;
            realcontent += content.charAt(i);
            if(content.charAt(i) == '}'){
                flag = true;
            }
        }
        return realcontent;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void createPersonDataIntoPdf(String data){
        PersonalData personalData = new Gson().fromJson(data,PersonalData.class);

        String[] informationArray = new String[]{"이름", "주민등록번호", "휴대폰 번호", "주소"};
        String[] personalValues = new String[]{personalData.name,personalData.registrationnumber,personalData.phonenumber,personalData.adress};
        PdfDocument pdf = new PdfDocument();
        Paint paint = new Paint();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300,300,1).create();
        PdfDocument.Page page= pdf.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(18f);
        canvas.drawText("진료 접수증",pageInfo.getPageWidth()/2,50,paint);

        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(16f);
        int startXPosition = 10;
        int endXPosition = pageInfo.getPageWidth()-10;
        int startYPosition = 100;

        for(int i = 0; i < 4; i++){
            canvas.drawText(informationArray[i],startXPosition,startYPosition+(i ==3 ? 20 : 0),paint);

            if(i == 3){
                String str[] = new String[3];
                String main = personalValues[3];
                int len = main.length()-1;
                int startx = 0;
                for(int j = 0; j < 3; j++){
                    str[j] = personalValues[3].substring(startx < len? startx : len, startx+19 < len ? startx+19 : len);
                    startx+=19;
                }
                canvas.drawText(str[0], 113, startYPosition, paint);
                canvas.drawText(str[1], 113, startYPosition+20, paint);
                canvas.drawText(str[2], 113, startYPosition+40, paint);

            }
            else {
                canvas.drawText(personalValues[i], 113, startYPosition, paint);
            }
            canvas.drawLine(startXPosition,startYPosition+3 + (i == 3 ? 40 : 0),endXPosition,startYPosition+3+ (i == 3 ? 40 : 0),paint);
            startYPosition+=20;
        }
        canvas.drawLine(110,80,110,210,paint);

        pdf.finishPage(page);
        File file = new File(Environment.getExternalStorageDirectory(),"/person.pdf");
        try {
            pdf.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pdf.close();
        openPDF();

    }

    public void openPDF() {
        String filepath = Environment.getExternalStorageDirectory() + "/person.pdf";
        File file = new File(filepath);

        if (file.exists()) {
            Uri uri = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            try {
                startActivity(intent);
            } catch(ActivityNotFoundException e) {
                Toast.makeText(this, "PDF 파일을 보기 위한 뷰어 앱이 없습니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "PDF 파일이 없습니다.", Toast.LENGTH_SHORT).show();
        }

    }
}