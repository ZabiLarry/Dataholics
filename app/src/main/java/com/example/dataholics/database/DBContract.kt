package com.example.dataholics.database

import android.provider.BaseColumns;

object DBContract {

    class ActivityEntry : BaseColumns{

        companion object{
            val DATABASE_VERSION = 1;
            val DATABASE_NAME = "ActivitiesDatabase"
            val TABLE_NAME = "activities"
            val COLUMN_ACTIVITY_ID = "activityId"
            val COLUMN_ACTIVITY_TYPE = "activityType"
            val COLUMN_COMPANY = "company"
            val COLUMN_DATE = "date"
            val COLUMN_TIME = "time"
        }
    }
}