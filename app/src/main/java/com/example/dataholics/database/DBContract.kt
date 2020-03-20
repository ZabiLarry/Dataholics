package com.example.dataholics.database

import android.provider.BaseColumns;

object DBContract {

    class TaskEntry : BaseColumns{

        companion object{
            val DATABASE_VERSION = 1;
            val DATABASE_NAME = "TasksDatabase.db"
            val TABLE_NAME = "tasks"
            val COLUMN_TASK_ID = "taskId"
            val COLUMN_ACTIVITY = "activity"
            val COLUMN_COMPANY = "company"
        }
    }
}