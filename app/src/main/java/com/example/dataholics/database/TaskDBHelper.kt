package com.example.dataholics.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.dataholics.database.DBContract.TaskEntry.Companion.COLUMN_TASK_ID
import com.example.dataholics.database.DBContract.TaskEntry.Companion.COLUMN_ACTIVITY
import com.example.dataholics.database.DBContract.TaskEntry.Companion.COLUMN_COMPANY
import com.example.dataholics.database.DBContract.TaskEntry.Companion.COLUMN_DATE
import com.example.dataholics.database.DBContract.TaskEntry.Companion.COLUMN_TIME
import com.example.dataholics.database.DBContract.TaskEntry.Companion.DATABASE_NAME
import com.example.dataholics.database.DBContract.TaskEntry.Companion.DATABASE_VERSION
import com.example.dataholics.database.DBContract.TaskEntry.Companion.TABLE_NAME

class TaskDBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val CREATE_TASK_TABLE = ("CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_TASK_ID + " INTEGER PRIMARY KEY," + COLUMN_ACTIVITY + " INTEGER,"
            + COLUMN_COMPANY + " INTEGER," + COLUMN_DATE + " TEXT," + COLUMN_TIME + "TEXT)")


    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL(CREATE_TASK_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }


    fun addTask(activity: Int, company: Int, date: String, time: String) {
        //Gets the repo to write mode
        val db = writableDatabase

        //Mapping all the values to go in
        val values = ContentValues()
        values.put(COLUMN_ACTIVITY, activity)
        values.put(COLUMN_COMPANY, company)
        values.put(COLUMN_DATE, date)
        values.put(COLUMN_TIME, time)

        //Inserting the new row
        try {
            val newRowId = db.insert(TABLE_NAME, null, values)
        } catch (e: SQLiteException) {
            db.execSQL(CREATE_TASK_TABLE)
            val newRowId = db.insert(TABLE_NAME, null, values)
        }

    }

    fun deleteTask(id: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "activityId = ?", arrayOf(id))
    }

    fun showTask(id: Int): ArrayList<Task> {
        val task = ArrayList<Task>()
        val db = this.readableDatabase
        val selectQuery =
            "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TASK_ID + " = '" + id + "'"
        val cursor: Cursor
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(CREATE_TASK_TABLE)
            return task
        }

        var company: Int
        var activity: Int
        var date: String
        var time: String

        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                company = cursor.getInt(cursor.getColumnIndex(COLUMN_COMPANY))
                activity = cursor.getInt(cursor.getColumnIndex(COLUMN_ACTIVITY))
                date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))
                time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME))

                task.add(Task(id, company, activity, date, time))
                cursor.moveToNext()

            }
        }
        return task
    }

    fun allTasks(): ArrayList<Task> {
        val db = this.writableDatabase
        val res = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val taskList = ArrayList<Task>()
        val cursor: Cursor? = null

        var taskID: Int
        var company: Int
        var activity: Int
        var date: String
        var time: String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                taskID = cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_ID))
                company = cursor.getInt(cursor.getColumnIndex(COLUMN_COMPANY))
                activity = cursor.getInt(cursor.getColumnIndex(COLUMN_ACTIVITY))
                date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))
                time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME))

                taskList.add(Task(taskID, company, activity, date, time))
                cursor.moveToNext()
            }
        }
        return taskList
    }

    fun getActivities(): ArrayList<Int> {
        val db = this.writableDatabase
        val res = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val activityList = ArrayList<Int>()
        val cursor: Cursor? = null

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                activityList.add(cursor.getInt(cursor.getColumnIndex(COLUMN_ACTIVITY)))
                cursor.moveToNext()
            }
        }

    return activityList}
}