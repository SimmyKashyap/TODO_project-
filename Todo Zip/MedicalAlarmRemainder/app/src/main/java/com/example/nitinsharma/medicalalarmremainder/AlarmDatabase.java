package com.example.nitinsharma.medicalalarmremainder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nitin sharma on 28-Nov-18.
 */

public class AlarmDatabase extends SQLiteOpenHelper {
    String query =" CREATE TABLE Alarm ( Column_Id INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Hour INTEGER, Minute INTEGER,Status TEXT,Title_Status TEXT,Time_Status TEXT);";



    public AlarmDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "Alarm Database", factory, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
