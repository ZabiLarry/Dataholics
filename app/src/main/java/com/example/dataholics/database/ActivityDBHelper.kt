package com.example.dataholics.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.dataholics.database.DBContract.ActivityEntry.Companion.COLUMN_ACTIVITY_ID
import com.example.dataholics.database.DBContract.ActivityEntry.Companion.COLUMN_ACTIVITY_TYPE
import com.example.dataholics.database.DBContract.ActivityEntry.Companion.COLUMN_COMPANY
import com.example.dataholics.database.DBContract.ActivityEntry.Companion.COLUMN_DATE
import com.example.dataholics.database.DBContract.ActivityEntry.Companion.COLUMN_TIME
import com.example.dataholics.database.DBContract.ActivityEntry.Companion.DATABASE_NAME
import com.example.dataholics.database.DBContract.ActivityEntry.Companion.DATABASE_VERSION
import com.example.dataholics.database.DBContract.ActivityEntry.Companion.TABLE_NAME

class ActivityDBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val CREATE_ACTIVITY_TABLE = ("CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ACTIVITY_ID + " INTEGER PRIMARY KEY," + COLUMN_ACTIVITY_TYPE + " INTEGER,"
            + COLUMN_COMPANY  + " INTEGER," + COLUMN_DATE + " TEXT," + COLUMN_TIME + "INTEGER)")


    override fun onCreate(db: SQLiteDatabase?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        db?.execSQL(CREATE_ACTIVITY_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }


    fun addActivity(activityType: Int, company: Int, date: String, time: Int) {
        //Gets the repo to write mode
        val db = writableDatabase

        //Mapping all the values to go in
        val values = ContentValues()
        values.put(COLUMN_ACTIVITY_TYPE, activityType)
        values.put(COLUMN_COMPANY, company)
        values.put(COLUMN_DATE, date)
        values.put(COLUMN_TIME, time)

        //Inserting the new row
        try {
            val newRowId = db.insert(TABLE_NAME, null, values)
        } catch (e:SQLiteException){
            db.execSQL(CREATE_ACTIVITY_TABLE)
            val newRowId = db.insert(TABLE_NAME, null, values)
        }

    }

    fun deleteActivity(id: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "activityId = ?", arrayOf(id))
    }

    fun showActivity(id: Int): ArrayList<Activity>{
        val activity = ArrayList<Activity>()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ACTIVITY_ID + " = '" + id + "'"
        val cursor: Cursor
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException){
            db.execSQL(CREATE_ACTIVITY_TABLE)
            return activity
        }

        var company: Int
        var activityType: Int
        var date: String
        var time: Int

        if(cursor!!.moveToFirst()){
            while (!cursor.isAfterLast){
                company = cursor.getInt(cursor.getColumnIndex(COLUMN_COMPANY))
                activityType = cursor.getInt(cursor.getColumnIndex(COLUMN_ACTIVITY_TYPE))
                date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))
                time = cursor.getInt(cursor.getColumnIndex(COLUMN_TIME))

                activity.add(Activity(id, company, activityType, date, time))
                cursor.moveToNext()

            }
        }
        return activity
    }

    fun allActivities(): ArrayList<Activity> {
        val db = this.writableDatabase
        val res = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val activityList = ArrayList<Activity>()
        val cursor: Cursor? = null

        var activityID: Int
        var company: Int
        var activityType: Int
        var date: String
        var time: Int

        if(cursor!!.moveToFirst()){
            while (cursor.isAfterLast == false){
                activityID = cursor.getInt(cursor.getColumnIndex(COLUMN_ACTIVITY_ID))
                company = cursor.getInt(cursor.getColumnIndex(COLUMN_COMPANY))
                activityType = cursor.getInt(cursor.getColumnIndex(COLUMN_ACTIVITY_TYPE))
                date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))
                time = cursor.getInt(cursor.getColumnIndex(COLUMN_TIME))

                activityList.add(Activity(activityID, company, activityType, date, time))
                cursor.moveToNext()
            }
        }
        return activityList
    }

}