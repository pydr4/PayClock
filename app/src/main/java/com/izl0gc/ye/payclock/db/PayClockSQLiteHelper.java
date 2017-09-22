package com.izl0gc.ye.payclock.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.izl0gc.ye.payclock.db.contracts.WorkContract;




public class PayClockSQLiteHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "work_database";

    public PayClockSQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(WorkContract.Work.CREATE_TABLE);
        sqLiteDatabase.execSQL(WorkContract.Shift.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int ii){
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WorkContract.Work.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WorkContract.Shift.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WorkContract.Work.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WorkContract.Shift.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
