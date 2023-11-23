package com.example.overtime_scheduling;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Overtime_Data extends SQLiteOpenHelper {

    // TABLE INFORMATION
    static final String DB_NAME = "OVERTIME_DATA.DB";
    public final String TABLE_KEY_VALUE = "overtime_pairs";
    public final String KEY = "keyname";
    public final String VALUE = "itemvalue";
    //
    public Overtime_Data(Context context) {
        super(context, DB_NAME, null, 3);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("DB OnCreate called");
        createKeyValueTable(db);
    }
    private void createKeyValueTable(SQLiteDatabase db){
        try {
            db.execSQL("create table " + TABLE_KEY_VALUE + " (" + KEY + " TEXT, " + VALUE + " TEXT)");
        } catch (Exception e) {
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //createKeyValueTable(db);
        // SQL statements to change the database schema
    }
    private void handleError(SQLiteDatabase db, Exception e){
        String errorMsg = e.getMessage().toString();
        if (errorMsg.contains("no such table")){
            if (errorMsg.contains(TABLE_KEY_VALUE)){
                createKeyValueTable(db);
            }
        }
    }
    public Cursor execute(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;
        try {
            res = db.rawQuery(query, null);
        }catch (Exception e) {
            //e.printStackTrace();
            handleError(db, e);
            res = db.rawQuery(query, null);
        }
        return res;
    }

    public Boolean insertKeyValue(String key, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY, key);
        cv.put(VALUE, value);
        long result;
        try{
            result = db.insert(TABLE_KEY_VALUE, null, cv);
        }catch (Exception e){
            handleError(db, e);
            result = db.insert(TABLE_KEY_VALUE, null, cv);
        }
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public Integer deleteDataByKey(String key) {
        SQLiteDatabase db = this.getWritableDatabase();
        int isDeleted = 0;
        try{
            isDeleted = db.delete(TABLE_KEY_VALUE, KEY + " = ?", new String[] { key });
        }catch (Exception e){
            handleError(db, e);
            try {
                isDeleted = db.delete(TABLE_KEY_VALUE, KEY + " = ?", new String[]{key});
            }catch (Exception ex){}
        }
        return isDeleted;
    }

    public Integer deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_KEY_VALUE, null, null);
    }

    private void deleteRows(SQLiteDatabase db){
        try {
            db.execSQL("DELETE FROM TABLE_KEY_VALUE");
        } catch (Exception e) {
        }
    }

}
