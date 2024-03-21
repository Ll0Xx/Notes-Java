package com.antont.notesjava.db;

import static com.antont.notesjava.db.Constants.DB_NAME;
import static com.antont.notesjava.db.Constants.NOTE_CONTENT_NAME;
import static com.antont.notesjava.db.Constants.NOTE_ID_NAME;
import static com.antont.notesjava.db.Constants.NOTE_TABLE_NAME;
import static com.antont.notesjava.db.Constants.NOTE_TITLE_NAME;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;
    private static final int DATABASE_VERSION = 1;

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTableSql =
                "CREATE TABLE " + NOTE_TABLE_NAME + " (" + NOTE_ID_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + NOTE_TITLE_NAME + " TEXT NOT NULL, " + NOTE_CONTENT_NAME + " TEXT)";
        db.execSQL(createNoteTableSql);

        // populate the database with test data for easy testing
        String testData1 = "INSERT INTO Note VALUES(1,'First note','First note content');";
        db.execSQL(testData1);
        String testData2 = "INSERT INTO Note VALUES(2,'Second note','Second note long long long long long content');";
        db.execSQL(testData2);
        String testData3 = "INSERT INTO Note VALUES(3,'Third note','Second note long long long long long long long long long long long content');";
        db.execSQL(testData3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NOTE_TABLE_NAME);
        onCreate(db);
    }
}
