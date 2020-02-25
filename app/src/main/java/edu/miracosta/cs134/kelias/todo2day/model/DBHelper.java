package edu.miracosta.cs134.kelias.todo2day.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    // Task 1: Make constants for all database values
    // database name, version, table name, field names, primary key
    // psfs to type in constants string, psfi for constants int
    public static final String DATABASE_NAME = "ToDo2Day";
    public static final String TABLE_NAME = "Tasks";
    public static final int VERSION = 1;

    public static final String KEY_FIELD_ID = "_id";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_IS_DONE = "is_done";

    // constructor
    // DBHelper only needs to take in context
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSQL = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME  + "("
                + KEY_FIELD_ID + " INTEGER PRIMARY KEY, "
                + FIELD_DESCRIPTION + " TEXT, "
                + FIELD_IS_DONE + " INTEGER" + ")"; // in case we add more fields before )

        Log.i(DATABASE_NAME, createSQL);    // logs what is created
        db.execSQL(createSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // 1) Drop the old table, recreate the new
        if(newVersion > oldVersion)
        {
            String dropSQL = "DROP TABLE IF EXISTS " + TABLE_NAME;
            Log.i(DATABASE_NAME, dropSQL);
            db.execSQL(dropSQL);
            onCreate(db);
        }
    }

    /**
     * Adds a new task to the database.
     * @param task  The new task to be added.
     * @return The newly assigned id.
     */
    public long addTask(Task task){
        long id;
        // Decide whether we're reading or writing to / from the
        // database
        // For adding tasks, we're writing to the data base
        SQLiteDatabase db = getWritableDatabase();

        // When we add to the database, we use a data structure
        // called ContentValues (key, value) pairs
        ContentValues values = new ContentValues();

        // set up our key/value pairs
        values.put(FIELD_DESCRIPTION, task.getDescription());
        // ternary operator for 1 if true, 0 if false
        values.put(FIELD_IS_DONE, task.isIsDone() ? 1 : 0);

        // bd insert returns a long
        id = db.insert(TABLE_NAME, null, values);
        // After we're done, close the connection to the database
        db.close();

        return id;
    }

    /**
     * this method returns an ArrayList containing all the Tasks in the database
     * @return an array of list of tasks
     */
    public List<Task> getAllTasks()
    {
        // initialize empty array list
        List<Task> allTasks = new ArrayList<>();
        // Get the tasks from the database
        SQLiteDatabase db = getReadableDatabase();

        // Query the database to retrieve all records
        // Store them in a data structure known as a cursor
        // filter out what you want from database
        // new String = columns we want to query
        Cursor cursor = db.query(TABLE_NAME,
                new String[] {KEY_FIELD_ID, FIELD_DESCRIPTION, FIELD_IS_DONE},
                null,
                null,
                null,
                null,
                null);

        // Loop through the cursor results, one at a time
        // 1) Instantiate a new Task object
        // 2) Add the new Task to the List
        // use while b/c we don't know how many tasks there are
        if(cursor.moveToFirst())
        {
            do {
                // variable conversion for sql
                long id = cursor.getLong(0);
                String description = cursor.getString(1);
                boolean isDone = cursor.getInt(2) == 1; // compares if true == 1,
                                                                    // otherwise false

                Task newTask = new Task(id, description, isDone);
                allTasks.add(newTask);
                // could shortcut above 2 lines to make a single line
            } while(cursor.moveToNext());
        }

        // Close the cursor - eventually there will be a memory leak and stale data,
        // slow results occur
        cursor.close();
        // make sure to close/unlock the connection to the database
        db.close();

        return allTasks;
    }

    /**
     * wipe the database / clear all tasks
     */
    public void clearAllTasks()
    {
        // clears spreadsheet but does not delete the columns/table like DROP TABLE
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, null, null);

        db.close();
    }

    /**
     * this method deletes one task from the database
     * @param task  Task object
     */
    public void deleteTask(Task task)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, KEY_FIELD_ID + " = ?",
                new String[] {String.valueOf(task.getId())});
        db.close();
    }

    /**
     * this method updates some/all of the information about a Task in the database
     * @param task  Task object
     */
    public void updateTask(Task task)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIELD_ID, task.getId());
        values.put(FIELD_DESCRIPTION, task.getDescription());
        values.put(FIELD_IS_DONE, task.isIsDone() ? 1 : 0);

        db.update(TABLE_NAME, values,
                KEY_FIELD_ID + "=" + task.getId(), null);
        db.close();
    }

    /**
     * this method returns one Task from the database with the id specified, or null if the id
     * cannot be found.
     * @param id    unique (primary key) task number
     * @return      task from db or null if id not found
     */
    public Task getSingleTask(int id)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{KEY_FIELD_ID, FIELD_DESCRIPTION, FIELD_IS_DONE},
                KEY_FIELD_ID + "=" + id,
                null,
                null,
                null,
                null);

        Task task = null;
        if (cursor.moveToFirst())
        {
            task = new Task(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2) == 1);
        }
        cursor.close();
        db.close();
        return task;
    }
}
