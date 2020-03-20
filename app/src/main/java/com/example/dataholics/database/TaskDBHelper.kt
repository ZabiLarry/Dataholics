package com.example.dataholics.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.dataholics.database.DBContract.TaskEntry.Companion.COLUMN_ACTIVITY
import com.example.dataholics.database.DBContract.TaskEntry.Companion.COLUMN_COMPANY
import com.example.dataholics.database.DBContract.TaskEntry.Companion.COLUMN_TASK_ID
import com.example.dataholics.database.DBContract.TaskEntry.Companion.DATABASE_NAME
import com.example.dataholics.database.DBContract.TaskEntry.Companion.DATABASE_VERSION
import com.example.dataholics.database.DBContract.TaskEntry.Companion.TABLE_NAME

class TaskDBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val CREATE_TASK_TABLE = ("CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_TASK_ID + " BLOB PRIMARY KEY, " +
            "" + COLUMN_COMPANY + " INTEGER, " + COLUMN_ACTIVITY + " INTEGER" + ")")


    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL(CREATE_TASK_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }


    fun addTask(activity: Int, company: Int, date: Int) {
        //Gets the repo to write mode
        val db = writableDatabase

        //Mapping all the values to go in
        val values = ContentValues()

        values.put(COLUMN_COMPANY, company)
        values.put(COLUMN_ACTIVITY, activity)
        values.put(COLUMN_TASK_ID, date)
        //Checks if is already
        if (checkAlreadyExist(date)){
            db.update(TABLE_NAME, values, "taskId= " + date, null)
        } else {
            db.insert(TABLE_NAME, null, values)
        }

        //Inserting the new row
        db.close()


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
        var cursor: Cursor

        cursor = db.rawQuery(selectQuery, null)


        var company: Int
        var activity: Int


        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                company = cursor.getInt(cursor.getColumnIndex(COLUMN_COMPANY))
                activity = cursor.getInt(cursor.getColumnIndex(COLUMN_ACTIVITY))


                task.add(Task(id, company, activity))
                cursor.moveToNext()

            }
        }
        db.close()
        return task
    }

    fun checkAlreadyExist(taskId: Int): Boolean {
        val db = this.readableDatabase
        val query: String =
            "SELECT " + COLUMN_TASK_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_TASK_ID + " = '" + taskId + "'"
        val cursor: Cursor
        cursor = db.rawQuery(query, null)
        if (cursor.count <= 0){
            return false
        } else {
            return true
        }
    }

    fun allTasks(): ArrayList<Task> {
        val db = this.readableDatabase
        var cursor: Cursor

        cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        val taskList = ArrayList<Task>()


        var taskID: Int
        var company: Int
        var activity: Int

        if (cursor.moveToFirst()) {
            do {
                taskID = cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_ID))
                company = cursor.getInt(cursor.getColumnIndex(COLUMN_COMPANY))
                activity = cursor.getInt(cursor.getColumnIndex(COLUMN_ACTIVITY))


                taskList.add(Task(taskID, company, activity))
                cursor.moveToNext()
            } while (cursor.moveToNext())
        }
        db.close()
        return taskList
    }

    fun getActivities(): ArrayList<Int> {
        val db = this.readableDatabase
        var cursor: Cursor? = null
        cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val activityList = ArrayList<Int>()


        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                activityList.add(cursor.getInt(cursor.getColumnIndex(COLUMN_ACTIVITY)))
                cursor.moveToNext()
            }
        }
        db.close()
        return activityList
    }
}