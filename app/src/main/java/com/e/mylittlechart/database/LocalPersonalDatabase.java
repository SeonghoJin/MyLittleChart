package com.e.mylittlechart.database;
import android.app.Person;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TableLayout;

public class LocalPersonalDatabase {

    private static SQLiteDatabase db;
    private static final String DATABASENAME = "LocalPersonalDatabase";
    private static final String TABLENAME = "PersonalData";
    private static final String TAG = "DATABASE";

    public LocalPersonalDatabase(Context context) {
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
            db.execSQL(String.format("create table if not exists %s (" +
                    "_id integer PRIMARY KEY autoincrement, " +
                    "name text default temp, " +
                    "phonenumber text default phonenumber, " +
                    "registrationnumber text default registrationnumber," +
                    "basicadress text default basicadress, " +
                    "detailedadress text detailedadress)", TABLENAME));
            Log.d(TAG,"create Table");
        }
    }

    public void insertColumn() throws Error{
        Log.d(TAG,"insert Column");
        if(db == null){
            throw new Error();
        }else{
            if(empty()) {
                db.execSQL(String.format("insert into %s " +
                        "(_id) values (1) ", TABLENAME));
            }
        }
    }

    public String getName(){
        Log.d(TAG, "getName()");
        Cursor cur = db.rawQuery("select name from " + TABLENAME, null);
        cur.moveToFirst();
        return cur.getString(0);
    }

    public void setName(String name){
        Log.d(TAG, "setName()");
        db.execSQL(String.format("update %s set name='%s'", TABLENAME, name));
    }
    public String getPhoneNumber(){
        Log.d(TAG, "getPhoneNumber()");
        Cursor cur = db.rawQuery("select phonenumber from " + TABLENAME, null);
        cur.moveToFirst();
        return cur.getString(0);
    }

    public void setPhoneNumber(String phonenumber){
        Log.d(TAG, "setPhoneNumber()");
        db.execSQL(String.format("update %s set phonenumber=%s", TABLENAME, phonenumber));
    }
    public String getRegistrationNumber(){
        Log.d(TAG, "getRegistrationNumber()");
        Cursor cur = db.rawQuery("select registrationnumber from " + TABLENAME, null);
        cur.moveToFirst();
        return cur.getString(0);
    }

    public void setRegistrationNumber(String registrationnumber){
        Log.d(TAG, "setRegistrationNumber()");
        db.execSQL(String.format("update %s set registrationnumber='%s'", TABLENAME, registrationnumber));
    }
    public String getBasicAdress(){
        Log.d(TAG, "getBasicAdress()");
        Cursor cur = db.rawQuery("select basicadress from " + TABLENAME, null);
        cur.moveToFirst();
        return cur.getString(0);
    }

    public void setBasicAdress(String basicadress){
        Log.d(TAG, "setBasicAdress()");
        db.execSQL(String.format("update %s set basicadress='%s'", TABLENAME, basicadress));
    }
    public String getDetailedAdress(){
        Log.d(TAG, "getDetailedAdress()");
        Cursor cur = db.rawQuery("select detailedadress from " + TABLENAME, null);
        cur.moveToFirst();
        return cur.getString(0);
    }

    public void setDetailedAdress(String detailedadress){
        Log.d(TAG, "setDetailedAdress()");
        db.execSQL(String.format("update %s set detailedadress='%s'", TABLENAME, detailedadress));
    }

    public boolean empty(){
        Log.d(TAG, "empty()");
        Cursor cur = db.rawQuery("select count(*) from " + TABLENAME, null);
        cur.moveToFirst();
        int result = cur.getInt(0);
        if(result == 1)return false;
        else return true;
    }

}