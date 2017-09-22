package com.izl0gc.ye.payclock.db.contracts;

import android.provider.BaseColumns;


public final class WorkContract {

    private WorkContract(){}

    public static class Work implements BaseColumns {
        public static final String TABLE_NAME = "works";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_RATE = "rate";
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_RATE + " DOUBLE " +
                ")";




    }

    public static class Shift implements BaseColumns{
        public static final String TABLE_NAME = "shifts";
        public static final String COLUMN_WORKID = "work_id";
        public static final String COLUMN_RATE = "shift_rate";
        public static final String COLUMN_DATE = "work_date";
        public static final String COLUMN_START = "start";
        public static final String COLUMN_END = "end";
        public static final String COLUMN_HAS_BREAK = "has_break"; // = minus 30 mins from total hour
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_WORKID + " INTEGER, " +
                COLUMN_DATE  + " INTEGER, " +
                COLUMN_START + " DOUBLE, " +
                COLUMN_END + " DOUBLE, " +
                COLUMN_RATE + " DOUBLE, " +
                COLUMN_HAS_BREAK + " BOOLEAN, "+
                "FOREIGN KEY(" + COLUMN_WORKID + ") REFERENCES " +
        Work.TABLE_NAME + "(" + Work._ID + ") " + ")";
    }

}
