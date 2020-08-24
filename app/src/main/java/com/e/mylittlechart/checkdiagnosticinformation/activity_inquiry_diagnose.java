package com.e.mylittlechart.checkdiagnosticinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.e.mylittlechart.DiagnoseRecord.DiagnoseRecord;
import com.e.mylittlechart.DiagnoseRecord.DiagnosisClassification;
import com.e.mylittlechart.MyAdapter.DiagnosisRecordListViewAdapter;
import com.e.mylittlechart.R;
import com.e.mylittlechart.database.LocalDiagnoseRecordDatabase;
import com.e.mylittlechart.example.MyActivity;

public class activity_inquiry_diagnose extends AppCompatActivity implements MyActivity {
    Button cancel;
    ListView records;
    DiagnosisRecordListViewAdapter adapter;
    LocalDiagnoseRecordDatabase localDiagnoseRecordDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_diagnose);
        init();
        bindingObject();
        bindingEvent();
        settings();
    }

    @Override
    public void init() {
        adapter = new DiagnosisRecordListViewAdapter(getApplicationContext());
        localDiagnoseRecordDatabase = new LocalDiagnoseRecordDatabase(getApplicationContext());
    }

    @Override
    public void bindingObject() {
        cancel = findViewById(R.id.cancel);
        records = findViewById(R.id.records);
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
        for(int i = 0; i < localDiagnoseRecordDatabase.count(); i++){
            DiagnoseRecord record =localDiagnoseRecordDatabase.getData(i);
            record.setId(i);
            adapter.addRecord(record);
        }
        Log.d("adapter","어댑터에 넣을 레코드 생성");
        records.setAdapter(adapter);
    }

}