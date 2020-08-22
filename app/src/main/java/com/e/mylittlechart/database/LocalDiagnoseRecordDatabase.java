package com.e.mylittlechart.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.e.mylittlechart.DiagnoseRecord.DiagnoseRecord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocalDiagnoseRecordDatabase {

    private static SQLiteDatabase db;
    private static final String DATABASENAME = "LocalDiagnoseRecordDatabase";
    private static final String TABLENAME = "records";
    private static final String TAG = "DiagnoseDATABASE";

    public LocalDiagnoseRecordDatabase(Context context) {
        if(db == null){
            try {
                db = context.openOrCreateDatabase(DATABASENAME, context.MODE_PRIVATE, null);
                createTable();
            }catch(Exception e){
                Log.d(TAG,"db error : " + e.getMessage());
            }
        }
    }

    private void createTable() throws Error {
        if(db == null){
            throw new Error();
        }else{
            Log.d(TAG,"create Table");
            db.execSQL(String.format("create table if not exists %s (" +
                    "_id integer PRIMARY KEY autoincrement, " +
                    "date text not null, " +
                    "place text, " +
                    "maininjurycode text," +
                    "viceinjurycode text, " +
                    "disease text not null, " +
                    "diagnosisclassification not null"+")", TABLENAME));
        }
    }

    public void insert(DiagnoseRecord record) throws Error{
        Log.d(TAG,"insert");
        if(db == null){
            Log.d(TAG,"insert Error");
            throw new Error();
        }else{
            ArrayList<String> columns = new ArrayList<String>();
            ArrayList<String> values = new ArrayList<String>();

            if(record.getDate().length() > 0){
                columns.add("date");
                values.add(record.getDate());
            }
            if(record.getPlace().length() > 0){
                columns.add("place");
                values.add(record.getPlace());
            }
            if(record.getMaininjurycode().length() > 0){
                columns.add("maininjurycode");
                values.add(record.getMaininjurycode());
            }
            if(record.getViceinjurycode().length() > 0){
                columns.add("viceinjurycode");
                values.add(record.getViceinjurycode());
            }
            if(record.getDisease().length() > 0){
                columns.add("disease");
                values.add(record.getDisease());
            }
            if(record.getDiagnosisclassification().length() > 0){
                columns.add("diagnosisclassification");
                values.add(record.getDiagnosisclassification());
            }
            String columnsStr = "";
            String valuesStr = "";

            for(int i = 0; i < columns.size(); i++){
                columnsStr += columns.get(i);
                if(i+1 < columns.size()){
                    columnsStr += ',';
                }
            }

            for(int i = 0; i < values.size(); i++){
                valuesStr += "'"+values.get(i)+"'";
                if(i+1 < values.size()){
                    valuesStr += ',';
                }
            }

            db.execSQL(String.format("insert into %s (%s) values (%s) ", TABLENAME, columnsStr, valuesStr));

        }
    }

    public int count(){
        Log.d(TAG,"count");
        Cursor cur = db.rawQuery(String.format("select count(*) from "+TABLENAME), null);
        cur.moveToFirst();
        return cur.getInt(0);
    }

    public boolean empty(){
        Log.d(TAG,"empty");
        return count() > 0 ? false : true;
    }

    public DiagnoseRecord getData(int index){
        index++;
        Log.d(TAG,"getData");
        DiagnoseRecord record = new DiagnoseRecord();
        Cursor cur = db.rawQuery(String.format("select * from %s where _id = %d",TABLENAME,index),null);
        cur.moveToFirst();
        record.setDate(cur.getString(1) == null ? "" : cur.getString(1));
        record.setPlace(cur.getString(2) == null ? "" : cur.getString(2));
        record.setMaininjurycode(cur.getString(3) == null ? "" : cur.getString(3));
        record.setViceinjurycode(cur.getString(4) == null ? "" : cur.getString(4));
        record.setDisease(cur.getString(5) == null ? "" : cur.getString(5));
        record.setDiagnosisclassification(cur.getString(6) == null ? "" : cur.getString(6));
        return record;
    }

}
