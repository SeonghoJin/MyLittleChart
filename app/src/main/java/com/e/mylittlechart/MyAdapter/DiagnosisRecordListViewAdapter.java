package com.e.mylittlechart.MyAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.e.mylittlechart.DiagnoseRecord.DiagnoseRecord;
import com.e.mylittlechart.DiagnoseRecord.DiagnosisRecordView;

import java.util.ArrayList;

public class DiagnosisRecordListViewAdapter extends BaseAdapter {
    ArrayList<DiagnoseRecord> records = new ArrayList<DiagnoseRecord>();
    Context context;

    public DiagnosisRecordListViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return records.size();
    }

    public void addRecord(DiagnoseRecord record){
        records.add(record);
    }

    @Override
    public Object getItem(int i) {
        return records.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        DiagnosisRecordView recordView = new DiagnosisRecordView(context);
        DiagnoseRecord record = records.get(i);
        recordView.setRecord(record);
        return recordView;
    }
}
