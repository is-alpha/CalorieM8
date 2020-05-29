package com.dingo.caloriem8;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class CalM8SQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "cal_m8.db";
    private static final String FOODS_TABLE_NAME = "foods";
    private static final String FOODS_COL_ID = "_id";
    private static final String FOODS_COL_NAME = "name";
    private static final String FOODS_COL_SERVING = "serving";
    private static final String FOODS_COL_CALORIES = "calories";
    private static final String FOODS_COL_FAT = "fat";
    private static final String FOODS_COL_CARBS = "carbs";
    private static final String FOODS_COL_FIBER = "fiber";
    private static final String FOODS_COL_PROTEIN = "protein";

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
        contentValues.put("calories", calories);
        contentValues.put("fat", fat);
        contentValues.put("carbs", carbs);
        contentValues.put("fiber", fiber);
        contentValues.put("protein", protein);
        db.insert("foods", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor rVal = db.rawQuery("select * from foods where id="+id+";", null);
        return rVal;
    }

    public int numRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int rVal = (int) DatabaseUtils.queryNumEntries(db, FOODS_TABLE_NAME);
        return rVal;
    }

    public boolean updateFood(int id, String name, int serving, int calories, float fat, float carbs, float fiber, float protein) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("serving", serving);
        contentValues.put("calories", calories);
        contentValues.put("fat", fat);
        contentValues.put("carbs", carbs);
        contentValues.put("fiber", fiber);
        contentValues.put("protein", protein);
        db.update("foods", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public int deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("foods", "id = ? ", new String[]{Integer.toString(id)});
    }

    public ArrayList<String> getAllFoods() {
        ArrayList<String> arrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from foods", null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            arrayList.add(cursor.getString(cursor.getColumnIndex(FOODS_COL_NAME)));
            cursor.moveToNext();
        }
        return arrayList;
    }
}
