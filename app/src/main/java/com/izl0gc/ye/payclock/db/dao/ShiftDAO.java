package com.izl0gc.ye.payclock.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.izl0gc.ye.payclock.db.DAOInterface;
import com.izl0gc.ye.payclock.db.PayClockSQLiteHelper;
import com.izl0gc.ye.payclock.db.contracts.WorkContract;
import com.izl0gc.ye.payclock.models.Shift;


public class ShiftDAO implements DAOInterface<Shift> {
    private SQLiteDatabase db;
    private PayClockSQLiteHelper helper;

    public ShiftDAO(Context c){
        helper = new PayClockSQLiteHelper(c);
    }

    public void open(){
        db = helper.getWritableDatabase();
    }

    @Override
    public Cursor getAll() {
        return null;
    }

    @Override
    public Shift get(int id) {
        return null;
    }

    @Override
    public Shift update(Shift s) {
        return null;
    }

    @Override
    public Shift create(Shift s) {
        open();
        Log.v("Shift to be added", String.valueOf(s.getDate()));
        ContentValues values = new ContentValues();
        values.put(WorkContract.Shift.COLUMN_DATE, s.getDate());
        values.put(WorkContract.Shift.COLUMN_START, s.getStartTime());
        values.put(WorkContract.Shift.COLUMN_END, s.getEndTime());
        values.put(WorkContract.Shift.COLUMN_HAS_BREAK, s.isHas_break());
        values.put(WorkContract.Shift.COLUMN_WORKID, s.getWork_id());
        values.put(WorkContract.Shift.COLUMN_RATE, s.getRate());

        s.setId((int)db.insert(WorkContract.Shift.TABLE_NAME, null, values));

        return s;
    }

    @Override
    public Shift delete(Shift s) {
        return null;
    }

    public double getBreakTime(long startdate, long enddate, int work_id){
        open();
        String[] tableColumns = new String[] {
                WorkContract.Shift.COLUMN_START, WorkContract.Shift.COLUMN_END
        };
        String where = WorkContract.Shift.COLUMN_WORKID + "=? AND " + WorkContract.Shift.COLUMN_HAS_BREAK + "=? AND "
                + WorkContract.Shift.COLUMN_DATE + " BETWEEN ? AND ?";
        String[] val = {String.valueOf(work_id),"1", String.valueOf(startdate), String.valueOf(enddate)};

        Cursor workDates = db.query(WorkContract.Shift.TABLE_NAME, tableColumns,  where,val, null, null, null);

        return workDates.getCount() * .5;
    }

    public Cursor getWorkDates(long startdate, long enddate, int work_id){
        open();
        String[] tableColumns = new String[] {
                WorkContract.Shift.COLUMN_START, WorkContract.Shift.COLUMN_END, WorkContract.Shift.COLUMN_RATE
        };

        String where = WorkContract.Shift.COLUMN_WORKID + "=? AND "
                       + WorkContract.Shift.COLUMN_DATE + " BETWEEN ? AND ?";
        String[] val = {String.valueOf(work_id), String.valueOf(startdate), String.valueOf(enddate)};

        Cursor workDates = db.query(WorkContract.Shift.TABLE_NAME, tableColumns,  where,val, null, null, null);

        return workDates;
    }

}
