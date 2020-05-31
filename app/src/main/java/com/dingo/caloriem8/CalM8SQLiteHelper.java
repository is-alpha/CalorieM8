package com.dingo.caloriem8;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class CalM8SQLiteHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "cal_m8.db";
    public static final String FOODS_TABLE_NAME = "foods";
    public static final String FOODS_COL_ID = "_id";
    public static final String FOODS_COL_NAME = "name";
    public static final String FOODS_COL_SERVING = "serving";
    public static final String FOODS_COL_CALORIES = "calories";
    public static final String FOODS_COL_FAT = "fat";
    public static final String FOODS_COL_CARBS = "carbs";
    public static final String FOODS_COL_FIBER = "fiber";
    public static final String FOODS_COL_PROTEIN = "protein";

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
        db.close();
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor rVal = db.rawQuery("select * from foods where _id="+id+";", null);
        db.close();
        return rVal;
    }

    public int getNumRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int rVal = (int) DatabaseUtils.queryNumEntries(db, FOODS_TABLE_NAME);
        db.close();
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
        db.update("foods", contentValues, "_id = ? ", new String[]{Integer.toString(id)});
        db.close();
        return true;
    }

    public void clearFoodsDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS foods");
        onCreate(db);
        db.close();
    }

    public int deleteFood(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("foods", "_id = ? ", new String[]{Integer.toString(id)});
    }

    public ArrayList<String> getAllFoodNames() {
        ArrayList<String> arrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from foods", null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            arrayList.add(cursor.getString(cursor.getColumnIndex(FOODS_COL_NAME)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return arrayList;
    }

    public ArrayList<Food> getAllFoods() {
        Food foodItem;
        ArrayList<Integer> idArrList = new ArrayList<Integer>();
        ArrayList<String> nameArrList = new ArrayList<String>();
        ArrayList<Integer> servingArrList = new ArrayList<Integer>();
        ArrayList<Integer> caloriesArrList = new ArrayList<Integer>();
        ArrayList<Float> fatArrList = new ArrayList<Float>();
        ArrayList<Float> carbsArrList = new ArrayList<Float>();
        ArrayList<Float> fiberArrList = new ArrayList<Float>();
        ArrayList<Float> proteinArrList = new ArrayList<Float>();
        ArrayList<Food> foodArrayList = new ArrayList<Food>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from foods", null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            idArrList.add(cursor.getInt(cursor.getColumnIndex(FOODS_COL_ID)));
            nameArrList.add(cursor.getString(cursor.getColumnIndex(FOODS_COL_NAME)));
            servingArrList.add(cursor.getInt(cursor.getColumnIndex(FOODS_COL_SERVING)));
            caloriesArrList.add(cursor.getInt(cursor.getColumnIndex(FOODS_COL_CALORIES)));
            fatArrList.add(cursor.getFloat(cursor.getColumnIndex(FOODS_COL_FAT)));
            carbsArrList.add(cursor.getFloat(cursor.getColumnIndex(FOODS_COL_CARBS)));
            fiberArrList.add(cursor.getFloat(cursor.getColumnIndex(FOODS_COL_FIBER)));
            proteinArrList.add(cursor.getFloat(cursor.getColumnIndex(FOODS_COL_PROTEIN)));

            cursor.moveToNext();
        }
        for(int i = 0; i < idArrList.size(); i++) {
            foodItem = new Food();
            foodItem.setId(idArrList.get(i));
            foodItem.setName(nameArrList.get(i));
            foodItem.setServing(servingArrList.get(i));
            foodItem.setCalories(caloriesArrList.get(i));
            foodItem.setFat(fatArrList.get(i));
            foodItem.setCarbs(carbsArrList.get(i));
            foodItem.setFiber(fiberArrList.get(i));
            foodItem.setProtein(proteinArrList.get(i));
            foodArrayList.add(foodItem);
        }
        cursor.close();
        db.close();
        return foodArrayList;
    }
}
