package com.dingo.caloriem8;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CalM8SQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "cal_m8.db";
    private static final String FOOD_TABLE_NAME = "food";
    private static final String FOOD_COL_ID = "_id";
    private static final String FOOD_COL_NAME = "name";
    private static final String FOOD_COL_SERVING = "serving";
    private static final String FOOD_COL_CALORIES = "calories";
    private static final String FOOD_COL_FAT = "fat";
    private static final String FOOD_COL_CARBS = "carbs";
    private static final String FOOD_COL_FIBER = "fiber";
    private static final String FOOD_COL_PROTEIN = "protein";

    private static final int DB_VERSION = 1;

    CalM8SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE FOOD" +
        "(_ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT,  TEXT);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public void insertUser(SQLiteDatabase db, String email, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", email);
        contentValues.put("PASSWD", password);
        db.insert("SIGNEDIN_USER", null , contentValues);
    }

    public boolean updateUser(SQLiteDatabase db, String email, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", email);
        contentValues.put("PASSWD", password);
        // Update all records (there's only 1)
        db.update("SIGNEDIN_USER", contentValues, null, null);
        return  true;
    }

    public void deleteUser(SQLiteDatabase db) {
        // Delete all records (there's only 1)
        db.delete("SIGNEDIN_USER", null, null);
    }

    public Cursor getUser(SQLiteDatabase db, int id) {
        Cursor val = db.rawQuery("SELECT * FROM SIGNEDIN_USER WHERE _ID="+id+";", null);
        return val;
    }

}
