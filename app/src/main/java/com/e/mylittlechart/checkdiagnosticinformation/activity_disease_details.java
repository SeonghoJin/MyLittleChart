package com.e.mylittlechart.checkdiagnosticinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.e.mylittlechart.DiagnoseRecord.DiagnoseRecord;
import com.e.mylittlechart.R;
import com.e.mylittlechart.database.LocalDiagnoseRecordDatabase;
import com.e.mylittlechart.example.MyActivity;

public class activity_disease_details extends AppCompatActivity implements MyActivity {

    Button cancel;
    Button check_diagnostic_classification_rate;
    TextView date;
    TextView place;
    TextView disease;
    TextView diseaseclassification;
    LocalDiagnoseRecordDatabase localDiagnoseRecordDatabase;
    Intent Data;
    int id;
    DiagnoseRecord record;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_details);
        init();
        bindingObject();
        bindingEvent();
        settings();
    }

    @Override
    public void init() {
        localDiagnoseRecordDatabase = new LocalDiagnoseRecordDatabase(getApplicationContext());
        Data = getIntent();
        id = Data.getExtras().getInt("id");
        record = localDiagnoseRecordDatabase.getData(id);
    }

    @Override
    public void bindingObject() {
        cancel = findViewById(R.id.cancel);
        date = findViewById(R.id.date);
        disease = findViewById(R.id.disease);
        place = findViewById(R.id.place);
        diseaseclassification = findViewById(R.id.diseaseclassification);
        check_diagnostic_classification_rate = findViewById(R.id.check_diagnositic_classification_rate);
    }

    @Override
    public void bindingEvent() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        check_diagnostic_classification_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), activity_check_diagnostic_classification_rate.class);
                intent.putExtra("maininjurycode", record.getMaininjurycode());
                intent.putExtra("viceinjurycode",record.getViceinjurycode());
                startActivity(intent);
            }
        });
    }

    @Override
    public void settings() {
        date.setText(record.getDate());
        place.setText(record.getPlace());
        disease.setText(record.getDisease());
        diseaseclassification.setText(record.getDiagnosisclassification());
    }
}