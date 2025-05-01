package com.example.taskapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.taskapp.database.TaskContract.TaskEntry;
import java.util.ArrayList;
import java.util.List;

public class TaskDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Tasks.db";

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TaskEntry.SQL_CREATE_ENTRIES);
    }

    public long insertTask(String title, String desc, long datetime) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_TITLE, title);
        values.put(TaskEntry.COLUMN_DESC, desc);
        values.put(TaskEntry.COLUMN_DATETIME, datetime);
        return db.insert(TaskEntry.TABLE_NAME, null, values);
    }

    public List<Task> getFutureTasks(long currentTime) {
        List<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String selection = TaskEntry.COLUMN_DATETIME + " > ?";
        String[] selectionArgs = {String.valueOf(currentTime)};
        
        try (Cursor cursor = db.query(
                TaskEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null, null, null
        )) {
            while (cursor.moveToNext()) {
                tasks.add(new Task(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getLong(3)
                ));
            }
        }
        return tasks;
    }
}
