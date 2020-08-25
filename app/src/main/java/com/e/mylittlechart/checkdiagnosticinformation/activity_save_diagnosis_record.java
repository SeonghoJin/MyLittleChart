package com.e.mylittlechart.checkdiagnosticinformation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.e.mylittlechart.DiagnoseRecord.DiagnoseRecord;
import com.e.mylittlechart.R;
import com.e.mylittlechart.database.LocalDiagnoseRecordDatabase;
import com.e.mylittlechart.example.MyActivity;
import com.e.mylittlechart.requestcode.MyRequestCode;

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
    String diseases[];
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
                    DiagnoseRecord record = new DiagnoseRecord();
                    record.setDate(dateStr);
                    record.setPlace(placeStr);
                    record.setMaininjurycode(maininjurycodeStr);
                    record.setViceinjurycode(viceinjurycodeStr);
                    record.setDisease(diseaseStr);
                    record.setDiagnosisclassification(diagnosisclassificationStr);
                    localDiagnoseRecordDatabase.insert(record);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),dateStr + " " + diseaseStr + " " + diagnosisclassificationStr, Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),"비어있는 칸이 있습니다", Toast.LENGTH_LONG).show();
                }
            }
        });

        maininjurycode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b == false) {
                    diseases[0] = "efgd";
                    disease.setText(diseases[0] + ","+ diseases[1]);
                }
            }
        });

        viceinjurycode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b == false){
                   diseases[1] = "abcd";
                    disease.setText(diseases[0] + ","+ diseases[1]);
                }
            }
        });

    }

    @Override
    public void settings() {

    }

    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        cancel.setFocusableInTouchMode(true);
        cancel.requestFocus();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}