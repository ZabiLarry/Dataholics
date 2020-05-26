package com.example.dataholics

import android.util.Log
import com.example.dataholics.database.TaskDBHelper
import org.junit.Test


class TaskDBHelperTest {
    val taskDBHelper = TaskDBHelper(context!!)
    val timer = Timer()

    @Test
    fun testInput() {
        var inputSize = 100000;
        var date = 2020070522

        timer.startTimer()
        while (inputSize > 0) {
            taskDBHelper.addTask(1, 1, date)
            inputSize--
            date++
        }
        timer.stopTimer()
        Log.i("Results", timer.toString())
    }

}