package org.snake.android.datamodels;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class TodoItemsDBHelper extends SQLiteOpenHelper {

    private static final String Log = TodoItemsDBHelper.class.getName();
    private static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME = "todoDB";
    private static final String TABLE_TODO = "todo";

    //column names
    private static final String KEY_ID = "id";
    private static final String KEY_TEXT = "text";
    private static final String KEY_PRIORITY = "priority";

    private static final String CREATE_TABLE_TODO = "CREATE TABLE "
            + TABLE_TODO + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TEXT
            + " TEXT," + KEY_PRIORITY + " TEXT" + ")";

    public TodoItemsDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TODO);
    }

    //create a new todo item

    public long createTodo(TodoObject todo)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TEXT, todo.getText());
        values.put(KEY_PRIORITY,todo.getPriority());


        long todo_id = db.insert(TABLE_TODO,null,values);
        return todo_id;

    }

    //get a todo item
    public TodoObject getTodo(long todo_id) throws ParseException {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_TODO + " WHERE "
                + KEY_ID + " = " + todo_id;

        //Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery,null);
        if (c!=null)
            c.moveToFirst();

        int id = c.getInt(c.getColumnIndex(KEY_ID));
        String text = c.getString(c.getColumnIndex(KEY_TEXT));
        String priority = c.getString(c.getColumnIndex(KEY_PRIORITY));

       return new TodoObject(id, text, priority);

    }


    public List<TodoObject> getAllTodo() throws ParseException {
        List<TodoObject> todos = new ArrayList<TodoObject>();
        String selectQuery = "SELECT  * FROM " + TABLE_TODO;
        //Log.i(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(KEY_ID));
                String text = c.getString(c.getColumnIndex(KEY_TEXT));
                String priority =  c.getString(c.getColumnIndex(KEY_PRIORITY));

                todos.add(new TodoObject(id, text, priority));
            } while (c.moveToNext());
        }
        return todos;
    }

    public int updateToDo(TodoObject todo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TEXT, todo.getText());
        values.put(KEY_PRIORITY, todo.getPriority());
        return db.update(TABLE_TODO, values, KEY_ID + " = ?",
                new String[] { String.valueOf(todo.getId()) });
    }

    /**
     * Deleting a todo
     */
    public void deleteToDo(long todo_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO, KEY_ID + " = ?",
                new String[]{String.valueOf(todo_id)});
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }



}
