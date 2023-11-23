package com.example.overtime_scheduling;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "userdetails.db";

    public static final String TABLE_NAME = "user_details";
    public static final String ID = "Id";
    public static final String USERNAME = "Username";
    public static final String PASSWORD = "Password";
    public static final int VERSION_NUMBER = 1;
    private Context context;

    public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+USERNAME+" TEXT NOT NULL, "+PASSWORD+" TEXT NOT NULL)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DBNAME, null, VERSION_NUMBER);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("create table users(username TEXT primary key, password TEXT)");
        try {
            db.execSQL(CREATE_TABLE);
        }catch(Exception e)
        {
            Toast.makeText(context, "Exception : "+e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("drop table if exists users");
        try {
            Toast.makeText(context, "Updated Successfully", Toast.LENGTH_LONG).show();
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }catch(Exception e)
        {
            Toast.makeText(context, "Exception : "+e, Toast.LENGTH_LONG).show();
        }
    }

    public Boolean insertData(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USERNAME, username);
        values.put(PASSWORD, password);

        long result = db.insert("user_details", null, values);
        if(result == -1)
            return false;
        else
            return true;
    }




    public Boolean checkusername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE username=?", new String[] {username});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }
/*
    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user_details where username=? and password=?", new String[] {username, password});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }

 */
    public Boolean checkusernamepassword(String Username, String Password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        Boolean result = false;

        if(cursor.getCount() == 0){
            System.out.println("No Data Found !");
            //Toast.makeText(context, "No Data Found !", Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext()){
                String username = cursor.getString(1);
                String password = cursor.getString(2);

                if(username.equals(Username) && password.equals(Password)){
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
    public boolean deleteUserByUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, USERNAME + " = ?", new String[]{username}) > 0;
    }
}
