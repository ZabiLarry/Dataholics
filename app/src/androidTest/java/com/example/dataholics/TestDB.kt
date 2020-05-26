package com.example.dataholics

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import com.example.dataholics.database.Task
import com.example.dataholics.database.TaskDBHelper
import org.junit.Assert
import org.junit.Test
import org.junit.Assert.assertArrayEquals

val context = ApplicationProvider.getApplicationContext<Context>()

class TestDB {

    val taskDBHelper = TaskDBHelper(context!!)


    @Test
    fun testConnection() {

        Log.d("Testing connection","opening db and checking")
        taskDBHelper.writableDatabase
        Assert.assertEquals(true, taskDBHelper.openOrClosed())
        Log.d("Testing connection","closing db")
        taskDBHelper.close()
    }

    @Test
    fun testInputOutput() {

        Log.d("Testing connection","clearing database for testing")
        taskDBHelper.deleteAll()
        val activity = 1
        val company = 2
        val date = 20200705
        val time = 22

        Log.d("Testing Input/Output","sending Input")
        taskDBHelper.addTask(activity, company, (date * 100) + time)

        val expectedResults =  arrayOf<Int>((date * 100) + time, company, activity)

        Log.d("Testing Input/Output","sending Input")
        val actualResultsArrayList = taskDBHelper.allTasks()
        val task : Task = actualResultsArrayList.get(0)

        val actualResults = arrayOf<Int>(task.TaskId, task.activity, task.company)


        assertArrayEquals(expectedResults, actualResults)

    }

    @Test
    fun testDelete() {
        val activity = 1
        val company = 2
        val date : Int = 2020070522
        val time = 22

        Log.d("Testing delete","sending input")
        taskDBHelper.addTask(activity, company, date + time)
        Log.d("Testing delete","clearing DB")

        taskDBHelper.deleteAll()
        val result = taskDBHelper.allTasks();
        Assert.assertEquals(true, result.isEmpty())

    }

}