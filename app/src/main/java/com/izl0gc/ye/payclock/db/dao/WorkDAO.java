package com.izl0gc.ye.payclock.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.izl0gc.ye.payclock.db.DAOInterface;
import com.izl0gc.ye.payclock.db.PayClockSQLiteHelper;
import com.izl0gc.ye.payclock.db.contracts.WorkContract;
import com.izl0gc.ye.payclock.models.Work;

public class WorkDAO implements DAOInterface<Work> {
    private SQLiteDatabase db;
    private PayClockSQLiteHelper helper;

    public WorkDAO(Context c){
        helper = new PayClockSQLiteHelper(c);
    }

    public void open(){
        db = helper.getWritableDatabase();
    }

    @Override
    public Cursor getAll() {
        open();
        Cursor works =  db.query(WorkContract.Work.TABLE_NAME, new String[] {WorkContract.Work._ID, WorkContract.Work.COLUMN_NAME, WorkContract.Work.COLUMN_RATE}, null, null, null, null, null);

        return works;
    }

    @Override
    public Work get(int id) {
        open();
        Cursor workCursor =  db.query(WorkContract.Work.TABLE_NAME,
                                      new String[] {WorkContract.Work._ID, WorkContract.Work.COLUMN_NAME,
                                      WorkContract.Work.COLUMN_RATE},
                                      WorkContract.Work._ID +"="+id, null, null, null, null);

        Work work = null;
        if(workCursor.moveToFirst()){
            work = new Work(workCursor.getString(1), workCursor.getDouble(2));
            work.setId(workCursor.getInt(0));
        }

        return work;
    }

    @Override
    public Work update(Work w) {
        open();
        String where = WorkContract.Work._ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(w.getId())};

        ContentValues data = new ContentValues();
        data.put(WorkContract.Work.COLUMN_NAME, w.getName());
        data.put(WorkContract.Work.COLUMN_RATE, w.getRate());

        db.update(WorkContract.Work.TABLE_NAME, data, where, whereArgs );
        return w;
    }

    @Override
    public Work create(Work w) {
        open();
        ContentValues data = new ContentValues();
        data.put(WorkContract.Work.COLUMN_NAME, w.getName());
        data.put(WorkContract.Work.COLUMN_RATE, w.getRate());
        w.setId(((int) db.insert(WorkContract.Work.TABLE_NAME, null, data)));

        return w;
    }

    @Override
    public Work delete(Work w) {
        open();
        String where = WorkContract.Work._ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(w.getId())};

        db.delete(WorkContract.Work.TABLE_NAME, where, whereArgs );
        return null;
    }
}
