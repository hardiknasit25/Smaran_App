package com.example.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "name";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "table_name";
    private static final String ID = "id";
    private static final String SUTRA = "sutra";

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME +
                "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SUTRA + " TEXT" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void adddata(String sutra){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Values = new ContentValues();
        Values.put(SUTRA, sutra);

        db.insert(TABLE_NAME, null, Values);
    }

    public ArrayList<String> fetchdata(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<String> arrdata = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(1);
                arrdata.add(name);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

//        while(cursor.moveToNext()){
//
//            Namemodel model = new Namemodel();
//            model.sutra = cursor.getString(1);
//
//            adddata.add(model);
//
//        }
        return arrdata;
    }


}
