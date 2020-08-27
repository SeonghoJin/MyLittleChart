package com.e.mylittlechart.checkdiagnosticinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.e.mylittlechart.R;
import com.e.mylittlechart.example.MyActivity;

public class activity_check_diagnostic_classification_rate extends AppCompatActivity implements MyActivity {
    Button cancel;
    WebView webView;
    Intent queryData;
    String maininjurycode;
    String viceinjurycode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_diagnostic_classification_rate);
        init();
        bindingObject();
        bindingEvent();
        settings();
    }

    @Override
    public void init() {
        queryData = getIntent();
        maininjurycode = queryData.getExtras().getString("maininjurycode");
        viceinjurycode = queryData.getExtras().getString("viceinjurycode");
    }

    @Override
    public void bindingObject() {
        cancel = findViewById(R.id.cancel);
        webView = findViewById(R.id.webview);
    }

    @Override
    public void bindingEvent() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void settings() {
        String BaseURL = "https://seongho-test.run.goorm.io/visualize/chart.php?main_injury_code=%s&vice_injury_code=%s";
        if(maininjurycode.length() == 0 && viceinjurycode.length() == 0){
            Toast.makeText(getApplicationContext(),"주상병코드와 부상병코드가 없는 진료기록입니다",Toast.LENGTH_LONG).show();
            finish();
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.loadUrl(String.format(BaseURL,maininjurycode, viceinjurycode));

        }


}