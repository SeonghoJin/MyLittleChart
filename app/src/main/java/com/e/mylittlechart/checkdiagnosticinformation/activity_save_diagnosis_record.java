package com.e.mylittlechart.checkdiagnosticinformation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.e.mylittlechart.AppHelper;
import com.e.mylittlechart.DiagnoseRecord.DiagnoseRecord;
import com.e.mylittlechart.R;
import com.e.mylittlechart.database.LocalDiagnoseRecordDatabase;
import com.e.mylittlechart.example.MyActivity;
import com.e.mylittlechart.requestcode.MyRequestCode;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class activity_save_diagnosis_record extends AppCompatActivity implements MyActivity {
    Button cancel;
    Button save;
    EditText date;
    EditText place;
    EditText maininjurycode;
    EditText viceinjurycode;
    EditText disease;
    Spinner diagnosisclassification;
    LocalDiagnoseRecordDatabase localDiagnoseRecordDatabase;
    TextView loading;
    String diseases[];
    boolean Saving;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_diagnosis_record);
        init();
        bindingObject();
        bindingEvent();
        settings();
    }

    @Override
    public void init() {
        localDiagnoseRecordDatabase = new LocalDiagnoseRecordDatabase(getApplicationContext());
        diseases = new String[2];
        diseases[0] = "";
        diseases[1] = "";
        if(AppHelper.requestQueue == null){
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        Saving = false;
    }

    @Override
    public void bindingObject() {
        cancel = findViewById(R.id.cancel);
        save = findViewById(R.id.save);
        date = findViewById(R.id.date);
        place = findViewById(R.id.place);
        maininjurycode = findViewById(R.id.maininjurycode);
        viceinjurycode = findViewById(R.id.viceinjurycode);
        disease = findViewById(R.id.disease);
        diagnosisclassification = findViewById(R.id.diseaseclassification);
        loading = findViewById(R.id.loading);
    }

    @Override
    public void bindingEvent() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Saving){
                    Toast.makeText(getApplicationContext(),"저장중입니다. 잠시만 기다려주세요", Toast.LENGTH_LONG).show();
                    return;
                }
                String dateStr = date.getText().toString();
                String placeStr = place.getText().toString();
                String maininjurycodeStr = maininjurycode.getText().toString();
                String viceinjurycodeStr = viceinjurycode.getText().toString();
                String diseaseStr = disease.getText().toString();
                String diagnosisclassificationStr = diagnosisclassification.getSelectedItem().toString();


                Boolean saveFlag = true;
                saveFlag &= dateStr.length() > 0;
                saveFlag &= diseaseStr.length() > 0;
                saveFlag &= diagnosisclassificationStr.length() > 0;

                if(saveFlag){
                    Saving = true;
                    saveRecord(dateStr,placeStr,maininjurycodeStr,viceinjurycodeStr,diseaseStr,diagnosisclassificationStr);
                }
                else{
                    Toast.makeText(getApplicationContext(),"비어있는 칸이 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });

        maininjurycode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b == false) {
                    searchSickness(0,maininjurycode.getText().toString());
                }
            }
        });

        viceinjurycode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b == false){
                    searchSickness(1,viceinjurycode.getText().toString());
                }
            }
        });

    }

    @Override
    public void settings() {
        cancel.setFocusableInTouchMode(true);
        cancel.requestFocus();
    }

    public void searchSickness(final int index, String code){
        String url = "https://seongho-test.run.goorm.io/find.sickness.php?code="+code;

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("-1")) {
                            diseases[index] = "";
                        }
                        else{
                            Sickness sickness = new Gson().fromJson(response, Sickness.class);
                            diseases[index] = sickness.korean_name;
                        }
                        String diseaseStr = "";
                        diseaseStr += diseases[0];
                        diseaseStr += ((diseases[0].length() > 0 && diseases[1].length() > 0)? ',' : "") + diseases[1];
                        disease.setText(diseaseStr);
                    }
                },
                new Response.ErrorListener(){ //에러발생시 호출될 리스너 객체
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        disease.setText("찾을 수 없습니다. 직접 적어주세요.");
                    }
                }
        );

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);

    }

    public void saveRecord(final String dateStr, final String placeStr, final String maininjurycodeStr, final String viceinjurycodeStr, final String diseaseStr, final String diagnosisclassificationStr){

        String baseUrl = "https://seongho-test.run.goorm.io/record.insert.php?";
        String queryStr = "";
        queryStr += "date=" + dateStr+"&";
        queryStr += "place=" + placeStr+"&";
        queryStr += "maininjurycode=" + maininjurycodeStr+"&";
        queryStr += "viceinjurycode=" + viceinjurycodeStr+"&";
        queryStr += "diagnosisclassification=" + diagnosisclassificationStr;

        baseUrl += queryStr;
        final LoadingTask task = new LoadingTask(loading);
        task.execute();

        StringRequest request = new StringRequest(
                Request.Method.GET,
                baseUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("-1")){
                            Saving = false;
                            if(task.getStatus() == AsyncTask.Status.RUNNING){
                                task.cancel(true);
                                loading.setText("");
                            }
                            Toast.makeText(getApplicationContext(), "기록하지 못했습니다. 다시 시도해주세요.", Toast.LENGTH_LONG).show();
                        }
                        else {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    DiagnoseRecord record = new DiagnoseRecord();
                                    record.setDate(dateStr);
                                    record.setPlace(placeStr);
                                    record.setMaininjurycode(maininjurycodeStr);
                                    record.setViceinjurycode(viceinjurycodeStr);
                                    record.setDisease(diseaseStr);
                                    record.setDiagnosisclassification(diagnosisclassificationStr);
                                    localDiagnoseRecordDatabase.insert(record);
                                    Toast.makeText(getApplicationContext(), "기록하였습니다.", Toast.LENGTH_LONG).show();
                                    if(task.getStatus() == AsyncTask.Status.RUNNING){
                                        task.cancel(true);
                                        loading.setText("");
                                    }
                                    finish();
                                }
                            },1000);
                        }
                    }
                },
                new Response.ErrorListener(){ //에러발생시 호출될 리스너 객체
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Saving = false;
                        if(task.getStatus() == AsyncTask.Status.RUNNING){
                            task.cancel(true);
                            loading.setText("");
                        }
                        Toast.makeText(getApplicationContext(), "기록하지 못했습니다. 다시 시도해주세요.", Toast.LENGTH_LONG).show();
                    }
                }
        );
        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);

    }

    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View focusedView = getCurrentFocus();
        if(focusedView != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        settings();
        return true;
    }


    class LoadingTask extends AsyncTask<Void,Void,Void>{
        TextView loading;
        public LoadingTask(TextView loading) {
            super();
            this.loading = loading;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            int T = 30;
            while(T > 0){
                T--;
                try {
                    Thread.sleep(100);
                    publishProgress();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            loading.setText("Loading");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            String current = loading.getText().toString();
            if(current.equals("Loading")){
                current = "Loading.";
            }else if(current.equals("Loading.")){
                current = "Loading..";
            }else if(current.equals("Loading..")){
                current = "Loading...";
            }else if(current.equals("Loading...")){
                current = "Loading";
            }
            loading.setText(current);
        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            loading.setText("");
        }
    };

}