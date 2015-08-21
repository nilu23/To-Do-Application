package com.example.fnilofar.firstandriodapplication;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fnilofar on 8/14/2015.
 */
public class TodoDatabase extends SQLiteOpenHelper {

    private static TodoDatabase sInstance;

    // Database Info
    private static final String DATABASE_NAME = "todoDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_ITEMS = "items";

    // Items Table Columns
    private static final String KEY_ITEM_ID = "id";
    private static final String KEY_ITEM_ID_FK = "itemId";
    private static final String KEY_ITEM_TEXT = "text";
    private static final String KEY_ITEM_DUE_DATE = "dueDate";
    private static final String KEY_ITEM_PRIORITY = "priority";



    public static synchronized TodoDatabase getsInstance(Context context){

        if(sInstance == null){
            sInstance = new TodoDatabase(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
        }

        return sInstance;
    }

    public TodoDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS +
                "(" +
                KEY_ITEM_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_ITEM_ID_FK + " INTEGER," + // Define a foreign key
                KEY_ITEM_TEXT + " TEXT," +
                KEY_ITEM_DUE_DATE + " TEXT, " +
                KEY_ITEM_PRIORITY + " INTEGER" +
                ")";
        db.execSQL(CREATE_ITEMS_TABLE);

    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
            onCreate(db);
        }
    }



    // Insert a item into the database
    public void addItem(Items item) {

        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();


        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put(KEY_ITEM_ID_FK, item.id);
            values.put(KEY_ITEM_TEXT, item.text);
            values.put(KEY_ITEM_DUE_DATE, item.dueDate);
            values.put(KEY_ITEM_PRIORITY, item.priority);

            db.insertOrThrow(TABLE_ITEMS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(this.toString(), "Error while trying to add item to database");
        } finally {
            db.endTransaction();
        }
    }


    public List<Items> getAllItems() {
        List<Items> itemsArrayList = new ArrayList<>();

        // SELECT * FROM ITEMS

        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s ",
                        TABLE_ITEMS);

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Items newItem = new Items();
                    newItem.id = cursor.getInt(cursor.getColumnIndex(KEY_ITEM_ID_FK));
                    newItem.text = cursor.getString(cursor.getColumnIndex(KEY_ITEM_TEXT));
                    newItem.dueDate = cursor.getString(cursor.getColumnIndex(KEY_ITEM_DUE_DATE));
                    newItem.priority = cursor.getInt(cursor.getColumnIndex(KEY_ITEM_PRIORITY));
                    itemsArrayList.add(newItem);

                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(this.toString(), "Error while trying to get items from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return itemsArrayList;
    }


    // Update the item text
    public int updateItem(Items item, Items oldItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_TEXT, item.text);
        values.put(KEY_ITEM_PRIORITY, item.priority);
        values.put(KEY_ITEM_DUE_DATE, item.dueDate);

        // Updating item description
        return db.update(TABLE_ITEMS, values, KEY_ITEM_ID_FK + " = ?",
                new String[] { String.valueOf(oldItem.id) });
    }

    public void deleteItem(Items item) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            db.delete(TABLE_ITEMS, KEY_ITEM_ID_FK + " = ?", new String[]{String.valueOf(item.id)});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(this.toString(), "Error while trying to delete item");
        } finally {
            db.endTransaction();
        }
    }

    public void deleteAllItems() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            // Order of deletions is important when foreign key relationships exist.
            db.delete(TABLE_ITEMS, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(this.toString(), "Error while trying to delete all items");
        } finally {
            db.endTransaction();
        }
    }

}
