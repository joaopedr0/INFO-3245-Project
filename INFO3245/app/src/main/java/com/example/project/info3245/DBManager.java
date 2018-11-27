package com.example.project.info3245;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import net.sqlcipher.database.SQLiteDatabase;

import static com.example.project.info3245.DatabaseHelper.TABLE_PREFERENCES;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase("");
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String title, String date, int priority) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.TITLE, title);
        contentValue.put(DatabaseHelper.DATE, date);
        contentValue.put(DatabaseHelper.PRIORITY, priority);
        contentValue.put(DatabaseHelper.COMPLETED, "NO");
        database.insert(DatabaseHelper.TABLE_TASKS, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper.ID, DatabaseHelper.TITLE, DatabaseHelper.DATE,
                DatabaseHelper.PRIORITY, DatabaseHelper.COMPLETED};
        Cursor cursor = database.query(DatabaseHelper.TABLE_TASKS, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchByPriority() {
        String[] columns = new String[] { DatabaseHelper.ID, DatabaseHelper.TITLE, DatabaseHelper.DATE,
                DatabaseHelper.PRIORITY, DatabaseHelper.COMPLETED};
        Cursor cursor = database.query(DatabaseHelper.TABLE_TASKS, columns, null, null, null, null, "priority ASC");
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchNotificationPreference() {
        String[] columns = new String[] { DatabaseHelper.NOTIFICATIONS};
        Cursor cursor = database.query(DatabaseHelper.TABLE_PREFERENCES, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long id, String title, String date, int priority, boolean completed) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TITLE, title);
        contentValues.put(DatabaseHelper.DATE, date);
        contentValues.put(DatabaseHelper.PRIORITY, priority);
        if(completed)
            contentValues.put(DatabaseHelper.COMPLETED, "YES");
        else
            contentValues.put(DatabaseHelper.COMPLETED, "NO");
        int i = database.update(DatabaseHelper.TABLE_TASKS, contentValues, DatabaseHelper.ID + " = " + id, null);
        return i;
    }

    public void updateNotifications(boolean notifications) {
        ContentValues contentValues = new ContentValues();
        if(notifications)
            contentValues.put(DatabaseHelper.NOTIFICATIONS, "YES");
        else
            contentValues.put(DatabaseHelper.NOTIFICATIONS, "NO");
        database.update(DatabaseHelper.TABLE_PREFERENCES, contentValues, null, null);
    }

    public void delete(long id) {
        database.delete(DatabaseHelper.TABLE_TASKS, DatabaseHelper.ID + "=" + id, null);
    }

}
