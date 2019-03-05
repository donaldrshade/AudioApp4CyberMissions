package com.lightsys.audioapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lightsys.audioapp.R;

/**************************************************************************************************
 *                              Created by nicholasweg on 6/27/17.                                *
 *  Any changes made to this file regarding the database structure won't take effect unless you   *
 *  reinstall the app. The onCreate function only runs the first time the app is made. There may  *
 *  be a need to writed update functions so that won't have to happen if that situation comes up  *
 *  in the future. Otherwise you can use the onUpgrade function.                                  *
 **************************************************************************************************
 *
 * Correction to the above comment. While changes to the app while developement should be used with
 * a reinstall, you must watch for changes to the database on a new version. This changes should be
 * onUpgrade as a new case statement.
 * -DSHADE
 *
 * Used in AudioApp
 */

public class DatabaseConnection extends SQLiteOpenHelper {
    //Lesson variables
    public static final String LESSON_TABLE_NAME = "active_messages";
    public static String LESSON_COL_1 = "COURSE_NAME";
    public static String LESSON_COL_2 = "LESSON_NAME";
    public static String LESSON_COL_3 = "SEEK_TIME";
    public static String LESSON_COL_4 = "NOTE";
    private Resources r;

    private static final int versionNumber = 1;

    public DatabaseConnection(Context context) {
        super(context, context.getResources().getString(R.string.app_name)+".db", null, versionNumber);
        r = context.getResources();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //This function only runs the first time the app is run. See comment above.
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //new code will go here for the upgrade
        //Use the Alter Table table_name ADD new_column_name column_default_data
        //find out more at https://www.techonthenet.com/sqlite/tables/alter_table.php
        switch(newVersion){

        }
    }

    private void createTables(SQLiteDatabase db){
        String lessonTableCreate = String.format("create table " + LESSON_TABLE_NAME + " ( %s TEXT, %s TEXT PRIMARY KEY,%s INTEGER,%s TEXT)", LESSON_COL_1,LESSON_COL_2,LESSON_COL_3,LESSON_COL_4);
        db.execSQL(lessonTableCreate);
    }

    public void addLesson(Lesson lesson){
        SQLiteDatabase db = this.getWritableDatabase();
        //Do we need a check statement?YES
        String query = "select * from " + LESSON_TABLE_NAME + " where "+LESSON_COL_1+" = ?"+" AND "+LESSON_COL_2+" = ?";
        Cursor res = db.rawQuery(query, new String[]{lesson.getCourse(),lesson.getName()});
        if(res.moveToNext() == true){
            return;
        }



        ContentValues contentValues = new ContentValues();
        contentValues.put(LESSON_COL_1, lesson.getCourse());
        contentValues.put(LESSON_COL_2, lesson.getName());
        contentValues.put(LESSON_COL_3, lesson.getSeekTime());
        contentValues.put(LESSON_COL_4, lesson.getNotes());
        db.insert(LESSON_TABLE_NAME, null, contentValues);
    }
    public void updateLesson(Lesson lesson){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LESSON_COL_1, lesson.getCourse());
        contentValues.put(LESSON_COL_2, lesson.getName());
        contentValues.put(LESSON_COL_3, lesson.getSeekTime());
        contentValues.put(LESSON_COL_4, lesson.getNotes());
        String where = LESSON_COL_1+"=? AND "+LESSON_COL_2+"=?";
        String[] whereArgs = new String[] {lesson.getCourse(),lesson.getName()};
        db.update(LESSON_TABLE_NAME, contentValues, where, whereArgs);
    }
    public int getSeekTime(Lesson lesson) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " + LESSON_TABLE_NAME + " where "+LESSON_COL_1+" = ?"+" AND "+LESSON_COL_2+" = ?";
        Cursor res = db.rawQuery(query, new String[]{lesson.getCourse(),lesson.getName()});
        res.moveToNext();
        int temp = res.getInt(res.getColumnIndex(LESSON_COL_3));
        res.close();
        return temp;
    }
    public String getNotes(Lesson lesson) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " + LESSON_TABLE_NAME + " where "+LESSON_COL_1+" = ?"+" AND "+LESSON_COL_2+" = ?";
        Cursor res = db.rawQuery(query, new String[]{lesson.getCourse(),lesson.getName()});
        res.moveToNext();
        String temp = res.getString(res.getColumnIndex(LESSON_COL_4));
        res.close();
        return temp;
    }




}
