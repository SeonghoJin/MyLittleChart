package com.e.mylittlechart.DiagnoseRecord;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.e.mylittlechart.R;
import com.e.mylittlechart.checkdiagnosticinformation.activity_disease_details;
import com.e.mylittlechart.example.MyActivity;

public class DiagnosisRecordView extends LinearLayout implements MyActivity {
    Context context;
    DiagnoseRecord record;
    Button enter_more_information;
    TextView date;
    TextView sickness;
    public DiagnosisRecordView(Context context) {
        super(context);
        this.context = context;
        init();
        bindingObject();
        bindingEvent();
        settings();
    }

    public DiagnosisRecordView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
        bindingObject();
        bindingEvent();
        settings();
    }

    private void inflate(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.diagnosis_record, this, true);
    }

    @Override
    public void init() {
        inflate(context);
    }

    @Override
    public void bindingObject() {
        date = findViewById(R.id.date);
        sickness = findViewById(R.id.sickness);
        enter_more_information = findViewById(R.id.enter_more_information);
    }

    @Override
    public void bindingEvent() {
        enter_more_information.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, activity_disease_details.class);
                intent.putExtra("id", record.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void settings() {
        if(record != null){
            date.setText(record.getDate());
            sickness.setText(record.getDisease());
        }
    }

    public void setRecord(DiagnoseRecord record){
        this.record = record;
        settings();
    }
}
