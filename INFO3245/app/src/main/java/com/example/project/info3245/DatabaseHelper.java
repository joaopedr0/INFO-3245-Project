package com.example.project.info3245;

import android.content.Context;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_TASKS = "TASKS";
    public static final String TABLE_PREFERENCES = "PREFERENCES";

    // Table columns
    public static final String ID = "ID";
    public static final String TITLE = "TITLE";
    public static final String DATE = "DATE";
    public static final String PRIORITY = "PRIORITY";
    public static final String COMPLETED = "COMPLETED";
    public static final String NOTIFICATIONS = "NOTIFICATIONS";


    // Database Information
    static final String DB_NAME = "TOODOO.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE_TASKS = "create table " + TABLE_TASKS + "(" + ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE + " TEXT NOT NULL, " + DATE + " TEXT NOT NULL, "
            + PRIORITY + " INTEGER NOT NULL, " + COMPLETED + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_PREFERENCES = "create table " + TABLE_PREFERENCES + "("
            + NOTIFICATIONS + " TEXT NOT NULL);";

    private static final String ADD_NOTIFICATION = "insert into " + TABLE_PREFERENCES +
            "VALUES ('YES');";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TASKS);
        db.execSQL(CREATE_TABLE_PREFERENCES);
        db.execSQL(ADD_NOTIFICATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PREFERENCES);
        onCreate(db);
    }
}
