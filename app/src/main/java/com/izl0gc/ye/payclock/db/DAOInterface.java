package com.izl0gc.ye.payclock.db;

import android.database.Cursor;

public interface DAOInterface<T> {
    Cursor getAll();
    T get(int id);
    T update(T o);
    T create(T o);
    T delete(T o);
}
