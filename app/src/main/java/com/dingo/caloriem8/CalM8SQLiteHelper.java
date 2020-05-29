package com.dingo.caloriem8;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CalM8SQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "cal_m8.db";
    private static final String FOOD_TABLE_NAME = "foods";
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
        db.execSQL("CREATE TABLE foods" +
        "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, serving INTEGER, calories INTEGER, fat REAL, carbs REAL, fiber REAL, protein REAL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS foods");
        onCreate(db);
    }

    public boolean insertFood(String name, int serving, int calories, float fat, float carbs, float fiber, float protein) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("serving", serving);
        contentValues.put("fat", fat);
        return true;
    }

}
