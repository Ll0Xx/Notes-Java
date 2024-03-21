package com.antont.notesjava.db;

import static com.antont.notesjava.db.Constants.NOTE_CONTENT_NAME;
import static com.antont.notesjava.db.Constants.NOTE_ID_NAME;
import static com.antont.notesjava.db.Constants.NOTE_TABLE_NAME;
import static com.antont.notesjava.db.Constants.NOTE_TITLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.antont.notesjava.db.entity.Note;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseQueryClass {
    private final Context context;

    public DatabaseQueryClass(Context context) {
        this.context = context;
    }

    public int insert(Note note) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_TITLE_NAME, note.getTitle());
        contentValues.put(NOTE_CONTENT_NAME, note.getContent());

        try {
            return (int) sqLiteDatabase.insertOrThrow(NOTE_TABLE_NAME, null, contentValues);
        } catch (SQLiteException e) {
            Toast.makeText(context, "Failed to save note :( ", Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }
        return 0;
    }

    public List<Note> getAllNotes() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        try (SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase(); Cursor cursor = sqLiteDatabase.query(NOTE_TABLE_NAME, null, null, null, null, null, null, null)) {

            if (cursor != null) if (cursor.moveToFirst()) {
                List<Note> studentList = new ArrayList<>();
                do {
                    int idColumnIndex = cursor.getColumnIndex(NOTE_ID_NAME);
                    int id = cursor.getInt(idColumnIndex);
                    int titleColumnIndex = cursor.getColumnIndex(NOTE_TITLE_NAME);
                    String title = cursor.getString(titleColumnIndex);
                    int contentColumnIndex = cursor.getColumnIndex(NOTE_CONTENT_NAME);
                    String content = cursor.getString(contentColumnIndex);

                    studentList.add(new Note(id, title, content));
                } while (cursor.moveToNext());

                return studentList;
            }
        } catch (Exception e) {
            Toast.makeText(context, "Failed to read all Notes", Toast.LENGTH_SHORT).show();
        }
        return Collections.emptyList();
    }

    public void update(Note note) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_TITLE_NAME, note.getTitle());
        contentValues.put(NOTE_CONTENT_NAME, note.getTitle());

        try {
            sqLiteDatabase.update(NOTE_TABLE_NAME, contentValues, NOTE_ID_NAME + " = ? ", new String[]{String.valueOf(note.getUid())});
        } catch (SQLiteException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }
    }

    public void delete(long registrationNum) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        try (SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase()) {
            sqLiteDatabase.delete(NOTE_TABLE_NAME, NOTE_ID_NAME + " = ? ", new String[]{String.valueOf(registrationNum)});
        } catch (SQLiteException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
