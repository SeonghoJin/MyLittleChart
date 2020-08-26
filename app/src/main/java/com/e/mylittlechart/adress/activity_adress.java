package com.e.mylittlechart.adress;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.e.mylittlechart.R;
import com.e.mylittlechart.example.MyActivity;
import com.e.mylittlechart.requestcode.MyRequestCode;

public class activity_adress extends AppCompatActivity implements MyActivity {

    Button cancel;
    WebView webView;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress);

        init();
        bindingObject();
        bindingEvent();
        settings();

        init_webView();
    }

    @Override
    public void init() {
        handler = new Handler();
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

    }

    public void init_webView() {

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.addJavascriptInterface(new AndroidBridge(), "TestApp");
        webView.setWebChromeClient(new webChromeClient(this));
        webView.loadUrl("https://seongho-test.run.goorm.io/searchAdress.php");

    }


    private class AndroidBridge {

        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {

            handler.post(new Runnable() {

                @Override

                public void run() {
                    Log.d("test","test");
                    Intent intent = new Intent();
                    intent.putExtra("arg1", arg1);
                    intent.putExtra("arg2", arg2);
                    intent.putExtra("arg3", arg3);
                    setResult(MyRequestCode.SEND_OK_ACTIVITY_ADRESS,intent);
                    finish();

                }

            });

        }

    }

    private class webChromeClient extends WebChromeClient {
        private Activity activity;
        private webChromeClient(Activity activity){
            this.activity = activity;
        }
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            // Dialog Create Code
            WebView newWebView = new WebView(activity_adress.this);
            WebSettings webSettings = newWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);

            final Dialog dialog = new Dialog(activity_adress.this);
            dialog.setContentView(newWebView);

            ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
            dialog.show();
            newWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onCloseWindow(WebView window) {
                    dialog.dismiss();
                }
            });

            // WebView Popup에서 내용이 안보이고 빈 화면만 보여 아래 코드 추가


            ((WebView.WebViewTransport)resultMsg.obj).setWebView(newWebView);
            resultMsg.sendToTarget();
            return true;

        }


    }


}